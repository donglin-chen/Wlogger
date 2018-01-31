package com.wottui.wlogger.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 20:32
 */
@Document(collection = "tb_wlogger_info")
public class WLoggerInfo implements Serializable {
    @Id
    private String id;
    @Indexed
    private String namespace;
    @Indexed
    private String ip;
    @Indexed
    private String level;
    @Indexed
    private String content;
    @Indexed
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
