package com.ys.stock.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/7 15:18
 * @description 定义公共精准匹配数据类
 * 股票流水 板块流水 大盘流水 分库的策略一致 可以公用该类
 */
public class CommonDbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {


    /*
     * @param dbNames 数据源集合
     * @param preciseShardingValue 分片键相关信息的封装
     * @return 具体的数据源名称
     * */
    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<Date> preciseShardingValue) {
        //获取分片的字段
        String columnName = preciseShardingValue.getColumnName();
        System.out.println(columnName);
        //获取逻辑表
        String logicTableName = preciseShardingValue.getLogicTableName();
        System.out.println(logicTableName);
        //获取分片值
        Date date = preciseShardingValue.getValue();
        //获取年份
        int year = new DateTime(date).getYear();
        //从数据源集合中查找以指定的年结尾的数据源即可
        String dsName = dbNames.stream().filter(
                dbName -> dbName.endsWith(year + "")).findFirst().get();
        return dsName;
    }
}
