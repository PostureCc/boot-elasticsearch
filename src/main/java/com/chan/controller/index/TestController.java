package com.chan.controller.index;

import com.chan.config.ElasticSearchUtils;
import com.chan.model.VO.ExchangeElectricVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Chan
 * @Date: 2020/6/10 16:31
 * @Description:
 */
@Log4j2
@RestController
public class TestController {

    @Autowired
    private ElasticSearchUtils elasticSearchUtils;

    @GetMapping("/delIndex")
    public void delIndex() {
        elasticSearchUtils.deleteIndex(ExchangeElectricVO.EXCHANGE_ELECTRIC);
    }

}
