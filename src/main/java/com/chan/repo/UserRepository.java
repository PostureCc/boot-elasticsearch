package com.chan.repo;

import com.chan.model.VO.UserVO;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<UserVO, Long> {

    @Query("{\"match\":{\"message\":\"?0\"}}")
    List<UserVO> searchByMessage(String message);

}
