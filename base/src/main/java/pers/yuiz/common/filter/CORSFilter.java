package pers.yuiz.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.yuiz.common.costant.StringCostant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域过滤器
 */
@WebFilter(filterName = "CORSFilter", urlPatterns = "/*")
public class CORSFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(CORSFilter.class);
    private static String AccessControlAllowHeaders;
    private static String AccessControlAllowOrigin;
    private static String AccessControlAllowMethods;
    private static String AccessControlAllowCredentials;
    private static String AccessControlMaxAge;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.AccessControlAllowOrigin = "http://localhost";
        this.AccessControlAllowMethods = "GET, POST, OPTIONS, POST, DELETE";
        this.AccessControlAllowCredentials = "true";
        this.AccessControlMaxAge = "36000";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Origin, X-Requested-With, Content-Type, Accept, ");
        stringBuffer.append(StringCostant.auth);
        this.AccessControlAllowHeaders = stringBuffer.toString();
        logger.info("CORSFilter跨域过滤器初始化成功");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", this.AccessControlAllowOrigin);
        response.setHeader("Access-Control-Allow-Methods", this.AccessControlAllowMethods);
        response.setHeader("Access-Control-Allow-Headers", this.AccessControlAllowHeaders);
        response.setHeader("Access-Control-Allow-Credentials", this.AccessControlAllowCredentials);
        response.setHeader("Access-Control-Max-Age", this.AccessControlMaxAge);
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(200);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("CORSFilter跨域过滤器销毁成功");
    }
}
