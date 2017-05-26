package pers.yuiz.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.util.EncodeUtil;
import pers.yuiz.customer.dao.RoleDao;
import pers.yuiz.customer.dao.UserDao;
import pers.yuiz.customer.dao.UserRoleDao;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.entity.UserRole;
import pers.yuiz.customer.service.CustomerService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 保存一个User
     *
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {
        return userDao.insert(user);
    }

    /**
     * 列出所有的User
     *
     * @return
     */
    @Override
    public List<User> listAllUsers() {
        return userDao.selectAll();
    }

    /**
     * 在实现的方法体中,抛出RuntimeException,测试Mysql事务
     *
     * @return
     */
    @Override
    public int txTest() {
        User user = new User();
        user.setUsername("tx" + UUID.randomUUID().toString().substring(0, 5));
        user.setPassword("tx" + UUID.randomUUID().toString().substring(0, 5));
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userDao.insert(user);
        throw new RuntimeException();
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public int register(User user) {
        String newPassword = EncodeUtil.MD5Hex(user.getPassword());
        user.setPassword(newPassword);
        int i = userDao.insert(user);
        if (i > 0) {
            UserRole userRole = new UserRole();
            userRole.newCreate();
            user = userDao.findByUsername(user.getUsername());
            userRole.setUserId(user.getId());
            userRole.setRoleId(1L);
            userRoleDao.insert(userRole);
        } else {
            throw new RuntimeException("注册失败");
        }
        return i;
    }

    /**
     * 根据用户名获取角色列表
     *
     * @param username
     * @return
     */
    @Override
    public List<String> listRoleNamesByUsername(String username) {
        return roleDao.listRoleNamesByUsername(username);
    }

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public String findPasswordByUsername(String username) {
        return userDao.findPasswordByUsername(username);
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        String newPassword = EncodeUtil.MD5Hex(user.getPassword());
        user.setPassword(newPassword);
        user = userDao.selectOne(user);
        if (user == null) {
            throw new WarnException(4999, "用户名或密码错误");
        }
        return user;
    }
}
