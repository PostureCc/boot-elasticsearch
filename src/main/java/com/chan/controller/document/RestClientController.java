package com.chan.controller.document;

import com.chan.config.ElasticSearchConfig;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.utils.GsonUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RestClientController {

    @GetMapping("/save")
    public void save() throws IOException {
        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()
                .id("218118")
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

        Response response = ElasticSearchConfig.getRestClient().performRequest(request);

        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @GetMapping("/updateByQuery")
    public void updateByQuery() throws IOException {
        //慎用 有警告
        String index = "/bcadmin_exchange_electric/_update_by_query";

        Request request = new Request("POST", index);
        request.setJsonEntity("{\"query\":{\"term\":{\"id\":\"218118\"}},\"script\":{\"inline\":\"ctx._source.realName='李健';ctx._source.createTime='2020-08-06 16:29:50'\",\"lang\":\"painless\"}}");

        Response response = ElasticSearchConfig.getRestClient().performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @GetMapping("/matchAll")
    public void matchAll() throws IOException {
        Request request = new Request("POST", "/bcadmin_exchange_electric/_search");

        String queryString = "{\"query\":{\"match_all\":{}}}";
        request.setJsonEntity(queryString);

        Response response = ElasticSearchConfig.getRestClient().performRequest(request);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println("string " + string);
    }

    @GetMapping("/search")
    public void search(String cabinetName) throws IOException {
        Request request = new Request("POST", "/bcadmin_exchange_electric/_search");

        String queryString = String.format("{\"query\":{\"match\":{\"cabinetName\":\"%s\"}},\"highlight\":{\"fields\":{\"cabinetName\":{}}}}", cabinetName);
        request.setJsonEntity(queryString);

        Response response = ElasticSearchConfig.getRestClient().performRequest(request);

        System.out.println("RequestLine " + response.getRequestLine());

        String string = EntityUtils.toString(response.getEntity());
        System.out.println("string " + string);
    }

}
