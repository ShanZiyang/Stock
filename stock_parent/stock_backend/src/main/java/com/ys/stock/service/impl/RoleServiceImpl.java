package com.ys.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ys.stock.constants.MagicValue;
import com.ys.stock.exception.BusinessException;
import com.ys.stock.mapper.SysPermissionMapper;
import com.ys.stock.mapper.SysRoleMapper;
import com.ys.stock.mapper.SysRolePermissionMapper;
import com.ys.stock.pojo.SysPermission;
import com.ys.stock.pojo.SysRole;
import com.ys.stock.pojo.SysRolePermission;
import com.ys.stock.service.PermissionService;
import com.ys.stock.service.RoleService;
import com.ys.stock.utils.IdWorker;
import com.ys.stock.vo.req.AddRoleAndPermission;
import com.ys.stock.vo.req.RolePageReqVo;
import com.ys.stock.vo.req.UpdateRoleVo;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.PermissionRespNodeVo;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 9:14
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;


    /**
     * 分页查询当前角色信息
     *
     * @return
     */
    @Override
    public R<PageResult> QueryRolesByPage(RolePageReqVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysRole> allRoles = sysRoleMapper.QueryRolesByPage();
        if (CollectionUtils.isEmpty(allRoles)) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        PageInfo<SysRole> pageInfo = new PageInfo<>(allRoles);
        PageResult<SysRole> pageResult = new PageResult<>(pageInfo);
        return R.ok(pageResult);
    }

    /**
     * 树状结构回显权限集合,底层通过递归获取权限数据集合
     *
     * @return
     */
    @Override
    public R<List<PermissionRespNodeVo>> getTreePermissions() {
        //获取所有权限集合
        List<SysPermission> permissions = sysPermissionMapper.getAllPermission();
        //获取树状权限数据
        List<PermissionRespNodeVo> tree = permissionService.getTree(permissions, "0", false);
        return R.ok(tree);
    }

    /**
     * 添加角色和角色关联权限
     *
     * @param addRoleAndPermission
     * @return
     */
    @Override
    public R<String> AddRoleAndPermission(AddRoleAndPermission addRoleAndPermission) {
        //先添加角色
        String roleId = idWorker.nextId() + "";
        SysRole sysRole = SysRole.builder()
                .id(roleId)
                .name(addRoleAndPermission.getName())
                .description(addRoleAndPermission.getDescription())
                .status(MagicValue.STATUS_OPEN)
                .deleted(MagicValue.STATUS_NOT_DEL)
                .createTime(new Date()).build();
        int count = sysRoleMapper.insert(sysRole);
        //添加角色对应的权限
        List<String> permissionsIds = addRoleAndPermission.getPermissionsIds();
        int success = 0;
        if (!CollectionUtils.isEmpty(permissionsIds)) {
            for (String permissionsId : permissionsIds) {
                String id = idWorker.nextId() + "";
                Date createTime = new Date();
                success = sysRolePermissionMapper.addPermissionOfRole(id, roleId, permissionsId, createTime);
            }
        } else {
            return R.ok(ResponseCode.ERROR.getMessage());
        }
        if (count != MagicValue.SUCCESSVAL || success != MagicValue.SUCCESSVAL) {
            return R.error(ResponseCode.ERROR.getMessage());
        }

        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    @Override
    public R<List> getRolesOfUserInfo(String roleId) {
        List<String> Ids = sysRolePermissionMapper.getPerIdsOfUser(roleId);
        return R.ok(Ids);
    }

    @Override
    public R<Map> updatePermissions(UpdateRoleVo updateRoleVo) {
        //1.更新角色信息
        SysRole role = SysRole.builder()
                .id(updateRoleVo.getId())
                .name(updateRoleVo.getName())
                .updateTime(new Date())
                .description(updateRoleVo.getDescription())
                .build();
        int updateCount = this.sysRoleMapper.updateByPrimaryKeySelective(role);
        if (updateCount != MagicValue.SUCCESSVAL) {
            throw new BusinessException(ResponseCode.ROLE_FAIL.getMessage());
//            return R.error(ResponseCode.ERROR.getMessage());
        }
        //2.删除当前角色关联的权限信息
        sysRolePermissionMapper.deleteByRoleId(updateRoleVo.getId());
        //3.插入当前角色权限信息
        List<String> permissionsIds = updateRoleVo.getPermissionsIds();
        if (!CollectionUtils.isEmpty(permissionsIds)) {
            List<SysRolePermission> rps = permissionsIds.stream().map(permissionId -> {
                SysRolePermission rp = SysRolePermission.builder()
                        .roleId(updateRoleVo.getId())
                        .permissionId(permissionId)
                        .createTime(new Date())
                        .id(idWorker.nextId() + "")
                        .build();
                return rp;
            }).collect(Collectors.toList());
            this.sysRolePermissionMapper.addRolePermissionBatch(rps);
        }
//        return R.ok(ResponseCode.SUCCESS.getMessage());

        //TODO 权限添加后左侧菜单栏自动更新--前端待完善
        //获取所有权限集合
        List<SysPermission> permissions = sysPermissionMapper.getAllPermission();
        //获取树状权限数据
        List<PermissionRespNodeVo> tree = permissionService.getTree(permissions, "0", false);
        Map map = new HashMap();
        map.put("siderList",tree);
        return R.ok(map);
    }

    @Override
    public R<String> deleteRole(String roleId) {
        int count = sysRoleMapper.deleteByPrimaryKey(Long.valueOf(roleId));
        if (count != MagicValue.SUCCESSVAL) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());

    }

    @Override
    public R<String> updateStatus(String roleId, Integer status) {
        int count = 0;
        if (status == MagicValue.STATUS_OPEN) {
            count = sysRoleMapper.openStatus(roleId, MagicValue.STATUS_OPEN);

        } else {
            count = sysRoleMapper.updateStatus(roleId, MagicValue.STATUS_NOT_NORMAL);

        }
        if (count != MagicValue.SUCCESSVAL) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }
}