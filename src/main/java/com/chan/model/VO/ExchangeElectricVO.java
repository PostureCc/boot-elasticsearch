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

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor

//@Document 文档对象 （索引信息、文档类型 ）
@Document(indexName = ExchangeElectricVO.BC_ADMIN, type = ExchangeElectricVO.EXCHANGE_ELECTRIC, shards = 1, replicas = 0)
public class ExchangeElectricVO implements Serializable {

    /**
     * 索引名称只能小写!
     */
    public static final String BC_ADMIN = "bcadmin";
    public static final String EXCHANGE_ELECTRIC = "exchange_electric_rest";

    @Id
    //@Id 文档主键 唯一标识
    //@Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    @Field(store = true, index = false, type = FieldType.Long)
    private String id;

    @Field(index = true, store = true, type = FieldType.Text)
    private String uid;

    @Field(index = true, store = true, type = FieldType.Text)
    private String clientId;

    @Field(index = true, store = true, type = FieldType.Text)
    private String mobile;

    @Field(index = true, store = true, type = FieldType.Text)
    private String realName;

    @Field(index = true, store = true, type = FieldType.Text)
    private String oldBat;

    @Field(index = true, store = true, type = FieldType.Text)
    private String newBat;

    @Field(index = true, store = true, type = FieldType.Long)
    private Long operatorId;

    @Field(index = true, store = true, type = FieldType.Text)
    private String operatorName;

    @Field(index = true, store = true, type = FieldType.Text)
    private String cabinetName;

    @Field(index = true, store = true, type = FieldType.Date)
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOldBat() {
        return oldBat;
    }

    public void setOldBat(String oldBat) {
        this.oldBat = oldBat;
    }

    public String getNewBat() {
        return newBat;
    }

    public void setNewBat(String newBat) {
        this.newBat = newBat;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
