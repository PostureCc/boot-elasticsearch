package com.chan.config;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

@Log4j2
public class RestHighLevelClientPoolFactory implements PooledObjectFactory<RestHighLevelClient> {
    @Override
    public void activateObject(PooledObject<RestHighLevelClient> arg0) {
        System.out.println("activateObject");

    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
        RestHighLevelClient highLevelClient = pooledObject.getObject();
        highLevelClient.close();
    }

    /**
     * 生产对象
     */
    @Override
    public PooledObject<RestHighLevelClient> makeObject() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("fnjkj001", "fnjkj001"));

        RestClientBuilder builder = RestClient.builder(new HttpHost("fnjkj8", 9201, "http"));

        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                //默认为1S 重置为3S
                builder.setConnectTimeout(3000);
                //默认30S 防止有耗时操作 重置为3M
                builder.setSocketTimeout(180000);
                return builder;
            }
        }).setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                log.error("Node Exception: {}", node);
            }
        }).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {

                return httpAsyncClientBuilder.disableAuthCaching().setDefaultCredentialsProvider(credentialsProvider);
            }
        });

        System.err.println("builder " + builder);


        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(builder);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DefaultPooledObject(client);
    }

    @Override
    public void passivateObject(PooledObject<RestHighLevelClient> arg0) {
        System.out.println("passivateObject");
    }

    @Override
    public boolean validateObject(PooledObject<RestHighLevelClient> arg0) {
        return true;
    }

}
