package com.ys.stock.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ys.stock.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 * @TableName sys_log
 */
@Data
@ApiModel(description = "系统日志实体类")
public class SysLog implements Serializable {

    @Excel(name = "操作序号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "操作序号")
    private Long operId;

    @Excel(name = "操作模块")
    @ApiModelProperty(value = "操作模块")
    private String title;

    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;


    @ApiModelProperty(value = "业务类型数组")
    private Integer[] businessTypes;

    @Excel(name = "请求方法")
    @ApiModelProperty(value = "请求方法")
    private String method;

    @Excel(name = "请求方式")
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    @Excel(name = "操作人员")
    @ApiModelProperty(value = "操作人员")
    private String operName;

    @Excel(name = "请求地址")
    @ApiModelProperty(value = "请求地址")
    private String operUrl;

    @Excel(name = "操作地址")
    @ApiModelProperty(value = "操作地址")
    private String operIp;

    @Excel(name = "操作地点")
    @ApiModelProperty(value = "操作地点")
    private String operLocation;

    @Excel(name = "请求参数")
    @ApiModelProperty(value = "请求参数")
    private String operParam;

    @Excel(name = "返回参数")
    @ApiModelProperty(value = "返回参数")
    private String jsonResult;

    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private Integer status;

    @Excel(name = "错误消息")
    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date operTime;

    @Excel(name = "消耗时间", suffix = "毫秒")
    @ApiModelProperty(value = "消耗时间")
    private Long costTime;


    private static final long serialVersionUID = 1L;
}