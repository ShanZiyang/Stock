package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 9:19
 */
@Data
@ApiModel(description = "新增角色权限")
public class AddRoleAndPermission {
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色描述")
    private String description;
    @ApiModelProperty(value = "权限集合")
    private List<String> permissionsIds;
}
