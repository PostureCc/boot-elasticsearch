//package com.chan.controller.index;
//
//import com.chan.model.VO.ExchangeElectricVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class IndexController {
//
//    @Autowired
//    private ElasticSearchUtils elasticSearchUtils;
//
//    @GetMapping("/createIndex")
//    public void createIndex() {
//        String indexName = ExchangeElectricVO.EXCHANGE_ELECTRIC,
//                settings = "{\"index\":{\"number_of_shards\":1,\"number_of_replicas\":0}}",
//                mapping = "{\"properties\":{\"cabinetName\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"clientId\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"createTime\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"id\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"mobile\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"newBat\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"oldBat\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"operatorId\":{\"type\":\"long\"},\"operatorName\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"realName\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"uid\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}}}";
//
//        elasticSearchUtils.createIndex(indexName, settings, mapping);
//    }
//
//}
