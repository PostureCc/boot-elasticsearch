package com.chan.controller.document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chan.config.ElasticSearchUtils;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.utils.GsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FNJKJ
 */
@RestController
public class DocumentController {

    @Autowired
    private ElasticSearchUtils elasticSearchUtils;

    private RestClient restClient = RestClient.builder(
            new HttpHost("jd", 9201, "http"))
            .build();

    @GetMapping("/addDoc")
    public void addDoc() {
        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()
                .id("199324")
                .uid("CH200730000483")
                .clientId("V20200309121811")
                .mobile("15599534932")
                .realName("李健")
                .oldBat("BT104802512SZHL191120123")
                .newBat("BT104802512SZHL191120456")
                .operatorId(23L)
                .operatorName("四优换电")
                .cabinetName("XM朴朴SM12店-01")
                .createTime("2020-07-30 12:25:15")
                .build();

        elasticSearchUtils.addDoc(ExchangeElectricVO.EXCHANGE_ELECTRIC, exchangeElectricVO.getId(), GsonUtils.GsonString(exchangeElectricVO));
    }

    @GetMapping("/search")
    public void search(String key, String value) {
        SearchResponse response = elasticSearchUtils.search(key, value, 0, 200, ExchangeElectricVO.EXCHANGE_ELECTRIC);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println("source " + hit.getSourceAsString());

            Object bean = GsonUtils.GsonToBean(hit.getSourceAsString(), ExchangeElectricVO.class);
            System.out.println("bean " + bean);
        }
    }

    @GetMapping("/delDoc")
    public void delDoc(String id) {
        elasticSearchUtils.deleteDoc(ExchangeElectricVO.EXCHANGE_ELECTRIC, id);
    }

    @GetMapping("/searchAll")
    public void searchAll() {
        SearchRequest searchRequest = new SearchRequest(ExchangeElectricVO.EXCHANGE_ELECTRIC);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(builder);
        SearchResponse response = null;
        try {
            response = elasticSearchUtils.restHighLevelClient().search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

    }

    @GetMapping("/boolQuery")
    public void boolQuery() {
        SearchRequest request = new SearchRequest(ExchangeElectricVO.EXCHANGE_ELECTRIC);

        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("id", "199324");
        boolQueryBuilder.must(termsQueryBuilder);
        builder.query(boolQueryBuilder);

        request.source(builder);

        System.out.println("builder " + builder);
        try {
            SearchResponse response = elasticSearchUtils.restHighLevelClient().search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
