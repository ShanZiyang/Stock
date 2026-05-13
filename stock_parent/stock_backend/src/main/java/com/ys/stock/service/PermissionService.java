package com.ys.stock.service;

import com.ys.stock.common.domain.PermissionsTreeDomain;
import com.ys.stock.pojo.SysPermission;
import com.ys.stock.vo.req.AddPermissionVo;
import com.ys.stock.vo.req.UpdatePermissionVo;
import com.ys.stock.vo.resp.PermissionRespNodeVo;
import com.ys.stock.vo.resp.R;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author  杨森
 * @date  2023/7/12 8:57
 * @version 1.0
 * @description
 */

public interface PermissionService {
    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    List<SysPermission> getPermissionByUserId(@Param("userId") String userId);

    /**
     * @param permissions    权限树状集合
     * @param pid            权限父id，顶级权限的pid默认为0
     * @param isOnlyMenuType true:遍历到菜单，  false:遍历到按钮
     *                       type: 目录1 菜单2 按钮3
     * @return
     */
    List<PermissionRespNodeVo> getTree(List<SysPermission> permissions, String pid, boolean isOnlyMenuType);

    R<List<SysPermission>> getPermissions();

    R<List<PermissionsTreeDomain>> getPermissionsTree();

    R<String> insertPermissions(AddPermissionVo addPermissionVo);

    R<String> updatePermission(UpdatePermissionVo vo);

    R<String> deletePermission(String id);



    //......
}
