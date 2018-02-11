package com.wottui.wlogger.test.log4j;

import org.apache.log4j.Logger;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/2/11
 * @Time: 12:30
 */
public class Log4jMain {
    final static Logger logger = Logger.getLogger(Log4jMain.class);

    public static void main(String[] args) throws Exception {
        while (true) {
            LogUtils.debug();
            Thread.sleep(1000);
        }
    }
}
