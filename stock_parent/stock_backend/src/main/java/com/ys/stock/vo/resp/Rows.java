package com.ys.stock.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 9:25
 */
@Data
@ApiModel(description = "分页每行集合")
public class Rows {
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

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String updateId;

    @ApiModelProperty(value = "创建来源")
    private Integer createWhere;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String createUserName;

    @ApiModelProperty(value = "更新人")
    private String updateUserName;

    private static final long serialVersionUID = 1L;
}
