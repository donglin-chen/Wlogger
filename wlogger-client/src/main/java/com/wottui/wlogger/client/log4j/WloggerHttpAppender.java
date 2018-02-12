package com.wottui.wlogger.client.log4j;

import com.wottui.wlogger.client.core.WLoggerClient;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import static com.wottui.wlogger.client.core.WLoggerClient.DEFAULT_NAMESPACE;

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
        try {
            String level = loggingEvent.level.toString();
            Object msg = loggingEvent.getMessage();
            if (msg != null) {
                //send log message
                WLoggerClient.DEFAULT.dispatchStringMessage(msg.toString(), level);
            }
            ThrowableInformation information = loggingEvent.getThrowableInformation();
            if (information != null) {
                //send log message
                WLoggerClient.DEFAULT.dispatchThrowable(information.getThrowable(), level);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
        WLoggerClient.Env.e.setNamespace(appName == null ? DEFAULT_NAMESPACE : appName);
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
        if (gateway == null)
            throw new RuntimeException("Wlogger client gateway not null");
        WLoggerClient.Env.e.setUrl(gateway);
    }
}
