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
 * @date 2023/7/4 11:43
 * @description 周K线数据封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "周K线Domain")
public class Stock4WeekDomain {
    @ApiModelProperty(value = "股票名称")
    private String name;

    @ApiModelProperty(value = "一周内平均价")
    private BigDecimal avgPrice;

    @ApiModelProperty(value = "一周内最低价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "一周开盘价")
    private BigDecimal openPrice;

    @ApiModelProperty(value = "一周内最高价")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "周五收盘价")
    private BigDecimal closePrice;

    @ApiModelProperty(value = "一周内最大时间")
    private String maxTime;

    @ApiModelProperty(value = "股票编码")
    private String stockCode;
}
