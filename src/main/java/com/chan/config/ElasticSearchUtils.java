package com.chan.config;

import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Log4j2
@Component
public class ElasticSearchUtils {


    @Autowired
    @Qualifier(value = "restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 创建索引
     *
     * @param indexName
     * @param settings
     * @param mapping
     * @return
     * @
     */
    public CreateIndexResponse createIndex(String indexName, String settings, String mapping) {
        CreateIndexResponse response = null;
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            if (null != settings && !"".equals(settings)) {
                request.settings(settings, XContentType.JSON);
            }
            if (null != mapping && !"".equals(mapping)) {
                request.mapping(mapping, XContentType.JSON);
            }
            log.info("request--> " + request);
            response = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * 删除索引
     *
     * @param indexNames
     * @return
     * @
     */
    public AcknowledgedResponse deleteIndex(String... indexNames) {
        AcknowledgedResponse response = null;
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexNames);
            log.info("request--> " + request);
            response = client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }


    /**
     * 判断 index 是否存在
     *
     * @param indexName
     * @return
     * @
     */
    public boolean indexExists(String indexName) {
        boolean response = false;
        try {
            GetIndexRequest request = new GetIndexRequest(indexName);
            log.info("request--> " + request);
            response = client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * 根据 id 删除指定索引中的文档
     *
     * @param indexName
     * @param id
     * @return
     * @
     */
    public DeleteResponse deleteDoc(String indexName, String id) {
        DeleteResponse response = null;
        try {
            DeleteRequest request = new DeleteRequest(indexName, id);
            log.info("request--> " + request);
            return client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * 根据 id 更新指定索引中的文档
     * ElasticSearch版本升级后 参数不能用JSON了 但是可以用下面的Map方法
     *
     * @param indexName
     * @param id
     * @return
     * @
     */
    @Deprecated
    public UpdateResponse updateDoc(String indexName, String id, String updateJson) {
        UpdateResponse response = null;
        try {
            UpdateRequest request = new UpdateRequest(indexName, id);
            request.doc(XContentType.JSON, updateJson);
            log.info("request--> " + request);
            response = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * 根据 id 更新指定索引中的文档
     *
     * @param indexName
     * @param id
     * @return
     * @
     */
    public UpdateResponse updateDoc(String indexName, String id, Map<String, Object> updateMap) {
        UpdateResponse response = null;
        try {
            UpdateRequest request = new UpdateRequest(indexName, id);
            request.doc(updateMap);
            log.info("request--> " + request);
            response = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * 根据某字段的 k-v 更新索引中的文档
     *
     * @param fieldName
     * @param value
     * @param indexName
     * @
     */
    public BulkByScrollResponse updateByQuery(String fieldName, String value, String... indexName) {
        BulkByScrollResponse response = null;
        try {
            UpdateByQueryRequest request = new UpdateByQueryRequest(indexName);
            //单次处理文档数量
            request
                    //                .setBatchSize(100)
                    .setQuery(new TermQueryBuilder(fieldName, value))
                    .setTimeout(TimeValue.timeValueMinutes(2));
            log.info("request--> " + request);
            response = client.updateByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * 添加文档 手动指定ID
     * 如果ID相同 会自动修改
     *
     * @param indexName
     * @param id
     * @param source
     * @return
     * @
     */
    public IndexResponse addDoc(String indexName, String id, String source) {
        IndexResponse response = null;
        try {
            IndexRequest request = new IndexRequest(indexName);
            if (null != id) {
                request.id(id);
            }
            request.source(source, XContentType.JSON);
            log.info("request--> " + request);
            return client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * 添加文档 使用自动id
     *
     * @param indexName
     * @param source
     * @return
     * @
     */
    public IndexResponse addDoc(String indexName, String source) {
        return addDoc(indexName, null, source);
    }

    /**
     * 简单模糊匹配 默认分页为 0,10
     *
     * @param field
     * @param key
     * @param page
     * @param size
     * @param indexNames
     * @return
     * @
     */
    public SearchResponse search(String field, String key, int page, int size, String... indexNames) {
        SearchResponse response = null;
        try {
            SearchRequest request = new SearchRequest(indexNames);
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(new MatchQueryBuilder(field, key))
                    .from(page)
                    .size(size);
            request.source(builder);
            log.info("request--> " + request);
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }

    /**
     * term 查询 精准匹配
     *
     * @param field
     * @param key
     * @param page
     * @param size
     * @param indexNames
     * @return
     * @
     */
    public SearchResponse termSearch(String field, String key, int page, int size, String... indexNames) {
        SearchResponse response = null;
        try {
            SearchRequest request = new SearchRequest(indexNames);
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.termsQuery(field, key))
                    .from(page)
                    .size(size);
            request.source(builder);
            log.info("request--> " + request);
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
        return response;
    }


    /**
     * 批量导入
     *
     * @param indexName
     * @param isAutoId  使用自动id 还是使用传入对象的id
     * @param source
     * @return
     * @
     */
//    public BulkResponse importAll(String indexName, boolean isAutoId, String source)  {
//        if (0 == source.length()) {
//            //todo 抛出异常 导入数据为空
//        }
//        BulkRequest request = new BulkRequest();
//
//        JSONArray array = JSON.parseArray(source);
//
//        //todo 识别json数组
//        if (isAutoId) {
//            for (Object s : array) {
//                request.add(new IndexRequest(indexName).source(s, XContentType.JSON));
//            }
//        } else {
//            for (Object s : array) {
//                request.add(new IndexRequest(indexName).id(JSONObject.parseObject(s.toString()).getString("id")).source(s, XContentType.JSON));
//            }
//        }
//        return client.bulk(request, RequestOptions.DEFAULT);
//    }

}
