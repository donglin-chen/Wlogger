package com.wottui.wlogger.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/2/1
 * @Time: 14:43
 */
public class LogVO implements Serializable {
    private String id;
    private String namespace;
    private String ip;
    private String level;
    private List<String> content;
    private long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
