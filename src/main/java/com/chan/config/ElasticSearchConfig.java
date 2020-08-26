package com.chan.config;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

@Log4j2
public class ElasticSearchConfig {

    private static volatile RestClientBuilder restClientBuilder;

    private static volatile RestClient restClient;

    private static volatile RestHighLevelClient restHighLevelClient;

    /**
     * 初始化 RestClientBuilder
     */
    public static RestClientBuilder getRestClientBuilder() {
        if (restClientBuilder == null) {
            synchronized (ElasticSearchConfig.class) {
                if (restClientBuilder == null) {
                    RestClientBuilder builder = RestClient.builder(
                            new HttpHost("jd", 9201, "http")
                    );
                    builder.setFailureListener(new RestClient.FailureListener() {
                        @Override
                        public void onFailure(Node node) {
                            log.error("Node Exception: {}", node);
                        }
                    });

                    restClientBuilder = builder;
                }
            }
        }
        return restClientBuilder;
    }

    /**
     * 初始化 RestClient
     */
    public static RestClient getRestClient() {
        if (restClient == null) {
            synchronized (ElasticSearchConfig.class) {
                if (restClient == null) {
                    restClient = getRestClientBuilder().build();
                }
            }
        }
        return restClient;
    }

    /**
     * 初始化 RestHighLevelClient
     */
    public static RestHighLevelClient getRestHighLevelClient() {

        if (restHighLevelClient == null) {
            synchronized (ElasticSearchConfig.class) {
                if (restHighLevelClient == null) {
                    restHighLevelClient = new RestHighLevelClient(getRestClientBuilder());
                }
            }
        }
        return restHighLevelClient;
    }


}
