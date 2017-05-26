package pers.yuiz.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CookieUtil {
    private final static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

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
        return createCookie(name, 360000);
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
}
