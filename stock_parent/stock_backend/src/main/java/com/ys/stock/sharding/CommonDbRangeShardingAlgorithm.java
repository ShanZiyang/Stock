package com.ys.stock.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/7 15:27
 * @description 定义公共数据库范围查询的策略类
 */
public class CommonDbRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        //获取逻辑表
        String logicTableName = rangeShardingValue.getLogicTableName();
        //获取片键
        String columnName = rangeShardingValue.getColumnName();
        //获取封装上限和下限的对象
        Range<Date> valueRange = rangeShardingValue.getValueRange();

        if (valueRange.hasLowerBound()) {
            //获取下限 2020
            Date start = valueRange.lowerEndpoint();
            //获取年份
            int startYear = new DateTime(start).getYear();
            //获取出数据库名称后缀的值大于或者等于起始年的数据库名称集合
            availableTargetNames = availableTargetNames
                    .stream()
                    .filter(dbName -> Integer.valueOf(dbName.substring(dbName.lastIndexOf("-") + 1)) >= startYear)
                    .collect(Collectors.toList());
        }


        if (valueRange.hasUpperBound()) {
            //获取上限 2024
            Date end = valueRange.upperEndpoint();
            //获取年份
            int endYear = new DateTime(end).getYear();
            //获取出数据库名称后缀的值小于或者等于起始年的数据库名称集合
            availableTargetNames = availableTargetNames
                    .stream()
                    .filter(dbName -> Integer.valueOf(dbName.substring(dbName.lastIndexOf("-") + 1)) <= endYear)
                    .collect(Collectors.toList());
        }

        return availableTargetNames;
    }
}

