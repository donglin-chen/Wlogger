package com.wottui.wlogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 13:43
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class WLoggerApiserverMain {

    public static void main(String[] args) {
        SpringApplication.run(WLoggerApiserverMain.class, args);
    }

}
