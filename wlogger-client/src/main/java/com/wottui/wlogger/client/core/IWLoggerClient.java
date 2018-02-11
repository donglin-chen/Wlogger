package com.wottui.wlogger.client.core;

import com.wottui.wlogger.core.Level;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 16:37
 */
public interface IWLoggerClient {

    void infoLog(String log);

    void debugLog(String log);

    void warnLog(String log);

    void errorLog(String log);

    void errorLog(Throwable throwable);

    void fatalLog(String message);

    void dispatch(String message, String level);
}
