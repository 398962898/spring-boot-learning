package pers.yuiz.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yuiz.common.costant.ResultCostant;
import pers.yuiz.common.exception.WarnException;
import pers.yuiz.common.util.EncodeUtil;
import pers.yuiz.customer.mapper.RoleMapper;
import pers.yuiz.customer.mapper.UserMapper;
import pers.yuiz.customer.mapper.UserRoleMapper;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.entity.UserRole;
import pers.yuiz.customer.service.CustomerService;
import pers.yuiz.customer.vo.LoginInfo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 保存一个User
     *
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    /**
     * 列出所有的User
     *
     * @return
     */
    @Override
    public List<User> listAllUsers() {
        return userMapper.selectAll();
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
        userMapper.insert(user);
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
        Long userCount = userMapper.countByUsername(user.getUsername());
        if (userCount > 0) {
            throw new WarnException(ResultCostant.REGISTER_USERNAME_IS_REPEATED_WARN);
        }
        int i = 0;
        String newPassword = EncodeUtil.MD5Hex(user.getPassword());
        user.setPassword(newPassword);
        i = userMapper.insertSelective(user);
        if (i > 0) {
            UserRole userRole = new UserRole();
            userRole.newCreate();
            user = userMapper.findByUsername(user.getUsername());
            userRole.setUserId(user.getId());
            userRole.setRoleId(1L);
            userRoleMapper.insert(userRole);
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
        return roleMapper.listRoleNamesByUsername(username);
    }

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public String findPasswordByUsername(String username) {
        return userMapper.findPasswordByUsername(username);
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public LoginInfo login(User user) {
        String newPassword = EncodeUtil.MD5Hex(user.getPassword());
        user.setPassword(newPassword);
        user = userMapper.selectOne(user);
        if (user == null) {
            throw new WarnException(ResultCostant.LOGIN_ARGS_IS_WRONG_WARN);
        } else {
            LoginInfo loginInfo = userMapper.findLoginInfoByUserId(user.getId());
            return loginInfo;
        }
    }

}