package pers.yuiz.customer.service;

import org.springframework.transaction.annotation.Transactional;
import pers.yuiz.customer.entity.Role;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.vo.LoginInfo;

import java.util.List;

public interface CustomerService {

    /**
     * 保存一个User
     *
     * @param user
     * @return
     */
    public int saveUser(User user);

    /**
     * 列出所有的User
     *
     * @return
     */
    public List<User> listAllUsers();

    /**
     * 在实现的方法体中,抛出RuntimeException,测试Mysql事务
     *
     * @return
     */
    @Transactional
    public int txTest();

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public int register(User user);

    /**
     * 根据用户名获取角色列表
     *
     * @param username
     * @return
     */
    public List<String> listRoleNamesByUsername(String username);

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    public String findPasswordByUsername(String username);

    /**
     * 登录
     *
     * @param user
     * @return
     */
    public LoginInfo login(User user);
}
