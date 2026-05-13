package com.ys.stock.vo.resp;

import com.ys.stock.pojo.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/20 9:01
 * @description 响应用户角色信息
 */
@Data
@ApiModel(description = "响应用户角色信息")
public class UserOwnRoleRespVo {

    @ApiModelProperty(value = "用户角色id集合")
    private List<String> ownRoleIds;

    @ApiModelProperty(value = "所有角色集合")
    private List<SysRole> allRole;
}
