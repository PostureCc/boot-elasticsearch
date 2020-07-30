package com.chan.controller.document;

import com.alibaba.fastjson.JSONObject;
import com.chan.config.ElasticSearchUtils;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.utils.GsonUtils;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: Chan
 * @Date: 2020/6/10 17:21
 * @Description:
 */
@Log4j2
@RestController
public class DocumentController {

    @Autowired
    @Qualifier(value = "restHighLevelClient")
    private RestHighLevelClient client;

    @Autowired
    private ElasticSearchUtils elasticSearchUtils;

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

        elasticSearchUtils.addDoc(ExchangeElectricVO.EXCHANGE_ELECTRIC, exchangeElectricVO.getId().toString(), GsonUtils.GsonString(exchangeElectricVO));
    }

    @GetMapping("/bulkAdd")
    public void bulkAdd() {
        List<ExchangeElectricVO> list = Arrays.asList(
                ExchangeElectricVO.builder()
                        .id("200135")
                        .uid("CH200730001295")
                        .clientId("V20200423031635")
                        .mobile("15080053301")
                        .realName("吴发发")
                        .oldBat("BT104802512SZHL200323003")
                        .newBat("BT104802512SZHL200323391")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("FZ朴朴华建41店-01")
                        .createTime("2020-07-30 16:15:08")
                        .build(),
                ExchangeElectricVO.builder()
                        .id("200134")
                        .uid("CH200730001294")
                        .clientId("V20200709083301")
                        .mobile("13101437586")
                        .realName("张辉")
                        .oldBat("BT104802512ST00191130345")
                        .newBat("BT104802512ST00191130382")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("XM朴朴西亭33店-01")
                        .createTime("2020-07-30 16:14:59")
                        .build(),
                ExchangeElectricVO.builder()
                        .id("200133")
                        .uid("CH200730001293")
                        .clientId("V20200603152601")
                        .mobile("13276962231")
                        .realName("谢燕飞")
                        .oldBat("BT104802512SZHL200323061")
                        .newBat("BT104802512SZHL200401899")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("XM饿了么江头站-01安宝大厦")
                        .createTime("2020-07-31 16:14:50")
                        .build(),
                ExchangeElectricVO.builder()
                        .id("200132")
                        .uid("CH200730001291")
                        .clientId("V20200701133901")
                        .mobile("15970932874")
                        .realName("成雄")
                        .oldBat("BT104802512SZHL200401887")
                        .newBat("BT104802512ST00191130862")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("XM饿了么人才站-01育秀里")
                        .createTime("2020-07-31 16:12:36")
                        .build()
        );

        //添加请求
        BulkRequest request = new BulkRequest();
        for (ExchangeElectricVO item : list) {
            request.add(
                    new IndexRequest(ExchangeElectricVO.EXCHANGE_ELECTRIC)
                            .id(item.getId())
                            .source(GsonUtils.GsonString(item), XContentType.JSON)
            );
        }

        try {
            //发送请求
            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }

    }

    @GetMapping("/updateDoc")
    public void updateDoc() {
        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()
                .id("199323")
                .createTime("2020-07-30 18:24:15")
                .build();

        elasticSearchUtils.updateDoc(ExchangeElectricVO.EXCHANGE_ELECTRIC, exchangeElectricVO.getId().toString(), GsonUtils.GsonToMaps(GsonUtils.GsonString(exchangeElectricVO)));
    }

    @GetMapping("/delDoc")
    public void delDoc(String id) {
        elasticSearchUtils.deleteDoc(ExchangeElectricVO.EXCHANGE_ELECTRIC, id);
    }

    @GetMapping("/search")
    public void search(String key, String value) {
//        SearchResponse response = elasticSearchUtils.search("mobile", "15599534932", 1, 20, ExchangeElectricVO.EXCHANGE_ELECTRIC);
        SearchResponse response = elasticSearchUtils.search(key, value, 0, 20, ExchangeElectricVO.EXCHANGE_ELECTRIC);
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();

        for (SearchHit fields : hits1) {
            System.out.println(fields.getSourceAsString());
        }

//        hits.forEach(item -> System.out.println(item.getSourceAsString()));
    }

    @GetMapping("/term")
    public void term(String key, String value) {
//        SearchResponse response = elasticSearchUtils.search("mobile", "15599534932", 1, 20, ExchangeElectricVO.EXCHANGE_ELECTRIC);
        SearchResponse response = elasticSearchUtils.termSearch(key, value, 0, 20, ExchangeElectricVO.EXCHANGE_ELECTRIC);
        SearchHits hits = response.getHits();
        hits.forEach(item -> System.out.println(item.getSourceAsString()));
    }

}
