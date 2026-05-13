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
 * @description: 股票涨跌domain
 * @date 2023年06月24日 15:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "股票涨跌Domain")
public class StockUpdownDomain {

    @ApiModelProperty(value = "交易量")
    private Long tradeAmt;

    @ApiModelProperty(value = "前收盘价")
    private BigDecimal preClosePrice;

    @ApiModelProperty(value = "振幅")
    private BigDecimal amplitude;

    @ApiModelProperty(value = "股票编码")
    private String code;

    @ApiModelProperty(value = "股票名称")
    private String name;

    @ApiModelProperty(value = "当前日期")
    private String curDate;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal tradeVol;

    @ApiModelProperty(value = "涨跌")
    private BigDecimal increase;

    @ApiModelProperty(value = "涨幅")
    private BigDecimal upDown;

    @ApiModelProperty(value = "当前价格")
    private BigDecimal tradePrice;
}
