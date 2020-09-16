package com.chan.controller.document;

import com.chan.config.ElasticSearchConfig;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.utils.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class RestClientController {

    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @GetMapping("/save")
    public void save() throws IOException {/*
        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()
                .id(218118L)
                .uid("CH200730000483")
                .clientId("V20200309121811")
                .mobile("15599534932")
                .realName("李健")
                .oldBat("BT104802512SZHL191120274")
                .newBat("BT104802512SZHL200401653")
                .operatorId(23L)
                .operatorName("四优换电")
                .cabinetName("XM朴朴SM12店-02")
                .createTime("2020-08-06 16:29:40")
                .build();

        //新增时 如果不在索引后面跟ID 则用ElasticSearch默认的ID值,如果跟了ID 则用自定义的ID值
        //Request request = new Request("POST", "/bcadmin_exchange_electric/_doc/" + exchangeElectricVO.getId());
        Request request = new Request("POST", "/bcadmin_exchange_electric/_doc/" + exchangeElectricVO.getId());
        request.setJsonEntity(GsonUtils.GsonString(exchangeElectricVO));

        Response response = ElasticSearchConfig.getRestClient().performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));

    */
    }

    @GetMapping("/update")
    public void update() throws IOException {
        /**
         * _doc:索引类型
         * 218118:文档ID
         * _update:语法标记
         * */
        String index = "/bcadmin_exchange_electric/_doc/218118/_update";

        Request request = new Request("POST", index);
        request.setJsonEntity("{\"doc\":{\"realName\":\"李健2\"}}");

        Response response = elasticSearchConfig.getRestClient().performRequest(request);

        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @GetMapping("/updateByQuery")
    public void updateByQuery() throws IOException {
        //慎用 有警告
        String index = "/bcadmin_exchange_electric/_update_by_query";

        Request request = new Request("POST", index);
        request.setJsonEntity("{\"query\":{\"term\":{\"id\":\"218118\"}},\"script\":{\"inline\":\"ctx._source.realName='李健';ctx._source.createTime='2020-08-06 16:29:50'\",\"lang\":\"painless\"}}");

        Response response = elasticSearchConfig.getRestClient().performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @GetMapping("/matchAll")
    public void matchAll() throws IOException {
        Request request = new Request("POST", "/bcadmin_exchange_electric/_search");

        String queryString = "{\"query\":{\"match_all\":{}}}";
        request.setJsonEntity(queryString);

        Response response = elasticSearchConfig.getRestClient().performRequest(request);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println("string " + string);
    }

    @GetMapping("/search")
    public void search(String cabinetName) throws IOException {
        Request request = new Request("POST", "/bcadmin_exchange_electric/_search");

        String queryString = String.format("{\"query\":{\"match\":{\"cabinetName\":\"%s\"}},\"highlight\":{\"fields\":{\"cabinetName\":{}}}}", cabinetName);
        request.setJsonEntity(queryString);

        Response response = elasticSearchConfig.getRestClient().performRequest(request);

        System.out.println("RequestLine " + response.getRequestLine());

        String string = EntityUtils.toString(response.getEntity());
        System.out.println("string " + string);
    }

    @GetMapping("/count")
    public void count() {
        Request request = new Request("POST", "/bcadmin_exchange_electric/_count");

        try {
            Response response = elasticSearchConfig.getRestClient().performRequest(request);

            HashMap<String, Object> hashMap = GsonUtils.GsonToBean(EntityUtils.toString(response.getEntity()), HashMap.class);
            System.out.println(hashMap.get("count"));

            elasticSearchConfig.getRestClient().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/groupSearch")
    public void groupSearch(Long operatorId) {

        Request request = new Request("POST", "/bcadmin_exchange_electric/_search");

        String queryString = String.format("{\"size\":0,\"query\":{\"term\":{\"operatorId\":{\"value\":%s,\"boost\":1}}},\"aggregations\":{\"mobile\":{\"terms\":{\"field\":\"mobile\",\"size\":500000,\"order\":[{\"_count\":\"desc\"},{\"_key\":\"asc\"}]}}}}", operatorId);
        request.setJsonEntity(queryString);

        Response response = null;
        try {
            response = elasticSearchConfig.getRestClient().performRequest(request);

            String totalData = EntityUtils.toString(response.getEntity());
            System.out.println(totalData);

            Map<String, Object> gsonToMaps = GsonUtils.GsonToMaps(totalData);

            Map<String, Object> aggregations = GsonUtils.GsonToMaps(GsonUtils.GsonString(gsonToMaps.get("aggregations")));
            System.out.println(aggregations);

            Map<String, Object> mobileMap = GsonUtils.GsonToMaps(GsonUtils.GsonString(aggregations.get("mobile")));

            ArrayList<Map<String, Object>> buckets = GsonUtils.GsonToBean(GsonUtils.GsonString(mobileMap.get("buckets")), new ArrayList<Map<String, Object>>().getClass());
            System.out.println(buckets);
            buckets.stream().forEach(item ->
                    log.info("key:{} value:{}", item.get("key"), item.get("doc_count"))
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        RestClientController controller = new RestClientController();
        //controller.groupSearch(23L);

//        controller.count();

        RestHighLevelClientController restHighLevelClientController = new RestHighLevelClientController();
        restHighLevelClientController.search2(1, 20);

    }

}
