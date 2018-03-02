package com.wottui.wlogger.ui;

import com.wottui.wlogger.common.WLoggerInfo;
import com.wottui.wlogger.vo.QueryVO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/30
 * @Time: 10:32
 */
@Component
public class UIMongo implements IUI {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<WLoggerInfo> logConsole(QueryVO vo) {
       /* if (StringUtils.isEmpty(text))
            return null;
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(text);
        } catch (Throwable e) {
            return null;
        }
        String namespace = jsonObject.getString("namespace");
        String level = jsonObject.getString("level");
        Long startAt = jsonObject.getLong("startAt");
        Long endAt = jsonObject.getLong("endAt");
        String like = jsonObject.getString("like");
        String ip = jsonObject.getString("ip");
        Integer limit = jsonObject.getInteger("limit");
        if (limit == null)
            limit = 100;
        Query query = new Query();
        if (!StringUtils.isEmpty(namespace)) {
            query.addCriteria(Criteria.where("namespace").is(namespace));
        }
        if (!StringUtils.isEmpty(level)) {
            query.addCriteria(Criteria.where("level").is(level));
        }
        if (!StringUtils.isEmpty(ip)) {
            query.addCriteria(Criteria.where("ip").is(ip));
        }
        if (startAt != null && endAt != null && endAt >= startAt) {
            query.addCriteria(Criteria.where("timestamp").gte(startAt).lte(startAt));
        }
        if (!StringUtils.isEmpty(like)) {
            query.addCriteria(Criteria.where("content").regex(like));
        }
        query.limit(limit);
        query.with(new Sort(Sort.Direction.DESC, "timestamp"));
        List<WLoggerInfo> tempList = mongoTemplate.find(query, WLoggerInfo.class);
        List<WLoggerInfo> retList = new ArrayList<>();
        for (int i = tempList.size() - 1; i >= 0; i--) {
            retList.add(tempList.get(i));
        }
        return retList;*/
        return null;
    }
}
