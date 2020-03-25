package com.huiyou.liuzhi.model;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author Joe
 * @version 1.0
 * 回调信息
 * @date 2019/12/16 12:58
 */
public class ResponseBean {


    private Integer code;

    private List<T> data;

    private String msg;

    private Integer count;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
