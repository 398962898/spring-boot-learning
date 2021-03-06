package pers.yuiz.customer.mapper;

import org.apache.ibatis.annotations.Param;
import pers.yuiz.common.base.BaseMapper;
import pers.yuiz.customer.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户名获取角色名列表
     *
     * @param username
     * @return
     */
    public List<String> listRoleNamesByUsername(@Param("username") String username);
}