package com.chan.repo;

import com.chan.model.VO.ExchangeElectricVO;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface ExchangeElectricRepository extends ElasticsearchRepository<ExchangeElectricVO, String> {


    /**
     * 根据ID查询
     *
     * @return
     */
    Optional<ExchangeElectricVO> findById(String id);

    /**
     * 根据ID查询 在查询的时候 需要把最外面的query层去掉
     *
     * @return
     */
    @Query("{\"bool\":{\"must\":[{\"match\":{\"id\":\"?0\"}}]}}\n")
    List<ExchangeElectricVO> searchById(String id);

    @Query("{\"match\":{\"clientId\":\"?0\"}}")
    List<ExchangeElectricVO> searchByClientId(String clientId);

    /**
     * 不能这些查询所有!!
     */
    @Query("match_all:{}")
    List<ExchangeElectricVO> searchAll();

}
