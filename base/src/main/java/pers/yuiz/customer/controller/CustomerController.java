package pers.yuiz.customer.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.interceptor.KeepLoginInterceptor;
import pers.yuiz.common.util.CookieUtil;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.common.util.ResultUtil;
import pers.yuiz.common.vo.Result;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.service.CustomerService;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/register")
    public Result register(User user) {
        user.newCreate();
        customerService.register(user);
        return ResultUtil.success();
    }

    @RequestMapping("/login")
    public Result login(User user, HttpServletRequest request) {
        Jedis jedis = null;
        try {
            user = customerService.login(user);
            if (user != null) {
                jedis = JedisUtil.createJedis();
                Cookie cookie = CookieUtil.selectCookie(request, KeepLoginInterceptor.myCookie);
                String key = cookie.getValue();
                String value = JSON.toJSONString(user);
                jedis.setex(key, 18000, value);
            }
        } finally {
            JedisUtil.returnResource(jedis);
        }
        return ResultUtil.success();
    }
}
