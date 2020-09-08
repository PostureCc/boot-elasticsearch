package com.chan.config;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

@Log4j2
public class ElasticSearchConfig {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String _DCO = "_doc";

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
                    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "elastic"));

                    RestClientBuilder builder = RestClient.builder(
//                            new HttpHost("fnjkj6", 9201, "http"),
//                            new HttpHost("fnjkj7", 9201, "http"),
//                            new HttpHost("fnjkj8", 9201, "http")
                            new HttpHost("jd", 9201, "http")
                    );

                    builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                            //默认为1S 重置为3S
                            builder.setConnectTimeout(3000);
                            //默认30S 防止有耗时操作 重置为3M
                            builder.setSocketTimeout(180000);
                            return builder;
                        }
                    }).setFailureListener(new RestClient.FailureListener() {
                        @Override
                        public void onFailure(Node node) {
                            log.error("Node Exception: {}", node);
                        }
                    }).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {

                            return httpAsyncClientBuilder.disableAuthCaching().setDefaultCredentialsProvider(credentialsProvider);
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
