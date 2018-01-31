package com.wottui.wlogger.client;

import com.wottui.wlogger.core.ILoggerDataDealTools;
import com.wottui.wlogger.core.Level;
import com.wottui.wlogger.core.LoggerDataDealTools;
import com.wottui.wlogger.core.WLoggerData;
import com.wottui.wlogger.core.utils.JerseyClient;
import org.ho.yaml.Yaml;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.wottui.wlogger.core.API.LOG_UPLOAD;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 16:38
 */
public class WLoggerClient implements IWLoggerClient {
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(2, 8, 0, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue(100), new ThreadPoolExecutor.DiscardOldestPolicy());
    private static ILoggerDataDealTools tools = new LoggerDataDealTools();
    private String namespace;
    private static final String URL = "http://wlogger.mob.com/wlogger/api/gateway";
    private static WLoggerClient client = new WLoggerClient();

    private WLoggerClient() {
        this.namespace = this.getNamespace();
    }

    public static WLoggerClient newInstance() {
        return client;
    }

    private String getNamespace() {
        String namespace;
        try {
            Properties properties = new Properties();
            InputStream inputStream = WLoggerClient.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            properties.load(inputStream);
            namespace = properties.getProperty("app.name");
            if (namespace == null)
                namespace = this.getNamespaceAtYAML();
        } catch (Throwable e) {
            namespace = this.getNamespaceAtYAML();
        }
        return namespace;
    }

    private String getNamespaceAtYAML() {
        try {
            InputStream inputStream = WLoggerClient.class.getClassLoader().getResourceAsStream("application.yml");
            Map map = Yaml.loadType(inputStream, HashMap.class);
            namespace = (String) map.get("app.name");
            return namespace;
        } catch (Throwable e) {
            return "default";
        }
    }


    @Override
    public void infoLog(String log) {
        EXECUTOR_SERVICE.execute(new WLoggerWorker(log, Level.INFO));
    }

    @Override
    public void debugLog(String log) {
        EXECUTOR_SERVICE.execute(new WLoggerWorker(log, Level.DEBUG));
    }

    @Override
    public void warnLog(String log) {
        EXECUTOR_SERVICE.execute(new WLoggerWorker(log, Level.WARN));
    }

    @Override
    public void errorLog(Throwable throwable) {
        StringWriter errorsWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(errorsWriter, true));
        String log = errorsWriter.toString();
        EXECUTOR_SERVICE.execute(new WLoggerWorker(log, Level.ERROR));
    }

    @Override
    public void errorLog(String log) {
        EXECUTOR_SERVICE.execute(new WLoggerWorker(log, Level.ERROR));
    }

    private class WLoggerWorker implements Runnable {
        private String log;
        private Level level;

        public WLoggerWorker(String log, Level level) {
            this.log = log;
            this.level = level;
        }

        @Override
        public void run() {
            try {
                WLoggerData wLoggerData = new WLoggerData();
                wLoggerData.setContent(log);
                wLoggerData.setLevel(level.name());
                wLoggerData.setNamespace(namespace);
                wLoggerData.setTimestamp(System.currentTimeMillis());
                String text = tools.deal(wLoggerData);
                Map<String, String> params = new HashMap<>();
                params.put("text", text);
                params.put("apitype", LOG_UPLOAD.name());
                JerseyClient.post(URL, params);
            } catch (Throwable e) {
            }
        }

    }

    public static void main(String[] args) throws Exception {
        WLoggerClient client = new WLoggerClient();
        while (true) {
            client.debugLog("fhsifhsifhsifs");
            System.out.println(Thread.currentThread().getName());
        }
    }
}
