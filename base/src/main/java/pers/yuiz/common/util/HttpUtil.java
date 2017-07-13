package pers.yuiz.common.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.StringUtils;
import pers.yuiz.common.costant.NumberCostant;
import pers.yuiz.common.costant.StringCostant;
import pers.yuiz.customer.vo.LoginInfo;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 查找指定名的cookie
     *
     * @param cookies
     * @param name
     * @return
     */
    public static Cookie selectCookie(Cookie[] cookies, String name) {
        Cookie cookie = null;
        if (StringUtils.isEmpty(name)) {
            logger.error("查找的cookie名不能为空");
            throw new RuntimeException("查找的cookie名不能为空");
        }
        if (cookies == null || cookies.length == 0) {
            logger.error("需要被查询的cookies不能为空");
            throw new RuntimeException("需要被查询的cookies不能为空");
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                cookie = cookies[i];
                break;
            }
        }
        return cookie;
    }

    /**
     * 查找指定名的cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie selectCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        return selectCookie(cookies, name);
    }

    /**
     * 创建十小时的cookie
     *
     * @param name cookie名
     * @return
     */
    public static Cookie createCookie(String name) {
        return createCookie(name, NumberCostant.TOKEN_EXPIRE);
    }

    /**
     * 创建cookie
     *
     * @param name    cookie名
     * @param expires cookie有效时长
     * @return
     */
    public static Cookie createCookie(String name, Integer expires) {
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(name, uuid);
        cookie.setMaxAge(expires);
        cookie.setPath("/");
        return cookie;
    }

    /**
     * 返回用户的IP地址
     *
     * @param request
     * @return
     */
    public static String obtainIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 返回用户的IP地址
     *
     * @param request
     * @return
     */
    public static String obtainIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostName();
        }
        return ip;
    }

    /**
     * 从HttpServletRequest请求中获取用户登录信息放入attribute中,并延长时效
     *
     * @param request
     * @return
     */
    public static void prolongTokenAndPutLoginInfo(HttpServletRequest request) {
        LoginInfo loginInfo = prolongTokenAndObtainLoginInfo(request);
        request.setAttribute(StringCostant.LOGIN_INFO, loginInfo);
    }

    /**
     * 从HttpServletRequest请求中获取用户登录信息,并延长时效
     *
     * @param request
     * @return
     */
    public static LoginInfo prolongTokenAndObtainLoginInfo(HttpServletRequest request) {
        LoginInfo loginInfo = null;
        String key = request.getHeader(StringCostant.AUTH);
        Jedis jedis = null;
        try {
            if (key != null) {
                jedis = JedisUtil.createJedis();
                String value = jedis.get(key);
                if (value != null) {
                    jedis.expire(key, NumberCostant.TOKEN_EXPIRE);
                    loginInfo = JSON.parseObject(value, LoginInfo.class);
                }
            }
            return loginInfo;
        } finally {
            JedisUtil.returnResource(jedis);
        }
    }
}
