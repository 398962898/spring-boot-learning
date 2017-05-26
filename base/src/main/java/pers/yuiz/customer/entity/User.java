package pers.yuiz.customer.entity;

import pers.yuiz.common.entity.BaseEntity;

import java.util.Date;

public class User extends BaseEntity{

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别,0表示未设定,1表示男,2表示女
     */
    private Byte sex;

    /**
     * 电子邮箱地址
     */
    private String email;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 生日
     */
    private Date birthday;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}