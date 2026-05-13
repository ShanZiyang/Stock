package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/13 22:11
 */
@Data
@ApiModel(description = "更新角色VO")
public class UpdateRoleVo {
    @ApiModelProperty(value = "角色id")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "权限集合")
    List<String> permissionsIds;
}
