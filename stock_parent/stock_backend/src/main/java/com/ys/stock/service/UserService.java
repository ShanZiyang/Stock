package com.ys.stock.service;

import com.ys.stock.pojo.SysUser;
import com.ys.stock.vo.req.*;
import com.ys.stock.vo.resp.*;

import java.util.List;
import java.util.Map;

/**
 * @author 杨森
 * @description: 用户服务
 * @date 2023年06月15日 22:00
 */

public interface UserService {
    /**
     * 用户登录功能实现
     * @param vo
     * @return
     */
    R<LoginRespVo> login(LoginReqVo vo);

    R<Map> generateCaptcha();

    /**
     * 用户管理多条件查询
     *
     * @param usersReqVo 查询条件
     * @return
     */
    R<PageResult<Rows>> MoreQueryUsers(QueryConditionsUsersReqVo usersReqVo);

    /**
     * 添加用户
     *
     * @param vo 要添加的用户对象
     * @return
     */
    R<String> AddUser(UserAddReqVo vo);


    /**
     * 更新用户信息
     * @param vo
     * @return
     */
    R<String> UpdateRloesOfUser(UserOwnRoleReqVo vo);

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    R<String> deleteUsers(List<String> userIds);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    R<SysUser> getUserInfoById(String id);

    /**
     * 根据用户Id进行更新
     * @param vo
     * @return
     */
    R<String> updateUserInfo(UserEditReqVO vo);


    R<UserOwnRoleRespVo> getRolesOfUserInfo(String userId);

    R<String> updatePasById(String id);

    R<String> updatePas(UpdatePasVo updatePasVo);

}
