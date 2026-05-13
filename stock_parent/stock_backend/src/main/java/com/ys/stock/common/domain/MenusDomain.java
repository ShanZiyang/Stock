package com.ys.stock.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/13 10:34
 * @description 菜单domain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜单Domain")
public class MenusDomain {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "菜单权限名称")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "访问地址")
    private String path;

    @ApiModelProperty(value = "name与路由name一致")
    private String name;

    @ApiModelProperty(value = "父级菜单权限id")
    private String pid;

    @ApiModelProperty(value = "权限")
    private String permissions;

    @ApiModelProperty(value = "子菜单")
    private List<MenusDomain> children;
}
