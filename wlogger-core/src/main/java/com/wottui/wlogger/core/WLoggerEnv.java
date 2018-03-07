package com.wottui.wlogger.core;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/3/5
 * @Time: 18:34
 */
public class WLoggerEnv {

    public static final WLoggerEnv e = new WLoggerEnv();
    private String namespace;
    private String url;
    public static final String DEFAULT_NAMESPACE = "default";

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
