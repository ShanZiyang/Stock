package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/14 14:21
 * @description 修改密码vo
 */
@ApiModel(description = "修改密码VO")
public class UpdatePasVo {
    @ApiModelProperty(value = "旧密码")
    public String oldPwd;

    @ApiModelProperty(value = "新密码")
    public String newPwd;
}
