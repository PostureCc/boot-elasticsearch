package com.chan.model.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeElectricVO implements Serializable {

    /**
     * 索引名称只能小写!
     */
    public static final String EXCHANGE_ELECTRIC = "bcadmin_exchange_electric";

    private String id;

    private String uid;

    private String clientId;

    private String mobile;

    private String realName;

    private String oldBat;

    private String newBat;

    private Long operatorId;

    private String operatorName;

    private String cabinetId;

    private String cabinetName;

    private String createTime;

}
