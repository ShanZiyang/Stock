package com.ys.stock.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/11 8:37
 * @description 股票联想domain
 */
@Data
@ApiModel(description = "股票联想Domain")
public class StockCodeLenovoDomain {

    @ApiModelProperty(value = "股票编码")
    String code;

    @ApiModelProperty(value = "股票名称")
    String name;

}
