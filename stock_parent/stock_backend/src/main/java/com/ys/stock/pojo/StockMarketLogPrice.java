package com.ys.stock.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票大盘 开盘价与前收盘价流水表
 * @TableName stock_market_log_price
 */
@Data
@ApiModel(description = "股票大盘实体类")
public class StockMarketLogPrice implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "大盘编码")
    private String marketCode;

    @ApiModelProperty(value = "当前日期")
    private Date curDate;

    @ApiModelProperty(value = "前收盘价格")
    private BigDecimal preClosePrice;

    @ApiModelProperty(value = "开盘价格")
    private BigDecimal openPrice;

    private static final long serialVersionUID = 1L;
}