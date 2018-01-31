package com.wottui.wlogger.core.utils;

import java.net.InetAddress;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 19:56
 */
public class MachineUtils {

    public static String getLocalIP() {
        String ip;
        try {
            InetAddress a = InetAddress.getLocalHost();
            ip = a.getHostAddress();
        } catch (Exception ex) {
            ip = "UNKNOWN_IP";
        }
        return ip;
    }
}
