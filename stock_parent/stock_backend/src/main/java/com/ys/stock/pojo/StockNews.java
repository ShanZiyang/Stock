package com.ys.stock.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/26 21:04
 */
@Data
@Builder
@ApiModel(description = "股票新闻实体类")
public class StockNews {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "新闻目标网址")
    private String url;

    @ApiModelProperty(value = "新闻详情")
    private String info;

    @ApiModelProperty(value = "新闻时间")
//    private Date time;
    private String time;


}
