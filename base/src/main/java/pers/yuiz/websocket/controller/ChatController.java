package pers.yuiz.websocket.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pers.yuiz.common.costant.StringCostant;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.customer.vo.LoginInfo;
import pers.yuiz.websocket.vo.WebSocketResponse;

@Controller
public class ChatController {
    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);

    /**
     * MessageMapping:消息接收路径,客户端发送消息路径收取的方法
     * SendTo:消息广播路径,订阅该路径的客户端可以接收发送到该方法的消息
     *
     * @param message
     * @return
     */
    @MessageMapping("/websockethello")
    @SendTo("/topic/websockethello")
    public WebSocketResponse hello(@Header(name = StringCostant.auth) String key, String message) {
        WebSocketResponse webSocketResponse = new WebSocketResponse();
        webSocketResponse.newCreate();
        webSocketResponse.setMessage(message);
        webSocketResponse.setUsername("游客");
        if (key != null) {
            String value = JedisUtil.get(key);
            LoginInfo loginInfo = JSON.parseObject(value, LoginInfo.class);
            if (loginInfo != null) {
                webSocketResponse.setUsername(loginInfo.getUsername());
            }
        }
        logger.info("{}发送了{}", webSocketResponse.getUsername(), webSocketResponse.getMessage());
        return webSocketResponse;
    }
}
