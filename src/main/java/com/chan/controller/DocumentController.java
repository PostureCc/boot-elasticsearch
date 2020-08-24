package com.chan.controller;

import com.chan.model.VO.ExchangeElectricVO;
import com.chan.repo.ExchangeElectricRepository;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RestController
public class DocumentController {

    @Autowired
    private ExchangeElectricRepository exchangeElectricRepository;

    @GetMapping("/createIndex")
    public void createIndex() {
        String settings = "{\"index\":{\"number_of_shards\":1,\"number_of_replicas\":0}}",
                mapping = "{\"properties\":{\"cabinetName\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"clientId\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"createTime\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"id\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"mobile\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"newBat\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"oldBat\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"operatorId\":{\"type\":\"long\"},\"operatorName\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"realName\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}},\"uid\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\",\"ignore_above\":256}}}}}";
        CreateIndexRequest request = new CreateIndexRequest(ExchangeElectricVO.EXCHANGE_ELECTRIC);
        request.settings(settings, XContentType.JSON);
        request.mapping(mapping, XContentType.JSON);


    }

    @GetMapping("/addDoc")
    public void addDoc() {
//        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()
//                .id("199324")
//                .uid("CH200730000483")
//                .clientId("V20200309121811")
//                .mobile("15599534932")
//                .realName("李健")
//                .oldBat("BT104802512SZHL191120123")
//                .newBat("BT104802512SZHL191120456")
//                .operatorId(23L)
//                .operatorName("四优换电")
//                .cabinetName("XM朴朴SM12店-01")
//                .createTime("2020-07-30 12:25:15")
////                .createTime(new Date())
//                .build();
        ExchangeElectricVO exchangeElectricVO = ExchangeElectricVO.builder()
                .id("218118")
                .uid("CH200730000483")
                .clientId("V20200309121811")
                .mobile("15599534932")
                .realName("李健")
                .oldBat("BT104802512SZHL191120274")
                .newBat("BT104802512SZHL200401653")
                .operatorId(23L)
                .operatorName("四优换电")
                .cabinetName("XM朴朴SM12店-02")
                .createTime("2020-08-06 16:29:40")
//                .createTime(new Date())
                .build();
        exchangeElectricRepository.save(exchangeElectricVO);
    }

    @GetMapping("/saveAll")
    public void saveAll() {
        List<ExchangeElectricVO> list = Arrays.asList(
                ExchangeElectricVO.builder()
                        .id("200135")
                        .uid("CH200730001295")
                        .clientId("V20200423031635")
                        .mobile("15080053301")
                        .realName("吴发发")
                        .oldBat("BT104802512SZHL200323003")
                        .newBat("BT104802512SZHL200323391")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("FZ朴朴华建41店-01")
                        .createTime("2020-07-30 16:15:08")
                        .build(),
                ExchangeElectricVO.builder()
                        .id("200134")
                        .uid("CH200730001294")
                        .clientId("V20200709083301")
                        .mobile("13101437586")
                        .realName("张辉")
                        .oldBat("BT104802512ST00191130345")
                        .newBat("BT104802512ST00191130382")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("XM朴朴西亭33店-01")
                        .createTime("2020-07-30 16:14:59")
                        .build(),
                ExchangeElectricVO.builder()
                        .id("200133")
                        .uid("CH200730001293")
                        .clientId("V20200603152601")
                        .mobile("13276962231")
                        .realName("谢燕飞")
                        .oldBat("BT104802512SZHL200323061")
                        .newBat("BT104802512SZHL200401899")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("XM饿了么江头站-01安宝大厦")
                        .createTime("2020-07-31 16:14:50")
                        .build(),
                ExchangeElectricVO.builder()
                        .id("200132")
                        .uid("CH200730001291")
                        .clientId("V20200701133901")
                        .mobile("15970932874")
                        .realName("成雄")
                        .oldBat("BT104802512SZHL200401887")
                        .newBat("BT104802512ST00191130862")
                        .operatorId(23L)
                        .operatorName("四优换电")
                        .cabinetName("XM饿了么人才站-01育秀里")
                        .createTime("2020-07-31 16:12:36")
                        .build()
        );

        exchangeElectricRepository.saveAll(list);

    }

    @GetMapping("/findAll")
    public void findAll() {
        Iterable<ExchangeElectricVO> iterable = exchangeElectricRepository.findAll();
        iterable.forEach(item -> System.out.println(item));
        System.out.println();
    }

    @GetMapping("/searchAll")
    public void searchAll() {
        //不支持全部查询
        List<ExchangeElectricVO> list = exchangeElectricRepository.searchAll();
        list.forEach(item -> System.out.println(item));
    }

    @GetMapping("/searchById")
    public void searchById(String id) {
        System.out.println(exchangeElectricRepository.searchById(id));
    }

    @GetMapping("/findById")
    public void findById(String id) {
        //条件查询
        System.out.println(exchangeElectricRepository.findById(id));
    }

    @GetMapping("/searchByClientId")
    public void searchByClientId(String clientId) {
        List<ExchangeElectricVO> list = exchangeElectricRepository.searchByClientId(clientId);
        list.forEach(item -> System.out.println(item));
    }

    @GetMapping("/querySearch")
    public void querySearch(String clientId, String oldBat) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("clientId", clientId))
                .must(QueryBuilders.matchQuery("oldBat", oldBat));
        Iterable<ExchangeElectricVO> iterable = exchangeElectricRepository.search(builder);
        System.out.println("builder " + builder);
        for (ExchangeElectricVO vo : iterable) {
            System.out.println(vo);
        }
    }

    @GetMapping("/count")
    public void count() {
        long count = exchangeElectricRepository.count();
        System.out.println(count);
    }

}
