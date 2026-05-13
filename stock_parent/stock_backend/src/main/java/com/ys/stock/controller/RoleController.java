package com.ys.stock.controller;

import com.ys.stock.annotation.Log;
import com.ys.stock.enums.BusinessType;
import com.ys.stock.service.RoleService;
import com.ys.stock.vo.req.AddRoleAndPermission;
import com.ys.stock.vo.req.RolePageReqVo;
import com.ys.stock.vo.req.UpdateRoleVo;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.PermissionRespNodeVo;
import com.ys.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 9:13
 * @description 角色处理器
 */
@RestController
@Api(tags = "权限控制")
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("权限树状结构")
    @GetMapping("/permissions/tree/all")
    public R<List<PermissionRespNodeVo>> getAllPermissions() {
        return roleService.getTreePermissions();
    }


    @ApiOperation("角色信息查询")
    @ApiImplicitParam(name = "vo",value = "角色信息分页VO")
    @PostMapping("/roles")
    public R<PageResult> QueryRolesByPage(@RequestBody RolePageReqVo vo) {
        return roleService.QueryRolesByPage(vo);
    }


    @ApiOperation("添加角色和角色关联权限")
    @ApiImplicitParam(name = "addRoleAndPermission",value = "添加角色和权限VO")
    @Log(title = "添加关联权限",businessType = BusinessType.INSERT)
    @PostMapping("/role")
    public R<String> AddRoleAndPermission(@RequestBody AddRoleAndPermission addRoleAndPermission) {
        return roleService.AddRoleAndPermission(addRoleAndPermission);
    }

    @ApiOperation("角色权限查询")
    @ApiImplicitParam(name = "roleId",value = "角色id")
    @GetMapping("/role/{roleId}")
    public R<List> getPermissionsById(@PathVariable String roleId) {
        return roleService.getRolesOfUserInfo(roleId);

    }

    @ApiOperation("更新角色权限")
    @ApiImplicitParam(name = "updateRoleVo",value = "更新角色权限VO")
    @Log(title = "更新角色",businessType = BusinessType.UPDATE)
    @PutMapping("/role")
    public R<Map> updatePermissions(@RequestBody UpdateRoleVo updateRoleVo){
        return roleService.updatePermissions(updateRoleVo);
    }

    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "roleId",value = "角色id")
    @Log(title = "删除角色",businessType = BusinessType.DELETE)
    @DeleteMapping("/role/{roleId}")
    public R<String> deleteRole(@PathVariable String roleId){
        return roleService.deleteRole(roleId);
    }

    @ApiOperation("更改角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色id"),
            @ApiImplicitParam(name = "status",value = "角色状态")
    })
    @Log(title = "更新角色状态",businessType = BusinessType.UPDATE)
    @PostMapping("/role/{roleId}/{status}")
    public R<String> updateStatus(@PathVariable String roleId,@PathVariable Integer status){
        return roleService.updateStatus(roleId,status);
    }



}
