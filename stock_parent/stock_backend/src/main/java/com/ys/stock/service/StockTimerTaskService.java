package com.ys.stock.service;

import java.io.IOException;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/4 17:47
 * @description 定义采集股票信息的服务接口
 */
public interface StockTimerTaskService {

    /*
     * 获取国内大盘的实时数据信息
     * */
    void collertInnerMarketInfo();

    /*
     * 获取A股的信息
     * */
    void collectAShareInfo();

    /*
     * 采集板块信息
     * */
    void collectBlockInfo();

    /*
    * 采集国外大盘
    * */
    void collectOuterMarketInfo();

    //采集新闻
    void collectNews() throws IOException;
}
