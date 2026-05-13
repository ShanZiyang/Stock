package com.ys.stock.service;

import com.ys.stock.vo.req.AddRoleAndPermission;
import com.ys.stock.vo.req.RolePageReqVo;
import com.ys.stock.vo.req.UpdateRoleVo;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.PermissionRespNodeVo;
import com.ys.stock.vo.resp.R;

import java.util.List;
import java.util.Map;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 9:14
 */
public interface RoleService {
    /**
     *分页查询当前角色信息
     * @return
     */
    R<PageResult> QueryRolesByPage(RolePageReqVo vo);

    /**
     * 树状结构回显权限集合,底层通过递归获取权限数据集合
     * @return
     */
    R<List<PermissionRespNodeVo>> getTreePermissions();

    /**
     *  添加角色和角色关联权限
     * @param addRoleAndPermission
     * @return
     */
    R<String> AddRoleAndPermission(AddRoleAndPermission addRoleAndPermission);

    R<List> getRolesOfUserInfo(String roleId);

    R<Map> updatePermissions(UpdateRoleVo updateRoleVo);

    R<String> deleteRole(String roleId);

    R<String> updateStatus(String roleId, Integer status);

}
