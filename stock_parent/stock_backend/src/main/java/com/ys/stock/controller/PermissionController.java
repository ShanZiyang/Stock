package com.ys.stock.controller;

import com.ys.stock.annotation.Log;
import com.ys.stock.common.domain.PermissionsTreeDomain;
import com.ys.stock.enums.BusinessType;
import com.ys.stock.pojo.SysPermission;
import com.ys.stock.service.PermissionService;
import com.ys.stock.vo.req.AddPermissionVo;
import com.ys.stock.vo.req.UpdatePermissionVo;
import com.ys.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/20 10:59
 */
@RestController
@RequestMapping("/api")
@Api(tags = "菜单权限控制")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("菜单权限管理")
    @GetMapping("/permissions")
    public R<List<SysPermission>> getPermissions(){
        return permissionService.getPermissions();
    }

    @ApiOperation("添加菜单权限时所属菜单回显")
    @GetMapping("/permissions/tree")
    public R<List<PermissionsTreeDomain>> getPermissionsTree(){
        return permissionService.getPermissionsTree();
    }

    @ApiOperation("添加菜单权限")
    @ApiImplicitParam(name = "addPermissionVo",value = "添加菜单权限VO")
    @Log(title = "添加菜单",businessType = BusinessType.INSERT)
    @PostMapping("/permission")
    public R<String> insertPermissions(@RequestBody AddPermissionVo addPermissionVo){
        return permissionService.insertPermissions(addPermissionVo);
    }

    @ApiOperation("修改菜单权限")
    @ApiImplicitParam(name = "vo",value = "修改菜单权限VO")
    @Log(title = "修改菜单",businessType = BusinessType.UPDATE)
    @PutMapping("/permission")
    public R<String> updatePermission(@RequestBody UpdatePermissionVo vo){
        return permissionService.updatePermission(vo);
    }

    @ApiOperation("删除菜单权限")
    @ApiImplicitParam(name = "id",value = "删除菜单id")
    @Log(title = "删除菜单",businessType = BusinessType.DELETE)
    @DeleteMapping("/permission/{id}")
    public R<String> deletePermission(@PathVariable String id){
        return permissionService.deletePermission(id);
    }
}
