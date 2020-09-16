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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Chan
 * @time 2020-09-14
 */
@Log4j2
@Configuration
@ConditionalOnClass(ElasticSearchConfig.class)
public class ElasticSearchConfig {
    public static volatile RestClient restClient = null;
    public static volatile RestHighLevelClient restHighLevelClient = null;
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String _DCO = "_doc";
    public static final Integer SIZE = 500000;


    @Value("${elasticsearch.hosts}")
    public List<String> elasticHosts;
    @Value("${elasticsearch.port}")
    public Integer elasticPort;
    @Value("${elasticsearch.scheme}")
    public String elasticScheme;
    @Value("${elasticsearch.username}")
    public String elasticUserName;
    @Value("${elasticsearch.password}")
    public String elasticPassWord;

    /**
     * 初始化 RestClientBuilder
     */
    public RestClientBuilder getRestClientBuilder() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticUserName, elasticPassWord));

        Integer length = elasticHosts.size();
        HttpHost[] hosts = new HttpHost[length];
        for (int i = 0; i < length; i++) {
            hosts[i] = new HttpHost(elasticHosts.get(i), elasticPort, elasticScheme);
        }

        RestClientBuilder builder = RestClient.builder(hosts);

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

        System.err.println("builder " + builder);
        return builder;
    }

    /**
     * 初始化 RestClient
     */
    @Bean(destroyMethod = "close")
    public RestClient getRestClient() {
//        if (null == restClient) {
//            synchronized (this) {
//                if (null == restClient) {
//                    restClient = getRestClientBuilder().build();
//                }
//            }
//        }
//        return restClient;
        return getRestClientBuilder().build();
    }

    /**
     * 初始化 RestHighLevelClient
     */
    @Bean(destroyMethod = "close")
    public RestHighLevelClient getRestHighLevelClient() {
//        if (null == restHighLevelClient) {
//            synchronized (this) {
//                if (null == restHighLevelClient) {
//                    restHighLevelClient = new RestHighLevelClient(getRestClientBuilder());
//                }
//            }
//        }
//        return restHighLevelClient;
        return new RestHighLevelClient(getRestClientBuilder());
    }
}