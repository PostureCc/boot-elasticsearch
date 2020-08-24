package com.chan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chan.config.RestClientConfig;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/analyzer")
public class AnalyzerController {

    @GetMapping("/cabinetNameSearch")
    public void cabinetNameSearch(String cabinetName) {

        Map<String, String> params = Collections.emptyMap();

        String queryString = String.format("{\"query\":{\"match\":{\"cabinetName\":\"%s\"}},\"highlight\":{\"fields\":{\"cabinetName\":{}}}}", cabinetName);

        HttpEntity entity = new NStringEntity(queryString, ContentType.APPLICATION_JSON);

        try {

            Response response = RestClientConfig.restClient.performRequest("POST", "/bcadmin_exchange_electric/_search", params, entity);
            System.out.println(response.getStatusLine().getStatusCode());
            String responseBody = null;

            responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("******************************************** ");

            JSONObject jsonObject = JSON.parseObject(responseBody);

            System.out.println(jsonObject.get("hits"));
        } catch (ResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/search")
    public void search(String cabinetName) throws IOException {
        Request request = new Request("POST", "/bcadmin_exchange_electric/_search");

        String queryString = String.format("{\"query\":{\"match\":{\"cabinetName\":\"%s\"}},\"highlight\":{\"fields\":{\"cabinetName\":{}}}}", cabinetName);
        request.setJsonEntity(queryString);

        Response response = RestClientConfig.restClient.performRequest(request);

        System.out.println("RequestLine " + response.getRequestLine());

        String string = EntityUtils.toString(response.getEntity());
        System.out.println("string " + string);

    }

}