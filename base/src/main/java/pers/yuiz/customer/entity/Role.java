package pers.yuiz.customer.entity;

import pers.yuiz.common.entity.BaseEntity;

import java.util.Date;

public class Role extends BaseEntity{

    /**
     * 角色名
     */
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}