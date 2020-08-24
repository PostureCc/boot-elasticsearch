package com.chan.controller;

import com.chan.model.VO.UserVO;
import com.chan.repo.UserRepository;
import com.google.common.collect.Lists;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/addDoc")
    public void addDoc() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

//        List<UserVO> list = Lists.newArrayList(
//                UserVO.builder().id(1L).name("chan").age(18).sex("男").message("我是中国湖南人").createTime(simpleDateFormat.parse("2020-08-15 16:54:22")).build(),
//                UserVO.builder().id(2L).name("mack").age(19).sex("男").message("我是中国广东人").createTime(simpleDateFormat.parse("2020-08-15 16:56:22")).build(),
//                UserVO.builder().id(3L).name("jan").age(20).sex("女").message("我是美国阿拉巴马人").createTime(simpleDateFormat.parse("2020-08-16 36:54:22")).build(),
//                UserVO.builder().id(4L).name("job").age(22).sex("女").message("我是美国阿拉斯加人").createTime(simpleDateFormat.parse("2020-08-17 16:54:22")).build()
//        );

        List<UserVO> list = Lists.newArrayList(
                UserVO.builder().id(1L).name("chan").age(18).sex("男").message("我是中国湖南人").createTime("2020-08-15 16:54:22").build(),
                UserVO.builder().id(2L).name("mack").age(19).sex("男").message("我是中国广东人").createTime("2020-08-15 16:56:22").build(),
                UserVO.builder().id(3L).name("jan").age(20).sex("女").message("我是美国阿拉巴马人").createTime("2020-08-16 16:54:22").build(),
                UserVO.builder().id(4L).name("job").age(22).sex("女").message("我是美国阿拉斯加人").createTime("2020-08-17 16:54:22").build()
        );

        userRepository.saveAll(list);
    }

    @GetMapping("/searchByMessage")
    public void searchByMessage(String message) {
//        List<UserVO> list = userRepository.searchByMessage(message);
//        System.out.println(list);

        MatchQueryBuilder builder = QueryBuilders.matchQuery("message", message);
        System.out.println(builder);
        Iterable<UserVO> iterable = userRepository.search(builder);
        iterable.forEach(item -> System.out.println(item));

    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.parse("2020-08-16 36:54:22"));
    }

}
