package com.wottui.wlogger.client.core;

import com.wottui.wlogger.core.ILoggerDataDealTools;
import com.wottui.wlogger.core.Level;
import com.wottui.wlogger.core.LoggerDataDealTools;
import com.wottui.wlogger.core.WLoggerData;
import com.wottui.wlogger.core.utils.JerseyClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
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
                                                                                   new ArrayBlockingQueue<Runnable>(
                                                                                           100),
                                                                                   new ThreadPoolExecutor.DiscardOldestPolicy());
    public static final WLoggerClient DEFAULT = new WLoggerClient();
    private static ILoggerDataDealTools tools = new LoggerDataDealTools();
    public static final String DEFAULT_NAMESPACE = "default";

    public WLoggerClient() {

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
        EXECUTOR_SERVICE.execute(new WLoggerWorker(throwableToString(throwable), Level.ERROR));
    }

    private String throwableToString(Throwable throwable) {
        StringWriter errorsWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(errorsWriter, true));
        return errorsWriter.toString();
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
                wLoggerData.setNamespace(Env.e.getNamespace());
                wLoggerData.setTimestamp(System.currentTimeMillis());
                String text = tools.deal(wLoggerData);
                Map<String, String> params = new HashMap<>();
                params.put("text", text);
                params.put("apitype", LOG_UPLOAD.name());
                JerseyClient.post(Env.e.getUrl(), params);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void fatalLog(String message) {

    }

    /**
     * 日志分发器
     *
     * @param message 日志体
     * @param level   日志级别
     */
    @Override
    public void dispatchStringMessage(String message, String level) {
        if (Level.DEBUG.name().equals(level))
            this.debugLog(message);
        if (Level.INFO.name().equals(level))
            this.infoLog(message);
        if (Level.WARN.name().equals(level))
            this.warnLog(message);
        if (Level.ERROR.name().equals(level))
            this.errorLog(message);
        if (Level.FATAL.name().equals(level))
            this.fatalLog(message);
    }


    @Override
    public void dispatchThrowable(Throwable throwable, String level) {
        this.dispatchStringMessage(throwableToString(throwable), level);
    }

    public static class Env {
        public static final Env e = new Env();
        private String namespace;
        private String url;

        String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
