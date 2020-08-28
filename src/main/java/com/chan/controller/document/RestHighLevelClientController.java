package com.chan.controller.document;

import com.chan.common.response.Message;
import com.chan.common.response.ResultUtils;
import com.chan.config.ElasticSearchConfig;
import com.chan.mapper.DeviceChangeMapper;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.utils.GsonUtils;
import com.chan.utils.PageNumAndSize;
import com.chan.utils.PageUtil;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/high")
public class RestHighLevelClientController {

    @Autowired
    private DeviceChangeMapper deviceChangeMapper;

    @GetMapping("/add")
    public void add() {
        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()
                .id("92434")
                .uid("CH202008270717")
                .clientId("2020081814024")
                .realName("王杰军")
                .mobile("C_CSFX:17688720252")
                .oldBat("BT10600251200LW200701098")
                .newBat("BT10600251200LW200701256")
                .operatorId(6L)
                .operatorName("长沙吉百物流有限公司(长沙风行,骑手港)")
                .cabinetId("CHZD12HJL0200524011")
                .cabinetName("优狐电动车（火焰市场5区11栋4号）")
                .createTime("2020-08-27 11:51:30")
                .build();

        //如果存在相同ID的数据 则修改 如果不存在相同ID的数据 则更新
        ////可通过response.getResult()判断 新增时是create 修改时是update
        IndexRequest request = new IndexRequest("bcadmin_exchange_electric", "_doc")
                //指定ID新增 如果不指定ID 则用ElasticSearch自动生成的ID
                .id(exchangeElectricVO.getId())
                .source(GsonUtils.GsonString(exchangeElectricVO), XContentType.JSON);

        try {
            IndexResponse response = ElasticSearchConfig.getRestHighLevelClient().index(request, RequestOptions.DEFAULT);

            log.info(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/bulkAdd")
    public void bulkAdd(int page, int size) throws IOException {

        //创建批量请求对象
        BulkRequest request = new BulkRequest();

        List<ExchangeElectricVO> list = deviceChangeMapper.list(page, size);

        //无事务操作 例如新增100条数据 在新增到第99条数据时失败 不会回退
        //如果100条数据里 有20条数据的ID相同 会新增80条件数据 再根据ID修改20条数据
        long time1 = System.currentTimeMillis();
        list.stream().forEach(item -> {
            request.add(
                    new IndexRequest("bcadmin_exchange_electric1", "_doc")
                            .id(item.getId())
                            .source(GsonUtils.GsonString(item), XContentType.JSON)
            );
        });

        log.info("循环耗时 {}", System.currentTimeMillis() - time1);

        long time2 = System.currentTimeMillis();
        BulkResponse responses = ElasticSearchConfig.getRestHighLevelClient().bulk(request, RequestOptions.DEFAULT);
        //测试服务器 3w数据 40s左右 十万数据会打挂
        log.info("执行耗时 {}", System.currentTimeMillis() - time2);
        log.info("responses {}", responses);

        //批量操作是否失败
        log.info("hasFailures {}", responses.hasFailures());

    }

    @GetMapping("/search1")
    public Message<?> search1(
            @RequestParam(value = "operatorId", required = false) Long operatorId,
            @RequestParam(value = "cabinetName", required = false) String cabinetName,
            @RequestParam(value = "oldBat", required = false) String oldBat,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        SearchRequest searchRequest = new SearchRequest("bcadmin_exchange_electric");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        PageNumAndSize page = PageNumAndSize.checkPage0(pageNum, pageSize);
        searchSourceBuilder.from(page.getPageNum()).size(page.getPageSize());

        //条件查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .must(new TermQueryBuilder("operatorId", operatorId))
                .must(new MatchQueryBuilder("cabinetName", cabinetName).operator(Operator.AND).analyzer("ik_max_word"))
                //.must(new MatchQueryBuilder("oldBat", oldBat).operator(Operator.AND))
                //Wildcard相当于MySQL中的模糊查询
                .must(new WildcardQueryBuilder("oldBat", String.format("*%s*", oldBat)));

        searchSourceBuilder.query(boolQueryBuilder)
                .sort("id", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);

        log.info("searchSourceBuilder: {}", searchSourceBuilder);

        try {
            //获取查询条件的返回值
            SearchResponse searchResponse = ElasticSearchConfig.getRestHighLevelClient().search(searchRequest, RequestOptions.DEFAULT);

            //获取结果集
            SearchHits responseHits = searchResponse.getHits();
            SearchHit[] hitsHits = responseHits.getHits();

            //创建List对象 供分页使用
            List<ExchangeElectricVO> list = new ArrayList<>(hitsHits.length);

            for (SearchHit hit : hitsHits) {
                list.add(GsonUtils.GsonToBean(hit.getSourceAsString(), ExchangeElectricVO.class));
            }
            PageUtil pageUtil = new PageUtil();
            pageUtil.createPage(list, pageNum, page.getPageSize(), new BigDecimal(responseHits.getTotalHits()).intValue());

            return ResultUtils.object(pageUtil);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.ok();
    }

    /**
     * 模糊查询
     GET /bcadmin_exchange_electric/_search
     {
     "query": {
     "bool": {
     "must": [
     {
     "term": {
     "operatorId": 10
     }
     },
     {
     "wildcard": {
     "oldBat": "*112*"
     }
     }
     ]
     }
     }
     }

     POST _analyze
     {
     "tokenizer": "ngram",
     "text": "BT1048016080XHS200715004"
     }

     结合ngram分词器做自动补全
     * */


}
