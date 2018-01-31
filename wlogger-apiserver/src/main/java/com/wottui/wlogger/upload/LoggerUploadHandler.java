package com.wottui.wlogger.upload;

import com.wottui.wlogger.common.WLoggerInfo;
import com.wottui.wlogger.core.ILoggerDataDealTools;
import com.wottui.wlogger.core.LoggerDataDealTools;
import com.wottui.wlogger.core.WLoggerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:07
 */
@Component
public class LoggerUploadHandler implements ILoggerUploadHandler {
    private static ILoggerDataDealTools tools = new LoggerDataDealTools();
    private static Logger logger = LoggerFactory.getLogger(LoggerUploadHandler.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Async("myAsync")
    public void upload(String text) {
        WLoggerData data = tools.revert(text);
        WLoggerInfo info = new WLoggerInfo();
        info.setLevel(data.getLevel());
        info.setContent(data.getContent());
        info.setTimestamp(data.getTimestamp());
        info.setIp(data.getIp());
        info.setContent(info.getContent());
        info.setNamespace(data.getNamespace());
        mongoTemplate.save(info);
        logger.debug("WLoggerInfo save success!!!");
    }
}
