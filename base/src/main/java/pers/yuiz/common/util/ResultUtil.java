package pers.yuiz.common.util;

import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.vo.Result;

public class ResultUtil {
    private final static Result SUCCESS = new Result(200, "success");
    private final static Result SERVER_ERROR = new Result(500, "服务器异常");

    public final static Result ARGS_WARN = new Result(4000, "输入参数异常警告");

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

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    public static Result warnException(WarnException e) {
        return new Result(e);
    }
}
