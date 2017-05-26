package pers.yuiz.customer.dao;

import org.apache.ibatis.annotations.Param;
import pers.yuiz.customer.entity.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleDao extends Mapper<Role> {
    /**
     * 根据用户名获取角色名列表
     *
     * @param username
     * @return
     */
    public List<String> listRoleNamesByUsername(@Param("username") String username);
}