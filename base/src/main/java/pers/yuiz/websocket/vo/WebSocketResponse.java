package pers.yuiz.websocket.vo;

import pers.yuiz.common.base.BaseEntity;

public class WebSocketResponse extends BaseEntity {

    private String username;

    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
