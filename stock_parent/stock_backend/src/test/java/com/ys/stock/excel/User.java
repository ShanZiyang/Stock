package com.ys.stock.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 杨森
 * @description:
 * @date 2023年06月25日 9:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//通过注解自定义表头名称
// 注解添加排序规则：index越大 越靠右
@ColumnWidth(value = 50)//列宽
public class User implements Serializable {

//    @ExcelProperty(value = {"用户名"}, index = 1)
    @ExcelProperty(value = {"用户基本信息","用户名"}, index = 1) //增加表头
    private String userName;

//    @ExcelProperty(value = {"年龄"}, index = 2)
    @ExcelProperty(value = {"用户基本信息","年龄"}, index = 2) //增加表头
//    @ExcelIgnore//忽略这个字段不输出
    private Integer age;

//    @ExcelProperty(value = {"地址"}, index = 4)
    @ExcelProperty(value = {"用户基本信息","地址"}, index = 4) //增加表头

    private String address;

//    @ExcelProperty(value = {"出生年月"}, index = 3)
    @ExcelProperty(value = {"用户基本信息","出生年月"}, index = 3) //增加表头
    @DateTimeFormat("yyyy/MM/dd")
    private Date birthday;

}
