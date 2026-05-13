package com.ys.stock.pojo;

import com.ys.stock.vo.resp.PermissionRespNodeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

/**
 * 用户表
 * @TableName sys_user
 */
@Data
@ApiModel(description = "用户实体类")
public class SysUser implements UserDetails {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "用户密码密文")
    private String password;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "真实名称")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账户状态")
    private Integer status;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String updateId;

    @ApiModelProperty(value = "创建来源(1.web 2.android 3.ios )")
    private Integer createWhere;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限集合")
    private List<GrantedAuthority> authorities;

    @ApiModelProperty(value = "账户未过期")
    private boolean isAccountNonExpired=true;

    @ApiModelProperty(value = "账户未锁定")
    private boolean isAccountNonLocked=true;


    @ApiModelProperty(value = "密码未过期")
    private boolean isCredentialsNonExpired=true;

    @ApiModelProperty(value = "不禁用")
    private boolean isEnabled=true;

    @ApiModelProperty(value = "权限菜单集合")
    private List<PermissionRespNodeVo> menus;

    @ApiModelProperty(value = "前端按钮权限")
    private List<String> permissions;


}