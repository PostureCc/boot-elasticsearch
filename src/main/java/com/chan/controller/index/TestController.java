package com.chan.controller.index;

import com.chan.config.ElasticSearchConfig;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Auther: Chan
 * @Date: 2020/6/10 16:31
 * @Description:
 */
@Log4j2
@RestController
@RequestMapping("/index")
public class TestController {

    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @RequestMapping("/create")
    public void createIndex() {
        //创建请求
        CreateIndexRequest request = new CreateIndexRequest("test-index");

        try {
            //执行请求
            CreateIndexResponse response = elasticSearchConfig.restHighLevelClient()
                    .indices()
                    .create(request, RequestOptions.DEFAULT);

            log.info("response {}", response);
        } catch (IOException e) {
            log.error(e);
        }

    }

    @RequestMapping("/get")
    public void getIndex() {
        GetIndexRequest request = new GetIndexRequest("test-index");
        try {
            //在ElasticSearch中Index=MySQL的DataBase 所以只能判断是否存在
            boolean exists = elasticSearchConfig.restHighLevelClient()
                    .indices()
                    .exists(request, RequestOptions.DEFAULT);

            log.info("exists {}", exists);
        } catch (IOException e) {
            log.error(e);
        }

    }

    @RequestMapping("/delete")
    public void deleteIndex() {
        DeleteIndexRequest request = new DeleteIndexRequest("test-index");
        try {
            AcknowledgedResponse response = elasticSearchConfig.restHighLevelClient().indices().delete(request, RequestOptions.DEFAULT);
            log.info("response {}", response.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
