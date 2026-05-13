package com.ys.stock.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/7 21:02
 * @description 定义范围查询命中股票流水表的策略
 */
public class StockRtInfoTableRangeHandler implements RangeShardingAlgorithm<Date> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        Range<Date> valueRange = shardingValue.getValueRange();
        Collection<String> filters=new HashSet<>();
        if (valueRange.hasLowerBound()) {
            Date startTime = valueRange.lowerEndpoint();
            String startStr = new DateTime(startTime).toString("yyyyMM");
            Integer intDate = Integer.valueOf(startStr);
            //过滤获取大于指定日期的值
            availableTargetNames = availableTargetNames
                    .stream()
                    .filter(tbName ->
                            Integer.valueOf(tbName.substring(tbName.lastIndexOf("_")+1))>=intDate
            ).collect(Collectors.toList());
        }

        if (valueRange.hasUpperBound()) {
            Date endTime = valueRange.upperEndpoint();
            String endStr = new DateTime(endTime).toString("yyyyMM");
            Integer intDate = Integer.valueOf(endStr);
            //过滤获取大于指定日期的值
            availableTargetNames = availableTargetNames
                    .stream()
                    .filter(tbName ->
                            Integer.valueOf(tbName.substring(tbName.lastIndexOf("_")+1))<=intDate
                    ).collect(Collectors.toList());
        }
        return availableTargetNames;
    }
}
