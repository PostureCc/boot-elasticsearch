package com.chan.controller;

import com.chan.config.RestClientConfig;
import com.chan.model.VO.ExchangeElectricVO;
import com.chan.model.VO.RestEntity;
import com.chan.repo.ExchangeElectricRepository;
import com.chan.utils.GsonUtils;
import com.chan.utils.PageUtil;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Log4j2
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

    @GetMapping("/search2")
    public void search2() {
        List<ExchangeElectricVO> list = exchangeElectricRepository.search2("朴朴 华建", "华建", "朴朴");
        list.stream().forEach(item -> System.out.println(item));
    }

    @GetMapping("/findList")
    public void findList(/*String mobile, String oldBat, String newBat, String cabinetName, Long operatorId, String startTime, String endTime*/
            @RequestParam(value = "operatorId", required = false) Long operatorId,
            @RequestParam(value = "operatorName", required = false) String operatorName,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) throws IOException {

        Request request = new Request("POST", "/bcadmin_exchange_electric/_search");

        StringBuilder query = new StringBuilder()
                .append(String.format("{\n" +
                        "  \"from\": %s,\"size\": %s,\n" +
                        "  \"query\": {\n" +
                        "    \"bool\": {\n" +
                        "      \"must\": [", pageNum, pageSize));
        if (operatorId != null && operatorId != 0) {
            query.append(String.format("{\n" +
                    "          \"term\": {\n" +
                    "             \"operatorId\": %s\n" +
                    "          }\n" +
                    "        }", operatorId));
        }

        if (operatorName != null && operatorName != "") {
            query.append(String.format(",{\n" +
                    "          \"match\": {\n" +
                    "            \"cabinetName\": {\n" +
                    "              \"query\": \"%s\",\n" +
                    "              \"operator\": \"and\"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }", operatorName));
        }

        query.append("]\n" +
                "    }\n" +
                "  }\n" +
                "}");

        log.info("query: \n{}", query);
        request.setJsonEntity(query.toString());

        Response response = RestClientConfig.restClient.performRequest(request);

        String entity = EntityUtils.toString(response.getEntity());


        RestEntity restEntity = RestClientConfig.parseHits(entity);
        System.out.println(restEntity);

        PageUtil<RestEntity> pageUtil = new PageUtil<>();
        pageUtil.createPage(restEntity.getHits(), pageNum, pageSize, restEntity.getTotal());
        System.out.println(pageUtil);
    }

    public static void main(String[] args) {
//        Integer page = 3;
//        Integer size = 20;
//
//        Integer from = page == null || page == 1 ? 0 : (page - 1) * size;
//        size = size == null ? 20 : size;
//
//        System.out.println(from);
//        System.out.println(size);


        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);


        PageUtil<Integer> pager = new PageUtil<>();
//        pager.setCurentPageIndex(2);
//        pager.setPageSize(10);
        pager.createPage(list, 0, 20, 100);
        pager.total();//得到总条数
        pager.smallList();//得到小的集合(分页的当前页的记录)

        System.out.println(pager.smallList());


    }

    /**
     * 有意向迁移到ElasticSearch中的数据
     * 1.换电记录 可优化范围:换电记录列表/运营大屏/支付大屏/运营报表 等数据的统计
     * 2.异常告警(1.0和2.0) 可优化范围:异常告警报警的列表查询/监控报表的数据的统计
     * 3.租赁订单及换电订单
     * 4.资产地图的数据统计 迁移前需考虑好数据的更新
     *
     *
     CREATE TABLE `t_exchange_electric` (
     `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
     `uid` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `mobile` VARCHAR(30) NOT NULL COLLATE 'utf8mb4_bin',
     `clienId` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `realName` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `oldBat` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `newBat` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `operatorId` BIGINT(20) NULL DEFAULT NULL,
     `operatorName` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `cabinetId` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `cabinetName` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_bin',
     `createTime` DATETIME NULL DEFAULT NULL,
     PRIMARY KEY (`id`)
     )
     COMMENT='测试数据表'
     COLLATE='utf8mb4_bin'
     ENGINE=InnoDB
     ;

     INSERT INTO t_exchange_electric
     (id,uid,clienId,realName,mobile,oldBat,newBat,operatorId,operatorName,cabinetId,cabinetName,createTime)

     SELECT t1.`id`, t1.`uid`, t2.`identity_id` AS clientId, IFNULL(t2.real_name,t2.`mobile`)realName, t2.`mobile` mobile, t1.`old_battery` AS oldBat, t1.`new_battery` AS newBat, t4.`id` operatorId,t4.`operatorname` AS operatorName,t3.`number` cabinetId, t3.`name` AS cabinetName, t1.`create_time` createTime
     FROM t_device_change t1
     LEFT JOIN `t_wx_user` t2 ON (t2.id = t1.user_id)
     LEFT JOIN t_cabinets t3 ON (t3.number = t1.cabinet_id)
     LEFT JOIN t_operator t4 ON (t4.id = t3.id_operator)
     LEFT JOIN system_user_operator t5 ON t5.operatorid=t4.id
     WHERE t1.change_type = 1 AND t5.userid = 1
     ORDER BY t1.id DESC
     *
     * */

}
