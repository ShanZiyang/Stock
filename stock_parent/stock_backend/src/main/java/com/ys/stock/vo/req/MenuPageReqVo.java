package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 9:15
 */
@Data
@ApiModel(description = "菜单分页VO")
public class MenuPageReqVo {

    @ApiModelProperty(value = "当前页")
    private Integer pageNum=1;

    @ApiModelProperty(value = "每页大小")
    private Integer pageSize=10;
}