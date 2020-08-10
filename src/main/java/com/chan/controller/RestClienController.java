package com.chan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chan.config.RestClientConfig;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
public class RestClienController {

    @GetMapping("/restSearch")
    public void search() throws IOException {
        Map<String, String> params = Collections.emptyMap();

        String queryString = "{\"query\":{\"bool\":{\"must\":[{\"match\":{\"clientId\":\"V20200309121811\"}}]}}}";

        HttpEntity entity = new NStringEntity(queryString, ContentType.APPLICATION_JSON);

        try {

            Response response = RestClientConfig.restClient.performRequest("GET", "/bcadmin/_search", params, entity);
            System.out.println(response.getStatusLine().getStatusCode());
            String responseBody = null;

            responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("******************************************** ");

            JSONObject jsonObject = JSON.parseObject(responseBody);


            System.out.println(jsonObject.get("hits"));
        } catch (ResponseException e) {
            e.printStackTrace();
        }

    }

}
