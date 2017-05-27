package pers.yuiz.common.costant;

import pers.yuiz.common.vo.Result;

public class ResultCostant {
    public final static Result SUCCESS = new Result(200, "success");
    public final static Result SERVER_ERROR = new Result(500, "服务器异常");
    public final static Result ARGS_WARN = new Result(4000, "输入参数异常警告");

    /**
     * 登录注册相关的warnException返回结果
     */
    public final static Result REGISTER_ARGS_IS_WRONG_WARN = new Result(4100, "注册信息不完整");
    public final static Result REGISTER_USERNAME_IS_WRONG_WARN = new Result(4101, "请输入4至20长度的用户名");
    public final static Result REGISTER_PASSWORD_IS_WRONG_WARN = new Result(4102, "请输入4至20长度的密码");
    public final static Result REGISTER_USERNAME_IS_REPEATED_WARN = new Result(4103, "该用户名已被使用,请重新输入");
    public final static Result NOT_LOGIN_WARN = new Result(4001, "请登录后进行操作");
    public final static Result LOGIN_ARGS_IS_WRONG_WARN = new Result(4999, "用户名或密码错误");
}
