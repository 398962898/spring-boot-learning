package pers.yuiz.customer.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.common.costant.NumberCostant;
import pers.yuiz.common.costant.ResultCostant;
import pers.yuiz.common.costant.StringCostant;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.service.CustomerService;
import pers.yuiz.customer.vo.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public Result register(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            throw new WarnException(ResultCostant.REGISTER_ARGS_IS_WRONG_WARN);
        }
        if (user.getUsername().trim().length() < 4 || user.getUsername().trim().length() > 20) {
            throw new WarnException(ResultCostant.REGISTER_USERNAME_IS_WRONG_WARN);
        }
        if (user.getPassword().length() < 4 || user.getPassword().length() > 20) {
            throw new WarnException(ResultCostant.REGISTER_PASSWORD_IS_WRONG_WARN);
        }
        user.newCreate();
        customerService.register(user);
        return ResultUtil.success();
    }

    @PostMapping("/login")
    public Result login(User user) {
        LoginInfo loginInfo = customerService.login(user);
        String value = JSON.toJSONString(loginInfo);
        String key = JedisUtil.setex(NumberCostant.TOKEN_EXPIRE, value);
        return ResultUtil.success(key);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String key = request.getHeader(StringCostant.AUTH);
        if (key != null) {
            JedisUtil.del(key);
        }
        return ResultUtil.success();
    }

    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        LoginInfo loginInfo = (LoginInfo) request.getAttribute(StringCostant.LOGIN_INFO);
        return ResultUtil.success(loginInfo);
    }
}
