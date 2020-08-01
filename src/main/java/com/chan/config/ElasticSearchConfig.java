//package com.chan.config;
//
//import lombok.extern.log4j.Log4j2;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.Node;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Log4j2
//@Component
//public class ElasticSearchConfig {
//
//
//    @Value("${elasticsearch.host}")
//    private String host;
//    @Value("${elasticsearch.port}")
//    private int port;
//    @Value("${elasticsearch.scheme}")
//    private String scheme;
//    @Value("${elasticsearch.token}")
//    private String token;
//    @Value("${elasticsearch.charset}")
//    private String charSet;
//    @Value("${elasticsearch.client.connectTimeOut}")
//    private int connectTimeOut;
//    @Value("${elasticsearch.client.socketTimeout}")
//    private int socketTimeout;
//
//    @Bean
//    public RestClientBuilder restClientBuilder() {
//        RestClientBuilder restClientBuilder = RestClient.builder(
//                new HttpHost(host, port, scheme)
//        );
//
////        Header[] defaultHeaders = new Header[]{
////                new BasicHeader("Accept", "*/*"),
////                new BasicHeader("Charset", charSet),
////                //设置token 是为了安全 网关可以验证token来决定是否发起请求 我们这里只做象征性配置
////                new BasicHeader("E_TOKEN", token)
////        };
////        restClientBuilder.setDefaultHeaders(defaultHeaders);
//
//        restClientBuilder.setFailureListener(new RestClient.FailureListener() {
//            @Override
//            public void onFailure(Node node) {
//                log.info("监听某个es节点失败 :{}", node);
//            }
//        });
//        restClientBuilder.setRequestConfigCallback(builder ->
//                builder.setConnectTimeout(connectTimeOut).setSocketTimeout(socketTimeout));
//        return restClientBuilder;
//    }
//
//    @Bean("restHighLevelClient")
//    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
//        return new RestHighLevelClient(restClientBuilder);
//    }
//
//}
