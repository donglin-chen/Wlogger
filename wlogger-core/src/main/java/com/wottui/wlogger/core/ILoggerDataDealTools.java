package com.wottui.wlogger.core;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:13
 */
public interface ILoggerDataDealTools {


    String deal(WLoggerData wLoggerData);

    WLoggerData revert(String data);
}
