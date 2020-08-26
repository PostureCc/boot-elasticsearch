package com.chan.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：消息对象
 */
@Data
public class Message<T> implements Serializable {

    private static final long serialVersionUID = 2953946557905707053L;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public Message() {
    }

    public Message(IRet result) {
        this.code = result.getCode();
        this.message = result.getMessage();
    }

    public Message(IRet result, String msg) {
        this(result);
        this.message = msg;
    }

    public Message(IRet result, T data) {
        this(result);
        this.data = data;
    }

    public Message(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Message(IRet result, String message, T data) {
        this(result);
        this.message = message;
        this.data = data;
    }

    public Message(Message<?> msg, T data) {
        this.code = msg.getCode();
        this.message = msg.message;
        this.data = data;
    }

    public Message setCode(int code) {
        this.code = code;
        return this;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public Message setData(T data) {
        this.data = data;
        return this;
    }
}