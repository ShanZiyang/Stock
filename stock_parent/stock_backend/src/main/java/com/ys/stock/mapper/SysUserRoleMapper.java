package com.ys.stock.mapper;

import com.ys.stock.pojo.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YS
 * @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
 * @createDate 2023-06-15 18:06:09
 * @Entity com.ys.stock.pojo.SysUserRole
 */
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * 删除用户原来所拥有的角色
     *
     * @param userId
     */
    void deleteByUserId(@Param("userId") String userId);

    /**
     * 批量插入用户角色
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<SysUserRole> list);

    List<String> findRoleIdsByUserId(@Param("userId") String userId);

}
