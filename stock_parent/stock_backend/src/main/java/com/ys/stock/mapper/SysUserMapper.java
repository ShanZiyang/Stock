package com.ys.stock.mapper;

import com.ys.stock.pojo.SysUser;
import com.ys.stock.vo.resp.Rows;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author YS
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-06-15 18:06:09
* @Entity com.ys.stock.pojo.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SysUser findByUserName(@Param("username") String username);

    /**
     * 用户管理多条件查询
     *
     * @param username  用户名
     * @param nickName  用户昵称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<Rows> MoreQueryUsers(@Param("username") String username, @Param("nickName") String nickName, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 无条件查询所有用户
     *
     * @return
     */
    List<Rows> MoreQueryAll();
    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int insertUser( SysUser user);

    /**
     * 批量删除用户，通过修改status和deleted来确定
     * @param userIds
     * @return
     */
    int updateUserStatus4Deleted(@Param("userIds") List<String> userIds);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    SysUser getUserInfoById(@Param("id") String id);


    int updatePasById(@Param("id") String id, @Param("pwd") String pwd);

    int updatePas(@Param("name") String currentUserName, @Param("newPwd") String newPwd);

    SysUser findUserByUserName(@Param("userName") String username);

    String selectByName(@Param("currentUserName") String currentUserName);


    int userExists(@Param("username") String username);

    String getUserId(@Param("userName") String userName);

    int updateAvatar(@Param("resKey") String resKey, @Param("userid") String userid);

}
