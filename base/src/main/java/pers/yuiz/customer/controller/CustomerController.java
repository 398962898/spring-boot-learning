package pers.yuiz.customer.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.common.costant.ResultCostant;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.service.CustomerService;
import pers.yuiz.customer.vo.LoginInfo;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;


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
    public Result login(User user, HttpSession session) {
        LoginInfo loginInfo = customerService.login(user);
        if (loginInfo != null) {
            session.setAttribute("loginInfo", loginInfo);
        }
        String key = "loginInfo:" + UUID.randomUUID().toString();
        String value = JSON.toJSONString(loginInfo);
        JedisUtil.setex(key, 360000, value);
        return ResultUtil.success(key);
    }

    @PostMapping("/logout")
    public Result logout(String auth, HttpSession session, HttpServletRequest request) {
        session.removeAttribute("loginInfo");
        if (auth != null) {
            JedisUtil.del(auth);
        }
        auth = request.getHeader("auth");
        if (auth != null) {
            JedisUtil.del(auth);
        }
        return ResultUtil.success();
    }

    @GetMapping("/info")
    public Result info(HttpSession session) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        return ResultUtil.success(loginInfo);
    }
}
