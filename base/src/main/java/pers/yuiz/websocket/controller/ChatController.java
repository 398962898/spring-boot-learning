package pers.yuiz.websocket.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.yuiz.common.util.JedisUtil;
import pers.yuiz.customer.vo.LoginInfo;
import pers.yuiz.websocket.vo.WebSocketResponse;

@Controller
public class ChatController {
    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/websockethello")
    @SendTo("/topic/websockethello")
    public WebSocketResponse hello(@Header("auth") String auth, String message) {
        WebSocketResponse webSocketResponse = new WebSocketResponse();
        webSocketResponse.newCreate();
        webSocketResponse.setMessage(message);
        webSocketResponse.setUsername("游客");
        if (auth != null) {
            String value = JedisUtil.get(auth);
            LoginInfo loginInfo = JSON.parseObject(value, LoginInfo.class);
            if (loginInfo != null) {
                webSocketResponse.setUsername(loginInfo.getUsername());
            }
        }
        logger.info("{}发送了{}", webSocketResponse.getUsername(), webSocketResponse.getMessage());
        return webSocketResponse;
    }
}
