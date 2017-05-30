package pers.yuiz.customer.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 登录信息
 */
public class LoginInfo implements Serializable {
    private Long userId;
    private String username;
    private String headImg;
    private Byte sex;
    private List<RoleVo> roleVoList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public List<RoleVo> getRoleVoList() {
        return roleVoList;
    }

    public void setRoleVoList(List<RoleVo> roleVoList) {
        this.roleVoList = roleVoList;
    }
}
