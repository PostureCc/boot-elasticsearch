package com.chan.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

public class RestClientConfig {

    public static final RestClient restClient = RestClient.builder(
            new HttpHost("jd", 9201, "http")
//            ,new HttpHost("localhost", 9201, "http")
    ).build();

}
