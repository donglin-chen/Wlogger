package com.wottui.wlogger.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 数据处理工具类
 * <p>
 * zip(Base64(WLoggerData))
 */
public class LoggerDataDealTools implements ILoggerDataDealTools {
    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    @Override
    public String deal(WLoggerData wLoggerData) {
        String jsonStr = JSON.toJSONString(wLoggerData);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GZIPOutputStream gout = null;
        try {
            gout = new GZIPOutputStream(bout);
            gout.write(jsonStr.getBytes());
            gout.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (gout != null)
                    gout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encoder.encode(bout.toByteArray());
    }

    @Override
    public WLoggerData revert(String data) {
        if (data == null)
            return null;
        ByteArrayOutputStream out = null;
        try {
            byte[] bytes = decoder.decodeBuffer(data);
            out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            GZIPInputStream gzipInputStream = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        String eStr = new String(out.toByteArray());
        return JSONObject.toJavaObject(JSON.parseObject(eStr), WLoggerData.class);
    }

    public static void main(String[] args) throws Throwable {
        WLoggerData data = new WLoggerData();
        LoggerDataDealTools tools = new LoggerDataDealTools();
        data.setContent("msfhsifhsifs");
        String content = tools.deal(data);
        System.out.println(content);
        WLoggerData d = tools.revert(content);
        System.out.println(JSON.toJSONString(d));
    }
}
