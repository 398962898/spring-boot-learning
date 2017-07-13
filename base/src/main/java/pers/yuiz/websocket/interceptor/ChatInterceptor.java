package pers.yuiz.websocket.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import pers.yuiz.common.util.HttpUtil;
import pers.yuiz.customer.vo.LoginInfo;

import java.util.Map;

public class ChatInterceptor extends HttpSessionHandshakeInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(ChatInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String ip = HttpUtil.obtainIp(request);
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            LoginInfo loginInfo = HttpUtil.prolongTokenAndObtainLoginInfo(servletRequest.getServletRequest());
            if (loginInfo != null) {
                logger.info("用户 {} 于ip地址 {} 连接成功", loginInfo.getUsername(), ip);
                return true;
            }
        }
        logger.warn("用户于ip地址 {} 尝试未登录连接", ip);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
