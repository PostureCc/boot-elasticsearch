package com.chan.controller.index;

import com.chan.config.ElasticSearchConfig;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
public class IndexController {

    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @GetMapping("/createIndex")
    public void createIndex(String indexName) throws IOException {
        //String indexName = ExchangeElectricVO.EXCHANGE_ELECTRIC,
        //{"index":{"number_of_shards":3,"number_of_replicas":1}}
        String
                settings = "{\"index\":{\"number_of_shards\":1,\"number_of_replicas\":0,\"max_result_window\":500000}}",
                //mapping = "{\"properties\":{\"id\":{\"type\":\"long\"},\"uid\":{\"type\":\"keyword\"},\"userId\":{\"type\":\"long\"},\"orderNo\":{\"type\":\"keyword\"},\"mobile\":{\"type\":\"keyword\"},\"realName\":{\"type\":\"keyword\"},\"oldBat\":{\"type\":\"keyword\"},\"newBat\":{\"type\":\"keyword\"},\"siteId\":{\"type\":\"long\"},\"siteName\":{\"type\":\"keyword\"},\"operatorId\":{\"type\":\"long\"},\"operatorName\":{\"type\":\"keyword\"},\"cabinetNo\":{\"type\":\"keyword\"},\"cabinetName\":{\"type\":\"keyword\"},\"createTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"}}}";
                mapping = "{\"properties\":{\"id\":{\"type\":\"long\"},\"uid\":{\"type\":\"keyword\"},\"userId\":{\"type\":\"long\"},\"orderNo\":{\"type\":\"keyword\"},\"mobile\":{\"type\":\"keyword\"},\"realName\":{\"type\":\"keyword\"},\"oldBat\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"newBat\":{\"type\":\"keyword\"},\"siteId\":{\"type\":\"long\"},\"siteName\":{\"type\":\"keyword\"},\"operatorId\":{\"type\":\"long\"},\"operatorName\":{\"type\":\"keyword\"},\"cabinetNo\":{\"type\":\"keyword\"},\"cabinetName\":{\"type\":\"keyword\"},\"createTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"}}}";

        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(settings, XContentType.JSON);
        request.mapping(mapping, XContentType.JSON);

        CreateIndexResponse response = elasticSearchConfig.getRestHighLevelClient().indices().create(request, RequestOptions.DEFAULT);

        log.info("response {}", response);
    }

    @GetMapping("/deleteIndex")
    public void deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {
            AcknowledgedResponse response = elasticSearchConfig.getRestHighLevelClient().indices().delete(request, RequestOptions.DEFAULT);
            log.info("response {}", response.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
