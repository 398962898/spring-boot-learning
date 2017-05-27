package pers.yuiz.common.util;

import pers.yuiz.common.costant.ResultCostant;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.vo.Result;

public class ResultUtil {
    /**
     * 成功,无数据返回
     *
     * @return
     */
    public static Result success() {
        return new Result(ResultCostant.SUCCESS);
    }

    /**
     * 成功,有数据返回
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result(ResultCostant.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 服务器异常
     *
     * @return
     */
    public static Result serverError() {
        return new Result(ResultCostant.SERVER_ERROR);
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
