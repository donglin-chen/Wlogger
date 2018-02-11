package com.wottui.wlogger.client.log4j;

import com.wottui.wlogger.client.core.WLoggerClient;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/2/11
 * @Time: 14:42
 */
public class WloggerHttpAppender extends AppenderSkeleton {
    private String appName;
    private String gateway;

    @Override
    protected void append(LoggingEvent loggingEvent) {
        String level = loggingEvent.level.toString();
        String msg = loggingEvent.getMessage().toString();
        WLoggerClient.DEFAULT.dispatch(msg, level);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
        WLoggerClient.Env.e.setNamespace(appName);
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
        WLoggerClient.Env.e.setUrl(gateway);
    }
}
