package com.chan.config;

import com.chan.model.VO.RestEntity;
import com.chan.model.VO.RestHits;
import com.chan.utils.GsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.util.ArrayList;
import java.util.List;

public class RestClientConfig {

    public static final RestClient restClient = RestClient.builder(
            new HttpHost("jd", 9201, "http")
//            ,new HttpHost("localhost", 9201, "http")
    ).build();

    public static RestEntity parseHits(String entity) {
        JsonObject gson = GsonUtils.GsonToBean(entity, JsonObject.class);
        JsonObject hitsObj = gson.getAsJsonObject("hits");
        int total = hitsObj.get("total").getAsInt();

        if (0 > total) {
            return null;
        } else {
            List<RestHits> resHits = new ArrayList<>(total);
            JsonArray hits = hitsObj.getAsJsonArray("hits");
            hits.forEach(item -> {
                JsonObject data = item.getAsJsonObject();

                JsonObject source = data.getAsJsonObject("_source");

                resHits.add(RestHits.builder()
                        .id(data.get("_id").getAsString())
                        .score(data.get("_score").getAsLong())
                        .source(GsonUtils.GsonString(source))
                        .build());

            });

            return RestEntity.builder()
                    .total(total)
                    .hits(resHits)
                    .build();
        }

    }

}
