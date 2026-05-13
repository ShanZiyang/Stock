package com.ys.stock.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/7 15:18
 * @description 定义公共精准匹配表的类
 * 股票流水 板块流水 大盘流水 分库的策略一致 可以公用该类
 */
public class CommonTbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {


    /*
     * @param tbNames 物理表名集合
     * @param preciseShardingValue 分片键相关信息的封装
     * @return 具体的数据源名称
     * */
    @Override
    public String doSharding(Collection<String> tbNames, PreciseShardingValue<Date> preciseShardingValue) {
        //对于板块表和大盘流水表仅仅做了分库处理，没有做分表处理，逻辑表和物理表一致
        //获取逻辑表
        String logicTableName = preciseShardingValue.getLogicTableName();
        return logicTableName;
    }
}
