package pers.yuiz.common.interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.yuiz.common.util.CookieUtil;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.customer.entity.User;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KeepLoginInterceptor implements HandlerInterceptor {
    public final static String myCookie = "yuizId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Cookie yuizId = null;
        if (cookies == null || cookies.length == 0) {
            yuizId = CookieUtil.createCookie(myCookie);
        } else {
            for (int i = 0; i < cookies.length; i++) {
                if (myCookie.equals(cookies[i].getName())) {
                    yuizId = cookies[i];
                    break;
                }
                if (i == cookies.length - 1) {
                    yuizId = CookieUtil.createCookie(myCookie);
                }
            }
        }
        yuizId.setMaxAge(360000);
        response.addCookie(yuizId);
        Jedis jedis = null;
        try {
            jedis = JedisUtil.createJedis();
            String key = yuizId.getValue();
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
