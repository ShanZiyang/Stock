package com.ys.stock.mapper;

import com.ys.stock.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author YS
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2023-06-15 18:06:09
* @Entity com.ys.stock.pojo.SysRole
*/
public interface SysRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);


    /**
     * 分页查询当前角色信息
     * @return
     */
    List<SysRole> QueryRolesByPage();

    /**
     * 获取角色集合 基于角色鉴权注解需要将角色前追加ROLE_
     * @param id
     * @return
     */
    List<SysRole> getRoleByUserId(@Param("id") String id);

    int updateStatus(@Param("roleId") String roleId, @Param("status") int status);

    int openStatus(@Param("roleId") String roleId, @Param("statusOpen") int statusOpen);

    List<SysRole> findAll(@Param("statusOne") int statusOne);


    String getRileId(@Param("username") String username);

}
