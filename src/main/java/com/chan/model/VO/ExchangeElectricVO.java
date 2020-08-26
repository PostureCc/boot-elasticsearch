package com.chan.model.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

//indexName:索引名称 type:规定只能使用_doc 防止后续升级出现不必要的问题
@Document(indexName = ExchangeElectricVO.EXCHANGE_ELECTRIC, type = "_doc", shards = 1, replicas = 0)
public class ExchangeElectricVO implements Serializable {

    /**
     * 索引名称只能小写!
     */
    public static final String EXCHANGE_ELECTRIC = "bcadmin_exchange_electric";

    @Id
    //@Id 文档主键 唯一标识
    //@Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    @Field(type = FieldType.Long)
    private String id;

    @Field(type = FieldType.Text)
    private String uid;

    @Field(type = FieldType.Text)
    private String clientId;

    @Field(type = FieldType.Text)
    private String mobile;

    @Field(type = FieldType.Text)
    private String realName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String oldBat;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String newBat;

    @Field(type = FieldType.Long)
    private Long operatorId;

    @Field(type = FieldType.Text)
    private String operatorName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String cabinetId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String cabinetName;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

}
