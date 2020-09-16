package com.chan.config;

import com.chan.model.VO.ExchangeElectricVO;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;


public class ElasticSearchPoolUtil {

    // 对象池配置类，不写也可以，采用默认配置
    private static GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

    // 采用默认配置maxTotal是8，池中有8个client
    {
        poolConfig.setMaxTotal(8);
    }

    // 要池化的对象的工厂类，这个是我们要实现的类
    private static RestHighLevelClientPoolFactory esClientPoolFactory = new RestHighLevelClientPoolFactory();
    // 利用对象工厂类和配置类生成对象池
    private static GenericObjectPool<RestHighLevelClient> clientPool = new GenericObjectPool<>(esClientPoolFactory,
            poolConfig);

    /**
     * 获得对象
     *
     * @return
     * @throws Exception
     */
    public static RestHighLevelClient getClient() throws Exception {
        // 从池中取一个对象
        RestHighLevelClient client = clientPool.borrowObject();
        return client;
    }

    /**
     * 归还对象
     *
     * @param client
     */
    public static void returnClient(RestHighLevelClient client) {
        // 使用完毕之后，归还对象
        clientPool.returnObject(client);
    }


}
