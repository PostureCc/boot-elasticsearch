package com.chan.controller;

import com.chan.mapper.DeviceChangeMapper;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.repo.ExchangeElectricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SyncDataSource {

    @Autowired
    private DeviceChangeMapper deviceChangeMapper;

    @Autowired
    private ExchangeElectricRepository exchangeElectricRepository;

    @GetMapping("/sync")
    public void sync(){
        List<ExchangeElectricVO> list = deviceChangeMapper.list();
        exchangeElectricRepository.saveAll(list);
    }

}
