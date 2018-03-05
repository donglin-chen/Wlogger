package com.wottui.wlogger.conf;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 由于用spring data elasticsearch jar包依赖有点恶心，所有自己封装了一个
 *
 * @Author: 1556964226@qq.com
 * @Date: 2018/3/5
 * @Time: 16:21
 */
@Configuration
public class ElasticsearchClientConf {

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;
    @Value("${elasticsearch.cluster.nodes}")
    private String clusterNodes;
    private static final Logger LOGGER = LoggerFactory.getLogger(Object.class);

    @Bean
    public TransportClient client() {
        LOGGER.info("Elasticsearch client initializing...");
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        if (StringUtils.isEmpty(clusterNodes))
            throw new RuntimeException("Elasticsearch nodes can't empty");
        String[] nodeArr = clusterNodes.split(",");
        if (nodeArr.length == 0)
            throw new RuntimeException("Elasticsearch node format is error!!!");
        List<InetSocketTransportAddress> inetSocketTransportAddressList = new ArrayList<>();
        for (String node : nodeArr) {
            String[] nodeConfArr = node.split(":");
            try {
                inetSocketTransportAddressList
                        .add(new InetSocketTransportAddress(InetAddress.getByName(nodeConfArr[0].trim()), Integer
                                .parseInt(nodeConfArr[1].trim())));
            } catch (Throwable e) {
                throw new RuntimeException("Elasticsearch node format is error!!!");
            }
        }
        TransportClient retClient = transportClient.addTransportAddresses(inetSocketTransportAddressList
                .toArray(new InetSocketTransportAddress[inetSocketTransportAddressList.size()]));
        LOGGER.info("Elasticsearch client success");
        return retClient;
    }


}
