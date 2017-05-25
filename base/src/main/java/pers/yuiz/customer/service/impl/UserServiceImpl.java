package pers.yuiz.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.yuiz.customer.dao.UserDao;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 保存一个User
     *
     * @param user
     * @return
     */
    @Override
    public int saveOne(User user) {
        return userDao.insert(user);
    }

    /**
     * 列出所有的User
     *
     * @return
     */
    @Override
    public List<User> listAll() {
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
        user.setName("tx" + UUID.randomUUID().toString().substring(0, 5));
        user.setPassword("tx" + UUID.randomUUID().toString().substring(0, 5));
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userDao.insert(user);
        throw new RuntimeException();
    }
}
