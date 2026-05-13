package com.ys.stock.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/15 10:30
 */
@Data
@ApiModel(description = "日志每行封装VO")
public class LogRows {

    @ApiModelProperty(value = "用户id")
    private String operId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户操作")
    private String operation;

    @ApiModelProperty(value = "响应时间")
    private Integer time;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
