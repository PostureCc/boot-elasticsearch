package com.chan.config;


import lombok.extern.log4j.Log4j2;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Log4j2
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.scheme}")
    private String scheme;
    @Value("${elasticsearch.token}")
    private String token;
    @Value("${elasticsearch.charset}")
    private String charSet;
    @Value("${elasticsearch.client.connectTimeOut}")
    private int connectTimeOut;
    @Value("${elasticsearch.client.socketTimeout}")
    private int socketTimeout;

    @Override
    public RestHighLevelClient elasticsearchClient() {
//        RestClientBuilder restClientBuilder = RestClient.builder(
//                new HttpHost(host, port, scheme)
//        );
//
//        restClientBuilder.setFailureListener(new RestClient.FailureListener() {
//            @Override
//            public void onFailure(Node node) {
//                log.info("监听某个es节点失败 :{}", node);
//            }
//        });
//        restClientBuilder.setRequestConfigCallback(builder ->
//                builder.setConnectTimeout(connectTimeOut).setSocketTimeout(socketTimeout));

//        return new RestHighLevelClient(restClientBuilder);

//        return RestClients.create(ClientConfiguration.create("jd:9201")).rest();

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("jd:9201")                    // es的http连接地址
//                .withBasicAuth("username", "password")    // 如果开启了用户名密码验证，则需要加上
                .build();


        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    ElasticsearchRestTemplate elasticsearchRestTemplate(@Autowired RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRestTemplate(restHighLevelClient);
    }

}
