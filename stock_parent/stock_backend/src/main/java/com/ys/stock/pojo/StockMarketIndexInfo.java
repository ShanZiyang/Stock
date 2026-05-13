package com.ys.stock.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票大盘数据详情表
 * @TableName stock_market_index_info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "股票大盘数据详情实体类")
public class StockMarketIndexInfo implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;


    @ApiModelProperty(value = "大盘ID")
    private String markId;

    @ApiModelProperty(value = "当前时间")
    private Date curTime;

    @ApiModelProperty(value = "指数名称")
    private String markName;

    @ApiModelProperty(value = "当前点数")
    private BigDecimal curPoint;

    @ApiModelProperty(value = "当前价格")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "涨跌率")
    private BigDecimal updownRate;

    @ApiModelProperty(value = "成交量")
    private Long tradeAccount;

    @ApiModelProperty(value = "成交额")
    private Long tradeVolume;

    private static final long serialVersionUID = 1L;
}