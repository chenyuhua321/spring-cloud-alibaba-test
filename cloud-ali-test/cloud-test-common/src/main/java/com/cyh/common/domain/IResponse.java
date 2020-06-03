package com.cyh.common.domain;

/**
 * @author Chenyuhua
 * @date 2020/5/24 15:01
 */
public class IResponse {
    private Object code;
    private String msg;
    private String data;

    public IResponse() {
    }

    public IResponse(Object code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public IResponse(Object code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
