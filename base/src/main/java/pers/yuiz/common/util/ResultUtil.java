package pers.yuiz.common.util;

import pers.yuiz.common.vo.Result;

public class ResultUtil {
    private final static Result SUCCESS = new Result(200, "success");
    private final static Result SERVER_ERROR = new Result(500, "服务器异常");

    /**
     * 成功,无数据返回
     *
     * @return
     */
    public static Result success() {
        return new Result(SUCCESS);
    }

    /**
     * 成功,有数据返回
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result(SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 服务器异常
     *
     * @return
     */
    public static Result serverError() {
        return new Result(SERVER_ERROR);
    }
}
