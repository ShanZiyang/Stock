package com.ys.stock.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 个股详情信息表
 * @TableName stock_rt_info
 */
@ApiModel(description = "个股详情实体类",subTypes ={StockRtInfo.class})
@Data
@Builder
public class StockRtInfo implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "股票代码")
    private String stockCode;

    @ApiModelProperty(value = "当前时间")
    private Date curTime;

    @ApiModelProperty(value = "股票名称")
    private String stockName;

    @ApiModelProperty(value = "开盘价")
    private BigDecimal openPrice;

    @ApiModelProperty(value = "昨日收盘价")
    private BigDecimal preClosePrice;

    @ApiModelProperty(value = "当前价格")
    private BigDecimal curPrice;

    @ApiModelProperty(value = "今日最高价")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "今日最低价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "成交量")
    private Long tradeAmount;

    @ApiModelProperty(value = "成交总金额")
    private BigDecimal tradeVolume;

    private static final long serialVersionUID = 1L;
}