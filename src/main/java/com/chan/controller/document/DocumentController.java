package com.chan.controller.document;

import com.chan.config.ElasticSearchConfig;
import com.chan.model.DTO.UserDTO;
import com.chan.utils.GsonUtils;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Chan
 * @Date: 2020/6/10 17:21
 * @Description:
 */
@Log4j2
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @GetMapping("/create")
    public void create() {

        List<Long> roles = new ArrayList<>();
        roles.add(1L);
        roles.add(2L);
        UserDTO.UserDTOBuilder builder = UserDTO.builder().id(1L).name("test1").roles(roles);

        IndexRequest request = new IndexRequest("test-index");
        //指定数据的ID
        request.id("1");

        IndexRequest source = request.source(GsonUtils.GsonString(builder), XContentType.JSON);
        log.info("source {}", source);

        try {
            IndexResponse response = elasticSearchConfig.restHighLevelClient().index(request, RequestOptions.DEFAULT);
            log.info("response {}", response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/get")
    public void get(@RequestParam("id") String id) {
        GetRequest request = new GetRequest("test-index", id);

        try {
            boolean exists = elasticSearchConfig.restHighLevelClient().exists(request, RequestOptions.DEFAULT);
            log.info("exists {}", exists);

            GetResponse response = elasticSearchConfig.restHighLevelClient().get(request, RequestOptions.DEFAULT);
            log.info("response {}", response.getSourceAsString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/update")
    public void update(@RequestParam("id") String id) {
        UpdateRequest request = new UpdateRequest("test-index", id);

        UserDTO.UserDTOBuilder builder = UserDTO.builder().name("test2");
        UpdateRequest doc = request.doc(GsonUtils.GsonString(builder), XContentType.JSON);
        log.info("doc {}", doc);

        try {
            //当条件为id时 update不存在的数据会报错
            UpdateResponse response = elasticSearchConfig.restHighLevelClient().update(request, RequestOptions.DEFAULT);
            log.info("response {}", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/delete")
    public void delete(@RequestParam("id") String id) {
        DeleteRequest request = new DeleteRequest("test-index", id);

        try {
            //当条件为id时 delete不存在的数据不会报错 status=NOT_FOUND
            DeleteResponse response = elasticSearchConfig.restHighLevelClient().delete(request, RequestOptions.DEFAULT);
            log.info("response {}", response);
            log.info("status {}", response.status());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("bulkInsert")
    public void insert() {
        //创建bulk请求对象
        BulkRequest request = new BulkRequest();


        //模拟业务数据
        List<Long> roles = new ArrayList<>();
        roles.add(1L);
        roles.add(2L);

        List<UserDTO> dataList = new ArrayList<>();
        dataList.add(UserDTO.builder().id(10L).name("test10").roles(roles).build());
        dataList.add(UserDTO.builder().id(11L).name("test11").roles(roles).build());
        dataList.add(UserDTO.builder().id(12L).name("test12").roles(roles).build());

        for (UserDTO userDTO : dataList) {
            //将数据循环添加到bulk对象中
            request.add(
                    //此处添加bulk操作的方法 例如insert update delete等
                    new IndexRequest("test-index")
                            .id(userDTO.getId().toString())
                            .source(GsonUtils.GsonString(userDTO), XContentType.JSON)
            );
        }

        try {
            //执行批量新增操作
            BulkResponse response = elasticSearchConfig.restHighLevelClient().bulk(request, RequestOptions.DEFAULT);

            log.info("response {}", response);

            //批量操作是否失败
            log.info("hasFailures {}", response.hasFailures());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/bulkUpdate")
    public void bulkUpdate() {
        BulkRequest request = new BulkRequest();

        List<UserDTO> dataList = new ArrayList<>();
        dataList.add(UserDTO.builder().id(10L).name("test10-update").build());
        dataList.add(UserDTO.builder().id(11L).name("test11-update").build());

        for (UserDTO userDTO : dataList) {
            request.add(
                    new UpdateRequest("test-index", userDTO.getId().toString())
                            .doc(GsonUtils.GsonString(userDTO), XContentType.JSON)
            );
        }

        try {
            BulkResponse response = elasticSearchConfig.restHighLevelClient().bulk(request, RequestOptions.DEFAULT);
            log.info("response {}", response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("bulkDelete")
    public void bulkDelete() {
        BulkRequest request = new BulkRequest();

        List<String> list = new ArrayList<>();
        list.add("10");
        list.add("11");

        for (String s : list) {
            request.add(new DeleteRequest("test-index", s));
        }

        try {
            BulkResponse response = elasticSearchConfig.restHighLevelClient().bulk(request, RequestOptions.DEFAULT);
            log.info("response {}", response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/search")
    public void search(
            @RequestParam("name") String name,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {

        //创建请求对象
        SearchRequest request = new SearchRequest("test-index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from(page);
        searchSourceBuilder.size(size);

        //条件查询
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("name", name);
        SearchSourceBuilder query = searchSourceBuilder.query(termQueryBuilder);
        log.info("query {}", query);

        request.source(searchSourceBuilder);


        SearchResponse response = null;
        try {
            response = elasticSearchConfig.restHighLevelClient().search(request, RequestOptions.DEFAULT);
            log.info("response {}", response);

            List<UserDTO> resList = new ArrayList<>();
            for (SearchHit fields : response.getHits().getHits()) {
                String value = fields.getSourceAsString();
                log.info("fields {}", value);
                resList.add(GsonUtils.GsonToBean(value, UserDTO.class));
            }

            resList.stream().forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
