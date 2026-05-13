package com.ys.stock.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 *
 * @author 杨森
 * @date 2023/6/24 9:18
 * @Description 股票板块domain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "股票板块Domain")
public class StockBlockDomain {

    @ApiModelProperty(value = "公司数量")
    private Integer companyNum;

    @ApiModelProperty(value = "交易量")
    private Long tradeAmt;

    @ApiModelProperty(value = "板块编码")
    private String code;

    @ApiModelProperty(value = "平均价")
    private BigDecimal avgPrice;

    @ApiModelProperty(value = "板块名称")
    private String name;

    @ApiModelProperty(value = "当前日期")
    private String curDate;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal tradeVol;

    @ApiModelProperty(value = "涨跌幅")
    private String updownRate;
}
