package pers.yuiz.common.interceptor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.yuiz.common.costant.StringCostant;
import pers.yuiz.common.util.CookieUtil;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.customer.entity.User;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KeepLoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(KeepLoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Cookie baseCookieName = null;
        if (cookies == null || cookies.length == 0) {
            baseCookieName = CookieUtil.createCookie(StringCostant.baseCookie);
        } else {
            for (int i = 0; i < cookies.length; i++) {
                if (StringCostant.baseCookie.equals(cookies[i].getName())) {
                    baseCookieName = cookies[i];
                    break;
                }
                if (i == cookies.length - 1) {
                    baseCookieName = CookieUtil.createCookie(StringCostant.baseCookie);
                }
            }
        }
        baseCookieName.setMaxAge(360000);
        response.addCookie(baseCookieName);
        Jedis jedis = null;
        try {
            jedis = JedisUtil.createJedis();
            String key = baseCookieName.getValue();
            String userJson = jedis.get(key);
            if (userJson != null) {
                User user = JSON.parseObject(userJson, User.class);
                request.setAttribute("user", user);
                jedis.expire(key, 360000);
            }
            return true;
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
