package com.wottui.wlogger.client.core;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 16:37
 */
public interface IWLoggerClient {

    /**
     * @param log
     */
    void infoLog(String log);

    void debugLog(String log);

    void warnLog(String log);

    void errorLog(String log);

    void errorLog(Throwable throwable);

    void fatalLog(String message);

    void dispatchStringMessage(String message, String level);

    void dispatchThrowable(Throwable throwable, String level);
}
