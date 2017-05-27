package pers.yuiz.customer.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.yuiz.common.costant.ResultCostant;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.customer.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(CustomerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object o = request.getAttribute("user");
        if (o != null && o instanceof User) {
            return true;
        }
        throw new WarnException(ResultCostant.NOT_LOGIN_WARN);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
