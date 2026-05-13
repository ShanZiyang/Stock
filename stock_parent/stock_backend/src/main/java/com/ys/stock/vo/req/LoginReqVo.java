package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 杨森
 * @description: 登录请求vo
 * @date 2023年06月15日 21:55
 */
@Data
@ApiModel(description = "登录请求VO")
public class LoginReqVo {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "存入redis的key")
    private String rkey;
}
