package com.chan.controller.index;

import com.chan.config.ElasticSearchConfig;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
public class IndexController {


    @GetMapping("/createIndex")
    public void createIndex(String indexName) throws IOException {
        //String indexName = ExchangeElectricVO.EXCHANGE_ELECTRIC,
        //{"index":{"number_of_shards":3,"number_of_replicas":1}}
        String
                settings = "{\"index\":{\"number_of_shards\":1,\"number_of_replicas\":0}}",
                mapping = "{\"properties\":{\"cabinetId\":{\"type\":\"keyword\"},\"cabinetName\":{\"type\":\"keyword\"},\"clientId\":{\"type\":\"keyword\"},\"createTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"id\":{\"type\":\"keyword\"},\"mobile\":{\"type\":\"keyword\"},\"newBat\":{\"type\":\"keyword\"},\"oldBat\":{\"type\":\"keyword\"},\"operatorId\":{\"type\":\"long\"},\"operatorName\":{\"type\":\"keyword\"},\"realName\":{\"type\":\"keyword\"},\"uid\":{\"type\":\"keyword\"}}}";

        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(settings, XContentType.JSON);
        request.mapping(mapping, XContentType.JSON);

        CreateIndexResponse response = ElasticSearchConfig.getRestHighLevelClient().indices().create(request, RequestOptions.DEFAULT);

        log.info("response {}", response);
    }

    @GetMapping("/deleteIndex")
    public void deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {
            AcknowledgedResponse response = ElasticSearchConfig.getRestHighLevelClient().indices().delete(request, RequestOptions.DEFAULT);
            log.info("response {}", response.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
