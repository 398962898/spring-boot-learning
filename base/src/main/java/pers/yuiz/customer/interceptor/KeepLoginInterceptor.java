package pers.yuiz.customer.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.yuiz.common.costant.StringCostant;
import pers.yuiz.common.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KeepLoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(KeepLoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object o = request.getAttribute(StringCostant.LOGIN_INFO);
        if (o != null) {
            String ip = HttpUtil.obtainIp(request);
            logger.warn("{}用户伪造登录状态", ip);
            return false;
        }
        HttpUtil.prolongTokenAndPutLoginInfo(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
