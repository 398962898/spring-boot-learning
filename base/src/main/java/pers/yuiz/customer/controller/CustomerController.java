package pers.yuiz.customer.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.common.costant.ResultCostant;
import pers.yuiz.common.costant.StringCostant;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.util.CookieUtil;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.service.CustomerService;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


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
    public Result login(User user, HttpServletRequest request) {
        Jedis jedis = null;
        try {
            user = customerService.login(user);
            if (user != null) {
                jedis = JedisUtil.createJedis();
                Cookie cookie = CookieUtil.selectCookie(request, StringCostant.baseCookie);
                String key = cookie.getValue();
                String value = JSON.toJSONString(user);
                jedis.setex(key, 18000, value);
            }
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return ResultUtil.success();
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.createJedis();
            Cookie cookie = CookieUtil.selectCookie(request, StringCostant.baseCookie);
            jedis.del(cookie.getValue());
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return ResultUtil.success();
    }

    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return ResultUtil.success(user);
    }
}
