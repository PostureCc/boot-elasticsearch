package com.chan.mapper;

import com.chan.model.VO.ExchangeElectricVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceChangeMapper {

    List<ExchangeElectricVO> list(int page,int size);

}
