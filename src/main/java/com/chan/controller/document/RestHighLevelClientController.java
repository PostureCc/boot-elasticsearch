package com.chan.controller.document;

import com.chan.common.response.Message;
import com.chan.common.response.ResultUtils;
import com.chan.config.ElasticSearchConfig;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.utils.GsonUtils;
import com.chan.utils.PageNumAndSize;
import com.chan.utils.PageUtil;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/high")
public class RestHighLevelClientController {

    @GetMapping("/create")
    public void create() {
        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()

                .build();
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
                .must(new MatchQueryBuilder("cabinetName", cabinetName).operator(Operator.AND))
                .must(new MatchQueryBuilder("oldBat", oldBat).analyzer("ik_max_word").operator(Operator.AND));

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


}
