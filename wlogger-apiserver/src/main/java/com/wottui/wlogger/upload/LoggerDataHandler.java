package com.wottui.wlogger.upload;

import com.wottui.wlogger.common.WLoggerInfo;
import com.wottui.wlogger.core.ILoggerDataDealTools;
import com.wottui.wlogger.core.LoggerDataDealTools;
import com.wottui.wlogger.core.WLoggerData;
import com.wottui.wlogger.vo.QueryVO;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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
        data.setNamespace("default");
        String indexName = this.getIndexName(data.getNamespace());
        //judge index("wlogger_info_index_"+namespace) is exist, not so create it
        if (!this.isExistIndex(indexName)) {
            this.createIndex(indexName);
        }
        this.saveWLoggerInfo(data);
        LOGGER.debug("WLoggerInfo save success!!!");
    }

    @Override public List<WLoggerInfo> logConsole(QueryVO vo) {
        return null;
    }

    /*@PostConstruct
    public void test() {
        upload("");
    }*/

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
        return "wlogger_info_" + namespace;
    }

    private boolean createIndex(String indexName) {
        CreateIndexRequestBuilder builder = transportClient.admin().indices()
                                                           .prepareCreate(indexName);
        return builder.execute().actionGet().isAcknowledged();
    }

    /**
     * 新增WLogger
     *
     * @param data 日志数据体
     */
    private void saveWLoggerInfo(WLoggerData data) {
        try {
            String indexName = data.getNamespace();
            transportClient.prepareIndex(indexName, indexName, "2").setSource(
                    jsonBuilder().startObject().field("namespace", data.getNamespace())
                                 .field("timestamp", data.getTimestamp())
                                 .field("content", data.getContent()).endObject())
                           .execute().actionGet();
        } catch (IOException e) {
            LOGGER.error("Save wlogger info failed", e);
        }
    }

   /* public static void main(String[] args) {
        Client client = initElasticSearchClient();
        String esIndex = "graylog_0";
        SearchResponse searchResponse = client.prepareSearch(esIndex)
                                              .setTypes("graylog_0")
                                              .setSearchType(SearchType.QUERY_AND_FETCH)
                                              .setFetchSource(new String[]{"the_field_you_want_to_fetch"}, null)
                                              .setQuery(QueryBuilders.termsQuery("your_query_key", "your_query_value"))
                                              .execute()
                                              .actionGet();
        int count = 0;
        for (SearchHit hit : searchResponse.getHits()) {
            Map map = hit.getSource();
            if (count < 3) {
                System.out.println(map);
            } else {
                break;
            }
            ++count;
        }
    }
    private static TransportClient initElasticSearchClient() {
        try {
            Settings settings = Settings.builder().put("cluster.name", "chendl_es_cluster").build();
            TransportClient transportClient = new PreBuiltTransportClient(settings);
            return transportClient
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.18.97.148"), 19300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }

    }*/
}
