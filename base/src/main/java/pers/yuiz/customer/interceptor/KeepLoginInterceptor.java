package pers.yuiz.customer.interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.yuiz.common.costant.StringCostant;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.customer.vo.LoginInfo;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KeepLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = request.getHeader(StringCostant.auth);
        Jedis jedis = null;
        try {
            if (key != null) {
                jedis = JedisUtil.createJedis();
                String value = jedis.get(key);
                if (value != null) {
                    LoginInfo loginInfo = JSON.parseObject(value, LoginInfo.class);
                    request.setAttribute("loginInfo", loginInfo);
                    jedis.expire(key, 36000);
                }
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
