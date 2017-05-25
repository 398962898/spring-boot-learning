package pers.yuiz.customer.service;

import org.springframework.transaction.annotation.Transactional;
import pers.yuiz.customer.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 保存一个User
     *
     * @param user
     * @return
     */
    public int saveOne(User user);

    /**
     * 列出所有的User
     *
     * @return
     */
    public List<User> listAll();

    /**
     * 在实现的方法体中,抛出RuntimeException,测试Mysql事务
     *
     * @return
     */
    @Transactional
    public int txTest();
}
