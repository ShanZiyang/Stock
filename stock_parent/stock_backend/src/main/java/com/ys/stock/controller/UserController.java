package com.ys.stock.controller;

import com.ys.stock.annotation.Log;
import com.ys.stock.common.domain.UrlDomain;
import com.ys.stock.enums.BusinessType;
import com.ys.stock.pojo.SysUser;
import com.ys.stock.service.UpLoadService;
import com.ys.stock.service.UserService;
import com.ys.stock.vo.req.*;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.Rows;
import com.ys.stock.vo.resp.UserOwnRoleRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 杨森
 * @description: 定义用户访问层
 * @date 2023年06月15日 17:52
 */
@RestController
@Api(tags = "用户访问")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private UpLoadService upLoadService;

//    /**
//     * 用户登录功能实现
//     * @param vo
//     * @return
//     */
//    @PostMapping("/login")
//    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){
//        R<LoginRespVo> r= this.userService.login(vo);
//        return r;
//    }

    @ApiOperation("生成验证码")
    @GetMapping("/captcha")
    public R<Map> generateCaptcha(){
        return this.userService.generateCaptcha();
    }

    @ApiOperation("多条件综合查询")
    @ApiImplicitParam(name = "usersReqVo",value = "查询条件VO")
    @PostMapping("/users")
    public R<PageResult<Rows>> QueryConditionsUsers(@RequestBody QueryConditionsUsersReqVo usersReqVo) {
        return userService.MoreQueryUsers(usersReqVo);
    }

    @ApiOperation("添加用户")
    @ApiImplicitParam(name = "vo",value = "添加用户VO")
    @Log(title = "添加用户",businessType = BusinessType.INSERT)
    @PostMapping("/user")
    public R<String> AddUser(@RequestBody UserAddReqVo vo) {
        return userService.AddUser(vo);
    }


    @ApiOperation("更新用户角色信息")
    @ApiImplicitParam(name = "vo",value = "更新用户角色信息VO")
    @Log(title = "更新用户",businessType = BusinessType.UPDATE)
    @PutMapping("/user/roles")
    public R<String> UpdateRloesOfUser(@RequestBody UserOwnRoleReqVo vo) {
        return userService.UpdateRloesOfUser(vo);
    }


    @ApiOperation("获取用户拥有的角色")
    @ApiImplicitParam(name = "userId",value = "用户id")
    @GetMapping("/user/roles/{userId}")
    public R<UserOwnRoleRespVo> getRolesOfUserInfo(@PathVariable String userId) {
        return userService.getRolesOfUserInfo(userId);
    }

    @ApiOperation("批量删除用户")
    @ApiImplicitParam(name = "userIds",value = "id集合")
    @Log(title = "批量删除用户",businessType = BusinessType.DELETE)
    @DeleteMapping("/user")
    public R<String> deleteUsers(@RequestBody List<String> userIds) {
        return userService.deleteUsers(userIds);
    }

    @ApiOperation("根据用户id查询用户信息")
    @ApiImplicitParam(name = "id",value = "用户id")
    @GetMapping("/user/info/{id}")
    public R<SysUser> getUserInfoById(@PathVariable String id) {
        return userService.getUserInfoById(id);
    }

    @ApiOperation("更新用户信息")
    @ApiImplicitParam(name = "vo",value = "更新信息VO")
    @Log(title = "更新用户",businessType = BusinessType.UPDATE)
    @PutMapping("/user")
    public R<String> updateUserInfo(@RequestBody UserEditReqVO vo) {
        return userService.updateUserInfo(vo);
    }

    @ApiOperation("密码重置")
    @ApiImplicitParam(name = "id",value = "用户id")
    @Log(title = "密码重置",businessType = BusinessType.UPDATE)
    @GetMapping("/user/password/{id}")
    public R<String> updatePasById(@PathVariable String id){
        return userService.updatePasById(id);
    }

    @ApiOperation("密码修改")
    @ApiImplicitParam(name = "updatePasVo",value = "更新密码VO")
    @Log(title = "密码修改",businessType = BusinessType.UPDATE)
    @PutMapping("user/password")
    public R<String> updatePas(@RequestBody UpdatePasVo updatePasVo){
        return userService.updatePas(updatePasVo);
    }

    @ApiOperation("上传头像")
    @ApiImplicitParam(name = "img",value = "头像")
    @Log(title = "上传头像",businessType = BusinessType.UPLOAD_IMG)
    @PostMapping("/uploadImg")
    public R upLoadImg(MultipartFile img){
        return upLoadService.uploadImg(img);
    }

    @ApiOperation("更新头像地址")
    @ApiImplicitParam(name = "url",value = "头像地址")
    @Log(title = "更新头像地址",businessType = BusinessType.UPDATE)
    @PutMapping("/updateUrl")
    public R upLoadUrl(@RequestBody UrlDomain urlDomain){
        return upLoadService.updateUrl(urlDomain);
    }
}
