package com.ys.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.annotation.Nullable;

/**
 * @author 杨森
 * @date 2023/7/12 9:10
 * @version 1.0
 */
@Data
@ApiModel(description = "条件查询VO")
public class QueryConditionsUsersReqVo {

    @ApiModelProperty(value = "当前页")
    private int pageNum;

    @ApiModelProperty(value = "每页大小")
    private int pageSize;

    @Nullable
    @ApiModelProperty(value = "真实名称")
    private String username;

    @Nullable
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @Nullable
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @Nullable
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
