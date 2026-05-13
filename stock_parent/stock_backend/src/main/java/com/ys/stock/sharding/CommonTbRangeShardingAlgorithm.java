package com.ys.stock.sharding;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/7 15:27
 * @description 定义公共表范围查询的策略类
 */
public class CommonTbRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {
    @Override
    public Collection<String> doSharding(Collection<String> tbNames, RangeShardingValue<Date> rangeShardingValue) {
        return tbNames;
    }
}

