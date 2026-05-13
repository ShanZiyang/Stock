package com.ys.stock.common.domain;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * @Description 导出股票信息的实体类对象
 * @date 2023/6/25 10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "导出股票信息Domain")
public class StockExcelDomain {
	
    @ExcelProperty(value = {"股票涨幅信息统计表","股票编码"},index = 0)
    @ApiModelProperty(value = "股票编码")
    private String code;

    @ExcelProperty(value = {"股票涨幅信息统计表","股票名称"},index = 1)
    @ApiModelProperty(value = "股票名称")
    private String name;

    @ExcelProperty(value = {"股票涨幅信息统计表","前收盘价格"},index = 2)
    @ApiModelProperty(value = "前收盘价")
    private BigDecimal preClosePrice;

    @ExcelProperty(value = {"股票涨幅信息统计表","当前价格"},index= 3)
    @ApiModelProperty(value = "当前价格")
    private BigDecimal tradePrice;

    @ExcelProperty(value = {"股票涨幅信息统计表","涨跌"},index= 4)
    @ApiModelProperty(value = "涨跌")
    private BigDecimal increase;

    @ExcelProperty(value = {"股票涨幅信息统计表","涨幅"},index= 5)
    @ApiModelProperty(value = "涨幅")
    private BigDecimal upDown;

    @ExcelProperty(value = {"股票涨幅信息统计表","振幅"},index= 6)
    @ApiModelProperty(value = "振幅")
    private BigDecimal amplitude;

    @ExcelProperty(value = {"股票涨幅信息统计表","交易总量"},index = 7)
    @ApiModelProperty(value = "总交易量")
    private Long tradeAmt;

    @ExcelProperty(value = {"股票涨幅信息统计表","交易总金额"},index = 8)
    @ApiModelProperty(value = "交易总金额")
    private BigDecimal tradeVol;

    @ExcelProperty(value = {"股票涨幅信息统计表","日期"},index = 9)
    @ApiModelProperty(value = "日期")
    private String curDate;

}
