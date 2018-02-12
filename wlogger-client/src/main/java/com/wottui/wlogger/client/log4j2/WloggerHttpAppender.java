package com.wottui.wlogger.client.log4j2;


import com.wottui.wlogger.client.core.WLoggerClient;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.message.Message;

import java.io.Serializable;

import static com.wottui.wlogger.client.core.WLoggerClient.DEFAULT_NAMESPACE;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/2/11
 * @Time: 17:23
 */
@Plugin(name = "WloggerHttpAppender", category = "Core", elementType = "appender", printObject = true)
public class WloggerHttpAppender extends AbstractAppender {

    public WloggerHttpAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    @Override
    public void append(LogEvent logEvent) {
        try {
            String level = logEvent.getLevel().name();
            Message message = logEvent.getMessage();
            if (message != null) {
                //send log message
                WLoggerClient.DEFAULT.dispatchStringMessage(message.getFormattedMessage(), level);
            }
            Throwable throwable = logEvent.getThrown();
            if (throwable != null) {
                //send log message
                WLoggerClient.DEFAULT.dispatchThrowable(throwable, level);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @PluginFactory
    public static WloggerHttpAppender createAppender(@PluginAttribute("name") String name,
                                                     @PluginElement("Filter") final Filter filter,
                                                     @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                     @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                                     @PluginAttribute("appName") String appName,
                                                     @PluginAttribute("gateway") String gateway) {
        if (gateway == null || gateway.length() == 0)
            throw new RuntimeException("WloggerClient Gateway Value not null");
        //init wlogger client environment
        WLoggerClient.Env.e.setUrl(gateway);
        WLoggerClient.Env.e.setNamespace(appName == null ? DEFAULT_NAMESPACE : appName);
        return new WloggerHttpAppender(name, filter, layout);
    }

}
