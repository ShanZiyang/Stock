package com.ys.stock.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @author 杨森
 * @date 2023/6/24 9:17
 * @Description 定义封装国内大盘数据的实体类
 */
@Data
@ApiModel(description = "国内大盘数据Domain")
public class InnerMarketDomain {

    @ApiModelProperty(value = "成交量")
    private Long tradeAmt;

    @ApiModelProperty(value = "前收盘价")
    private BigDecimal preClosePrice;

    @ApiModelProperty(value = "股票编码")
    private String code;

    @ApiModelProperty(value = "股票名称")
    private String name;

    @ApiModelProperty(value = "当前时间")
    private String curDate;

    @ApiModelProperty(value = "开盘价")
    private BigDecimal openPrice;

    @ApiModelProperty(value = "成交总金额")
    private Long tradeVol;

    @ApiModelProperty(value = "涨跌幅")
    private BigDecimal upDown;

    @ApiModelProperty(value = "当前价")
    private BigDecimal tradePrice;
}
