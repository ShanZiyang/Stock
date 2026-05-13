package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 9:12
 */
@Data
@ApiModel(description = "修改用户VO")
public class UserEditReqVO {
    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "账户名称")
    private String  username;

    @ApiModelProperty(value = "电话号码")
    private String  phone;

    @ApiModelProperty(value = "邮箱")
    private String  email;

    @ApiModelProperty(value = "昵称")
    private String  nickName;

    @ApiModelProperty(value = "真实姓名")
    private String  realName;

    @ApiModelProperty(value = "性别")
    private Integer  sex;

    @ApiModelProperty(value = "创建来源")
    private Integer  createWhere;

    @ApiModelProperty(value = "账户状态")
    private Integer  status;
}
