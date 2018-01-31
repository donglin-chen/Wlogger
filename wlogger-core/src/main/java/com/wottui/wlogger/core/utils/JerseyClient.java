package com.wottui.wlogger.core.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.wottui.wlogger.core.API;
import com.wottui.wlogger.core.ILoggerDataDealTools;
import com.wottui.wlogger.core.LoggerDataDealTools;
import com.wottui.wlogger.core.WLoggerData;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 15:58
 */
public class JerseyClient {

    public static String post(String URL, Map<String, String> params) {
        Client c = Client.create();
        c.setConnectTimeout(3000);
        c.setReadTimeout(3000);
        WebResource r = c.resource(URL);
        Form form = new Form();
        if (params != null && params.size() != 0) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                form.add(key, value);
            }
        }
        ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
        return response.getEntity(String.class);
    }

    public static void main(String[] args) throws Throwable {
        ILoggerDataDealTools tools = new LoggerDataDealTools();
        while (true) {
            Map<String, String> params = new HashMap<>();
           /* JSONObject jsonObject = new JSONObject();
            jsonObject.put("level", "DEBUG");
            jsonObject.put("namespace", "default");
            jsonObject.put("content", "connectionId" + System.currentTimeMillis());*/
            WLoggerData data = new WLoggerData();
            data.setLevel("DEBUG");
            data.setTimestamp(System.currentTimeMillis());
            data.setContent("log+" + UUID.randomUUID().toString());
            params.put("apitype", API.LOG_UPLOAD.name());

            params.put("text", tools.deal(data));
            String text = JerseyClient.post("http://127.0.0.1:8080/wlogger/api/gateway", params);
            System.out.println(text);
            Thread.sleep(1000);
        }
    }
}
