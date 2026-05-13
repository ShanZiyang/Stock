package com.ys.stock.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/12 16:35
 * @description 个股分时详情数据domain
 */
@Data
@ApiModel(description = "个股分时详情Domain")
public class StockDetailDomain {

    @ApiModelProperty(value = "最新交易量")
    private Long tradeAmt;

    @ApiModelProperty(value = "前收盘价格")
    private BigDecimal preClosePrice;

    @ApiModelProperty(value = "最低价")
    private BigDecimal lowPrice;

    @ApiModelProperty(value = "最高价")
    private BigDecimal highPrice;

    @ApiModelProperty(value = "开盘价")
    private BigDecimal openPrice;

    @ApiModelProperty(value = "交易金额")
    private Long tradeVol;

    @ApiModelProperty(value = "当前价格")
    private BigDecimal tradePrice;

    @JsonFormat(pattern = "yyyyMMddHH")
    @ApiModelProperty(value = "当前时间")
    private Date curDate;

}
