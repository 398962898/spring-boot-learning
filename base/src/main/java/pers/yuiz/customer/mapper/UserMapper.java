package pers.yuiz.customer.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yuiz.common.base.BaseMapper;
import pers.yuiz.customer.entity.User;
import pers.yuiz.customer.vo.LoginInfo;

public interface UserMapper extends BaseMapper<User> {

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

    /**
     * 根据用户名获取记录数
     *
     * @param username
     * @return
     */
    public Long countByUsername(@Param("username") String username);

    /**
     * 根据用户id获取登录信息
     *
     * @param id
     * @return
     */
    public LoginInfo findLoginInfoByUserId(@Param("id") Long id);
}