package com.wottui.wlogger.test.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/2/11
 * @Time: 17:43
 */
public class Log4j2Main {
    private static final Logger LOGGER = LogManager.getLogger(Log4j2Main.class.getName());

    public static void main(String[] args) throws Exception {
        while (true) {
            LOGGER.debug("tests",new NullPointerException("rer"));
            Thread.sleep(1000);
        }
    }
}
