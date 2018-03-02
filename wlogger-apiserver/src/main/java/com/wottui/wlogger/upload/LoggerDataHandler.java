package com.wottui.wlogger.upload;

import com.wottui.wlogger.common.WLoggerInfo;
import com.wottui.wlogger.core.ILoggerDataDealTools;
import com.wottui.wlogger.core.LoggerDataDealTools;
import com.wottui.wlogger.core.WLoggerData;
import com.wottui.wlogger.vo.QueryVO;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @Author: 1556964226@qq.com
 * @Date: 2018/1/29
 * @Time: 14:07
 */
@Component
public class LoggerDataHandler implements ILoggerDataHandler {

    private static ILoggerDataDealTools tools = new LoggerDataDealTools();
    private static Logger logger = LoggerFactory.getLogger(LoggerDataHandler.class);

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    @Async
    public void upload(String text) {
        logger.error("ressrsrer");
        //WLoggerData data = tools.revert(text);
        WLoggerData data = new WLoggerData();
        data.setNamespace("default");
        String indexName = this.getIndexName(data.getNamespace());
        //judge index("wlogger_info_index_"+namespace) exist
        if (!this.isExistIndex(indexName)) {
            this.createIndex(indexName);
        }
        this.saveWLoggerInfo(data);
        logger.debug("WLoggerInfo save success!!!");
    }

    @Override public List<WLoggerInfo> logConsole(QueryVO vo) {
        return null;
    }

    @PostConstruct
    public void test() {
        upload("");
    }

    /**
     * 判断是否存在索引
     *
     * @param indexName
     * @return
     */
    private boolean isExistIndex(String indexName) {
       /* IndicesExistsResponse response = elasticsearchTemplate.getClient().admin().indices()
                                                              .exists(new IndicesExistsRequest()
                                                                      .indices(new String[]{indexName})).actionGet();
        return response.isExists();*/
        return true;
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
       /* CreateIndexRequestBuilder builder = elasticsearchTemplate.getClient().admin().indices()
                                                                 .prepareCreate(indexName);
        return builder.execute().actionGet().isAcknowledged();*/
        return true;
    }

    /**
     * 新增WLogger
     *
     * @param data 日志数据体
     */
    private void saveWLoggerInfo(WLoggerData data) {
        /*try {
            String indexName = data.getNamespace();
            elasticsearchTemplate.getClient().prepareIndex(indexName, indexName, "1").setSource(
                    jsonBuilder().startObject().field("user", "kimchy")
                                 .field("postDate", new Date())
                                 .field("message", "trying out Elasticsearch").endObject())
                                 .execute().actionGet();
        } catch (IOException e) {
            logger.error("Save wlogger info failed", e);
        }*/
    }


    public static void main(String[] args) {

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
            Settings settings = new Settings.Builder().put("cluster.name", "mob71").build();
            TransportClient transportClient = TransportClient.builder().settings(settings).build();
            return transportClient
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.18.97.71"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

}
