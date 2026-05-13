package com.ys.stock.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票板块详情信息表
 * @TableName stock_block_rt_info
 */
@Data
@Builder
@ApiModel(description = "股票板块信息实体类")
public class StockBlockRtInfo implements Serializable {

    @ApiModelProperty(value = "板块主键ID")
    private String id;

    @ApiModelProperty(value = "板块类型")
    private String label;

    @ApiModelProperty(value = "板块名称")
    private String blockName;

    @ApiModelProperty(value = "公司数量")
    private Integer companyNum;


    @ApiModelProperty(value = "平均价格")
    private BigDecimal avgPrice;


    @ApiModelProperty(value = "涨跌幅")
    private BigDecimal updownRate;

    @ApiModelProperty(value = "交易量")
    private Long tradeAmount;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal tradeVolume;

    @ApiModelProperty(value = "当前日期")
    private Date curTime;

    private static final long serialVersionUID = 1L;
}