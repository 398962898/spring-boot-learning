package pers.yuiz.common.exception;

import pers.yuiz.common.vo.Result;

/**
 * 自定义异常
 */
public class WarnException extends RuntimeException {
    private Integer code;

    public WarnException() {
    }

    public WarnException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public WarnException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public WarnException(Result result) {
        super(result.getMsg());
        this.code = result.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
