package pers.yuiz.common.vo;

import pers.yuiz.common.exception.WarnException;

import java.io.Serializable;

public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Result result) {
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    public Result(WarnException e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
