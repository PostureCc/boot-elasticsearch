package com.chan.model.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

//@Document(indexName = ExchangeElectricVO.EXCHANGE_ELECTRIC, shards = 3, replicas = 1)
public class ExchangeElectricVO implements Serializable {

    /**
     * 索引名称只能小写!
     */
    public static final String EXCHANGE_ELECTRIC = "exchange_electric_rest";

    //@Id
    //@Id 文档主键 唯一标识
    //@Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    //@Field(store = true, index = false, type = FieldType.Long)
    private String id;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String uid;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String clientId;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String mobile;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String realName;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String oldBat;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String newBat;

    //@Field(index = true, store = true, type = FieldType.Long)
    private Long operatorId;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String operatorName;

    //@Field(index = true, store = true, type = FieldType.Text)
    private String cabinetName;

    //@Field(index = true, store = true, type = FieldType.Date)
    private String createTime;

}
