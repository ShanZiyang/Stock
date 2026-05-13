package com.ys.stock.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/2 19:11
 * @Description 个股分时数据封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "个股分时数据Domain")
public class Stock4MinuteDomain {

    @ApiModelProperty(value = "股票名称")
    private String name;

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "交易量")
    private Long tradeAmt;

    @ApiModelProperty(value = "股票编码")
    private String code;

    @ApiModelProperty(value = "最低价")
    private BigDecimal lowPrice;

    @ApiModelProperty(value = "前收盘价")
    private BigDecimal preClosePrice;

    @ApiModelProperty(value = "最高价")
    private BigDecimal highPrice;

    @ApiModelProperty(value = "开盘价")
    private BigDecimal openPrice;

    @ApiModelProperty(value = "当前交易总金额")
    private BigDecimal tradeVol;

    @ApiModelProperty(value = "当前价格")
    private BigDecimal tradePrice;
}
