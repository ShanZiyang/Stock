package com.ys.stock.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/7 21:01
 * @description 定义stock_rt_inf 表的精准匹配逻辑
 */
public class StockRtInfoTablePreciseHandler implements PreciseShardingAlgorithm<Date> {

    /**
     * m-$->{2021..2022}.stock_rt_info_$->{1..12}
     * @param availableTargetNames 当前逻辑表对应的所有的表名称集合： stock_rt_inf_1~stock_rt_inf_12
     * @param shardingValue 封装了cur_time片键相关的信息
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        //获取片键的值
        Date curTime = shardingValue.getValue();
        //获取年月日字符串
        String curStr = new DateTime(curTime).toString("yyyyMM");
        //如期对应的表
        String curTableName = availableTargetNames.stream().filter(tbName -> tbName.endsWith(curStr)).findFirst().get();
        return curTableName;
    }
}
