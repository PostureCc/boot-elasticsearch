package com.chan.common.response;

import lombok.Getter;


/**
 * @Auther: Chan
 * @Date: 2019/6/17 14:54
 * @Description: 自定义异常处理
 */
@Getter
public enum BaseResult implements IRet {

    LOGOUT_ERROR(-1, "登录已经失效 请重新登录"),
    SUCCESS(0, "operation.succeeded"),
    ERROR(1, "操作失败"),
    FREQUENTLY(2, "操作过于频繁"),
    CAPTCHA_FAILED(3, "verification.code.error"),

    USER_NOT_EXIST(700,"用户不存在"),
    FREEZE_USER(702,"用户被冻结，请联系管理员"),
    JURISDICTION_USER(703,"用户没有权限，请联系管理员"),
    ROLE_ALREADY_USED(730,"角色已经被使用，不能删除"),
    DEVICE_ERROR(853,"电池编号不存在"),
    ALREADY_USED(970,"告警提醒已存在"),
    CODE971(971,"型号已经存在"),
    ALREADY_NUMBER(972,"充电柜仓位数超出长度"),
    NO_CONTENT_EXIST_MSG(960,"协议类型 或者 告警名称 或者 运营商为空"),
    INTERFACE_CALL_ERROR(850,"接口调用失败"),
    DEVICE_OPERATE_FAIL(851,"设备操作失败,返回码：%s"),
    NO_CONTENT_EXIST(852,"请输入正确的设备号"),



    CODE602(602,"此菜单下存在子菜单，不能进行删除操作"),
    CODE604(604,"[%s]已经被使用"),
    CODE605(605,"未找到对应用户信息"),
    CODE606(606,"原密码不匹配"),
    CODE607(607,"品牌或型号为空"),
    CODE1110(1110,"文件导出失败"),


    /**excel导入*/
    EXCEL_ERROR(8,"文件表头字段与模板不一致"),

    /**租凭 BEGIN*/
    SITE_NOT_MSG(4, "没有找到相关租凭信息 请检查后重试"),
    CODE_10000(10000, "核算费用不能大于押金"),
    CODE_10001(10001, "微信退款失败"),

    /**租凭 END*/

    PARAMS_EXCEPTION(5, "参数异常 请检查后重试"),
    NO_DEVICE_INFORMATION(6, "没有设备信息"),
    IMAGES_UPLOAD_EXCEPTION(7, "图片上传失败 请稍后重试"),
    FILE_EXCEPTION(9,"文件错误或者为空"),
    MESSAGE_EXCEPTION(10, "短信发送失败 请检查后重试"),
    MESSAGE_CODE_TIME_OUT(11, "验证码已超时"),
    PHONE_NOT_FIND(12,"未找到电话"),
    NOT_BOUND_PHONE(13,"微信号未绑定电话"),
    SESSION_EXCEPTION_NULL(14,"session为空或者解密异常"),

    /**支付密码*/
    PASSWORD_NOT_FOUND(15,"密码未找到"),
    PASSWORD_ERROR(16,"密码错误"),
    SITE_NOT_FOUND(17,"站点未找到，检查后重试"),
    MESSAGE_ERROR_SMS_CODE(18,"短信验证错误"),
    MOBILE_HAVE_IN_DATA(19,"电话已存在"),
    ID_CARD_HAVE_IN_DATA(20,"身份证号已存在"),

    BATTERY_NOT_OPERATOR(21,"有电池不归属于一个运营商，请检查"),
    PROPERTY_EXIST(22,"该手机号已经被使用"),

    NUMBER_IS_NULL(23,"序号为空数据 请检查后重试！"),
    BATTERY_ERROR_SITE(24,"电池不属于站点,已参与过调拨,请检查后重试!"),
    BATTERY_ERROR_STATE(25,"电池不属于库存状态,请检查后重试!"),
    PROPERTY_LOGIN(26,"该登录名已经被使用"),
    CHARGE_HAVING_IN_SITE(27,"有站点已存在使用的计费"),


    EXCEL_IN_ERROR(28,"入库失败，共有%s行excel重复数据,序号为：%s重复 请检查后重试！"),
    EXCEL_ID_NOT_FOUND(29,"Excel电池序号存在空值为空"),
    TEXT_IS_NULL(30,"Excel文件内容为空"),
    ERROR_PLUS_ROW(31,"Excel不得大于1000"),
    ERROR_BRAND_MODLE(32,"请选择正确的厂家型号"),
    CABINET_ERROR(33,"设备已存在"),
    BATTERY_HAVING_ERROR(34,"有电池已经在进行调拨操作操作，请检查后重试!"),
    OPERATOR_ERROR(35,"调拨的电池换运营商，请检查操作是否正确!"),
    CODE_NUMBER_FOUND(36,"电池柜的编号已存在，检查后重试"),
    OPERATE_FAIL(37,"操作失败"),
    ORDER_ERROR(38,"当前操作用户过多，努力加载中，请稍后再试"),
    NOT_FOUND_PHONE(39,"已注册,请检查手机号后重试"),
    ERROR_EXCEL_MODEL(40,"错误的excel模板"),
    NOT_FOUND_BRAND_MODEL(41,"请填写电池编号和品牌"),
    FOUND_ERROR_ENTERPRISE_NAME(42,"企业名称存在"),
    /**
     * 电池入库
     */
    CODE_43_EXCEPTION(43, "excel %s行 电池序号：%s 已存在已存在！"),
    CODE_44_EXCEPTION(44, "电池：%s已经分配运营商出库，请检查后，重试"),
    CODE_45_EXCEPTION(45, "excel %s行 电池序号：%s未找到无法出库，检查电池编号是否正确"),
    CODE_40_EXCEPTION(46, "操作失败，共有%s行excel重复数据,序号为：%s重复 请检查后重试！"),
    CODE_41_EXCEPTION(47, "入库失败,序号为：%s重复 请检查后重试！"),
    CODE_42_EXCEPTION(48, "操作，%s序号为空数据 请检查后重试！"),
    PROTOCOL_ERROR(49, "电池格式异常，检查到与协议不匹配的设备号，请检查"),
    CODE_302_EXCEPTION(302, "入库失败,excel %s行,电池序号为：%s录入设备的格式异常！"),
    BATTERY_HAVING_IN_STORE(301, "入库失败!电池已入库,切勿重复新增"),
    BATTERY_HAVING_NOT_IN_STORE(303, "出库失败,未发现设备存在入库操作,请检查!"),
    BATTERY_IN_STORE_STATE_EXPORT(304, "出库失败,excel %s行 序号为：%s设备已完成出库操作,请勿重复操作!"),
    BATTERY_IN_STORE_STATE_IS_USE(304, "出库失败,设备已完成出库操作并在使用中,请勿重复操作!"),





    DOOR_ERROR(50,"您所需的电池可能在赶来的路上，请耐心等候"),
    FOUND_NOT_MEMBER(51,"未登记客户无法登陆"),
    /**
     * 运营商
     */
    OPERATOR_LOGO(52, "电池格式异常，检查到与协议不匹配的设备号，请检查"),
    OPERATOR_NAME_ERROR(53, "运营商名称或者联系人未填写"),
    WORK_ORDER_HAVING(54, "设备已存在与订单中，或者待处理，请检查后重试"),
    DEVICE_STATE_ERROR(55, "电池在租赁中，或者其他状态中"),
    DEVICE_SITE_OR_NULL(56,"电池设备站点为空,本身站点过多，无法识别"),
    FAULT_TYPE_HAVING(57,"故障类型已存在，检查后重试"),
    SITE_GROUP_HAVING(58,"站点组已重复，请检查后重试"),
    DEVICE_NOT_CONTROLLER(59,"设备不在您的负责范围之内"),

    HAVE_ORDER_SAVE(60,"有%s订单存在电池未结束，检查后重试"),
    FOUND_HAVING_MEMBER(61,"当前用户已在审核中，切勿重复提交"),
    CHECK_AMOUNT(62,"核算金额为空，请填写核算金额，无核算可填写0"),
    STORAGE_HAVING_IN(63,"创建的仓库已存在"),
    DEVICE_SITE_NOT_IN(64,"设备未在此站点，检查后重试"),
    DEVICE_HAVE_NOT_SITE(65,"设备未分配站点"),
    FRANCHISEE_HAVING_IN(66,"已存在品牌运营商"),

    ORDER_NOT_DEVICE_TYPE_EXCEPTION(67,"该设备与计费模板设备类型不匹配 请核对后重试"),
    ORDER_CODE_STATE_EXCEPTION(68,"当前订单状态已改变,不可更换电池编号"),
    CODE_NOT_HAVE_IN_SITE(69,"设备正在维修中,未在站点库存"),
    IS_ABLE_ORDER_HAVING_CHARGE(70,"计费模板无法操作，正有订单正在使用："),
    ORDER_HAVING_HIDE(71,"订单已操作删除，且勿重复操作!"),
    ERROR_ORDER_HIDE(72,"订单已完结，删除会影响用户显示，切勿操作！"),
    CHARGE_IS_NOT_HAVING(73,"切勿操作，当前计费模板生效中"),
    END_TIME_ERROR(74,"订单无操作可操作的订单结束日期，前查看是否是租赁中的订单!"),
    SITE_PROFESSION_TYPE_ERROR(75,"非朴朴相关的站点用户，不可选择兼职"),
    VIN_NOT_HAVING_IN_LIST(76,"不存在该车架号 请检查后重试"),
    VIN_NOT_HAVING_IN_LIST_EXCEL(77,"第%s行，不存在该车架号 请检查后重试"),
    VIN_NOT_UPDATE_IN_ORDER_USING(78,"用户: %s ,车架号已领取电池 请检查后重试"),
    VIN_IN_FORM_ERROR(79,"车架号已存在与列表 请检查后重试"),
    EXCEL_VIN_IN_FORM_ERROR(80,"excel %s行，车架号已存在与列表 请检查后重试"),
    OPERATOR_IS_NULL(81,"请选择一个运营商！"),
    VIN_HAVING_IN_USER(81,"车架号已绑定用户无法删除 请检查后重试"),


    /**支付*/
    PAY_EXCEPTION(100, "支付失败"),

    /**订单*/
    ORDER_STATE_EXCEPTION(110,"订单正在%s 请核对后重试"),
    DEVICE_NOT_EXCEPTION(111,"未找到相关设备信息 请检查后重试"),
    ORDER_NOT_EXCEPTION(112,"未找到相关订单信息 请检查后重试"),
    AMOUNT_NOT_EXCEPTION(113,"您的余额已不足"),
    ORDER_EMPLOY_EXCEPTION(114,"正在使用中的订单 不能进行此操作"),
    SITE_NOT_DEVICE_EXCEPTION(115,"该设备不属于此租赁点 请核对后重试"),
    DEVICE_SITE_EXCEPTION(116,"该设备已在租赁中"),
    DEVICE_EDIT_EXCEPTION(117,"这台设备当前未放在站点库存,请检查"),
    BRAND_OR_MODEL_EXCEPTION(118,"该电池品牌或型号与计费模板品牌不一致 请检查后重试"),
    WITHDRAW_EXCEPTION(119,"提现失败 请稍后重试"),
    WITHDRAW_NUMBER_EXCEPTION(120,"当天申请提现次数过多"),
    WITHDRAW_AMOUNT_EXCEPTION(121,"提现在金额不能大于原充值金额"),
    ORDER_SERVICE_122(122,"订单已完结或已取消"),
    ORDER_SERVICE_123(123,"订单已续租 不可退款 请核算"),

    /**支付*/
    MEMBER_NOT_EXIST_EXCEPTION(130, "暂未找到相关会员信息 请检查后重试"),
    CODE131(131, "操作频繁，如有加急，请致电0755-84655983"),
    ;

    private final int code;
    private final String message;

    BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
