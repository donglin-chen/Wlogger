package com.wottui.wlogger.core;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:13
 */
public interface ILoggerDataDealTools {

    /**
     * 处理日志文件 先压缩在BASE64编码
     *
     * @param wLoggerData
     * @return
     */
    String deal(WLoggerData wLoggerData);

    /**
     * 将压缩在BASE64编码后的日志文件，转成WLoggerData对象
     *
     * @param data
     * @return
     */
    WLoggerData revert(String data);
}
