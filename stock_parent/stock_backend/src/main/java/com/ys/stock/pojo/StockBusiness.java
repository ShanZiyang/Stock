package com.ys.stock.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 主营业务表
 * @TableName stock_business
 */
@Data
@ApiModel(description = "主营业务实体类")
public class StockBusiness implements Serializable {

    @ApiModelProperty(value = "股票编码")
    private String secCode;

    @ApiModelProperty(value = "股票名称")
    private String secName;

    @ApiModelProperty(value = "行业板块代码")
    private String sectorCode;

    @ApiModelProperty(value = "行业板块名称")
    private String sectorName;

    @ApiModelProperty(value = "主营业务")
    private String business;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}