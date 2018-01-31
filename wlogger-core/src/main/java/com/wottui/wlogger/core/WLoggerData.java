package com.wottui.wlogger.core;

import com.wottui.wlogger.core.utils.MachineUtils;

import java.io.Serializable;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:18
 */
public class WLoggerData implements Serializable {

    private String namespace;
    private String ip;
    private String level;
    private String content;
    private long timestamp;

    public WLoggerData() {
        this.timestamp = System.currentTimeMillis();
        this.ip = MachineUtils.getLocalIP();
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
