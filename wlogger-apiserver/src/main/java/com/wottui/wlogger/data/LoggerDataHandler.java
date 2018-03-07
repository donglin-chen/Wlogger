package com.wottui.wlogger.data;

import com.alibaba.fastjson.JSON;
import com.wottui.wlogger.common.WLoggerInfo;
import com.wottui.wlogger.core.ILoggerDataDealTools;
import com.wottui.wlogger.core.LoggerDataDealTools;
import com.wottui.wlogger.core.WLoggerData;
import com.wottui.wlogger.vo.QueryVO;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wottui.wlogger.core.WLoggerEnv.DEFAULT_NAMESPACE;
import static com.wottui.wlogger.utils.SnowFlake.SNOW_FLAKE;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:07
 */
@Component
public class LoggerDataHandler implements ILoggerDataHandler {

    private static ILoggerDataDealTools tools = new LoggerDataDealTools();
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerDataHandler.class);
    @Resource
    private TransportClient transportClient;

    @Override
    @Async
    public void upload(String text) {
        WLoggerData data = tools.revert(text);
        //WLoggerData data = new WLoggerData();
        //data.setNamespace("default");
        String indexName = this.getIndexName(data.getNamespace());
        //judge index("wlogger_info_index_"+namespace) is exist, not so create it
        if (!this.isExistIndex(indexName)) {
            this.createIndex(indexName);
        }
        this.saveWLoggerInfo(data);
        LOGGER.debug("WLoggerInfo save success!!!");
    }

    @Override
    public List<WLoggerInfo> logConsole(QueryVO vo) {
        String namespace = StringUtils.isEmpty(vo.getNamespace()) ? DEFAULT_NAMESPACE : vo.getNamespace();
        String indexName = this.getIndexName(namespace);
        if (!this.isExistIndex(indexName))
            return null;
        String level = vo.getLevel();
        Long startAt = vo.getStartAt();
        Long endAt = vo.getEndAt();
        String like = vo.getLike();
        String ip = vo.getIp();
        Integer limit = vo.getLimit();
        if (limit == 0)
            limit = 100;
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(level)) {
            qb.must(QueryBuilders.matchQuery("level", level));
        }
        if (!StringUtils.isEmpty(ip)) {
            qb.must(QueryBuilders.matchQuery("ip", ip));
        }
        if (startAt != null && endAt != null && endAt >= startAt) {
            qb.must(QueryBuilders.rangeQuery("timestamp").lte(endAt).gte(startAt));
        }
        if (!StringUtils.isEmpty(like)) {
            qb.must(QueryBuilders.fuzzyQuery("content", like));
        }
        SearchResponse response = transportClient.prepareSearch(indexName).setQuery(qb).setFrom(0).setSize(limit)
                                                 .addSort("timestamp", SortOrder.DESC).get();
        List<WLoggerInfo> retList = new ArrayList<>();
        SearchHits searchHits = response.getHits();
        if (searchHits == null)
            return null;
        SearchHit[] sourceArr = searchHits.getHits();
        if (sourceArr.length == 0)
            return null;
        int len = sourceArr.length;
        for (int i = len - 1; i >= 0; i--) {
            String source = sourceArr[i].getSourceAsString();
            WLoggerInfo info = JSON.toJavaObject(JSON.parseObject(source), WLoggerInfo.class);
            retList.add(info);
        }

        return retList;
    }

    /**
     * 判断是否存在索引
     *
     * @param indexName
     * @return
     */
    private boolean isExistIndex(String indexName) {
        IndicesExistsResponse response = transportClient.admin().indices().exists(new IndicesExistsRequest()
                .indices(indexName)).actionGet();
        return response.isExists();
    }

    /**
     * 获取index
     *
     * @param namespace = APPName
     * @return
     */
    private String getIndexName(String namespace) {
        return ("wlogger_info_" + namespace).toLowerCase();
    }

    private boolean createIndex(String indexName) {
        CreateIndexRequestBuilder builder = transportClient.admin().indices().prepareCreate(indexName);
        return builder.execute().actionGet().isAcknowledged();
    }

    //@PostConstruct
    public void deleteIndexTest() {
        DeleteIndexResponse dResponse = transportClient.admin().indices().prepareDelete("wlogger_info_default")
                                                       .execute().actionGet();
    }

    /**
     * 新增WLogger
     *
     * @param data 日志数据体
     */
    private void saveWLoggerInfo(WLoggerData data) {
        try {
            String indexName = this.getIndexName(data.getNamespace());
            BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
            bulkRequest.add(transportClient.prepareIndex(indexName, indexName, SNOW_FLAKE.nextId() + "")
                                           .setSource(XContentFactory.jsonBuilder()
                                                                     .startObject()
                                                                     .field("namespace", data.getNamespace())
                                                                     .field("timestamp", data.getTimestamp())
                                                                     .field("level", data.getLevel())
                                                                     .field("ip", data.getIp())
                                                                     .field("content", data.getContent()).endObject())
            );
            BulkResponse bulkResponse = bulkRequest.get();
            if (bulkResponse.hasFailures()) {
                LOGGER.error("Save wlogger info failed");
            }

        } catch (IOException e) {
            LOGGER.error("Save wlogger info failed", e);
        }
    }

}
