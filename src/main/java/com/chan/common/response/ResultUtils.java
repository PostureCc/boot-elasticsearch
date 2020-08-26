package com.chan.common.response;


import java.io.Serializable;

/**
 * @Auther: Chan
 * @Date: 2019/6/17 14:54
 * @Description: 自定义异常处理
 */
public class ResultUtils<T> implements Serializable {


    /**
     * 成功
     */
    public static Message ok() {
        return new Message<>(BaseResult.SUCCESS);
    }


    /**
     * 失败
     */
    public static Message exception(Integer status, String errMsg) {
        return new Message<>(status, errMsg);
    }


    /**
     * 失败
     */
    public static Message exception(String errMsg) {
        return new Message<>(BaseResult.ERROR, errMsg);
    }


    /**
     * 成功
     */
    public static <T> Message<T> object(T content) {
        return new Message<T>(BaseResult.SUCCESS, content);
    }


}
