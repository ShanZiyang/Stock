package com.ys.stock.mapper;

import com.ys.stock.common.domain.PermissionsTreeDomain;
import com.ys.stock.pojo.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author YS
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2023-06-15 18:06:09
* @Entity com.ys.stock.pojo.SysPermission
*/
public interface SysPermissionMapper{

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    /** 根据用户id查询用户信息
     * @param userId
     * @return
     */
    List<SysPermission> getPermissionByUserId(@Param("userId") String userId);

    /**
     * 树状结构回显权限集合,底层通过递归获取权限数据集合
     * @return
     */
    List<SysPermission> getAllPermission();


    List<PermissionsTreeDomain> getAllPermissionsTree();


    int findChildrenCountByParentId(@Param("id") String id);

}
