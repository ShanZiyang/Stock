package com.ys.stock.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/6 8:27
 * @description 国外大盘实体类
 */
@Data
@ApiModel(description = "外盘Domain")
public class OuterMarketDomain {

    @ApiModelProperty(value = "股票名称")
    private String name;

    @ApiModelProperty(value = "当前点")
    private BigDecimal curPoint;

    @ApiModelProperty(value = "当前价格")
    private BigDecimal curPrice;

    @ApiModelProperty(value = "涨跌幅")
    private BigDecimal upDownRate;

    @ApiModelProperty(value = "当前时间")
    private String curTime;

}
