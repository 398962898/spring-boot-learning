package pers.yuiz.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.yuiz.common.util.xss.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(filterName = "XssFilter",urlPatterns = "/*",asyncSupported = true)
public class XssFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(XssFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("XssFilter跨站请求防范过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
        logger.info("XssFilter跨站请求防范过滤器销毁");
    }
}
