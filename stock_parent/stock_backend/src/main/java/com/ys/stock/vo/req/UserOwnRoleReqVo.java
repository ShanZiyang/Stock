package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 杨森
 * @date 2023/7/12 9:11
 * @version 1.0
 */
@Data
@ApiModel(description = "用户拥有角色集合VO")
public class UserOwnRoleReqVo {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "角色id集合")
    private List<String> roleIds;
}
