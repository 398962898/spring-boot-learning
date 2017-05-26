package pers.yuiz.customer.dao;

import org.apache.ibatis.annotations.Param;
import pers.yuiz.customer.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserDao extends Mapper<User> {

    /**
     * 根据用户名获取用户密码
     *
     * @param username
     * @return
     */
    public String findPasswordByUsername(@Param("username") String username);

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    public User findByUsername(@Param("username") String username);
}