package com.ys.stock.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.ys.stock.service.PreStockService;
import com.ys.stock.service.StockService;
import com.ys.stock.service.StockTimerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/5 14:59
 * @description 定义配置xxljob执行任务的bean对象
 */
@Component
public class StockJob {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    @Resource
    private PreStockService preStockService;

    @Resource
    private StockService stockService;

    /*
     * 测试用例
     * */
    @XxlJob("testXxlJob")
    public void testXxlJob() {
        System.out.println("testXxlJob is runing....");
    }

    /*
     * 采集大盘数据
     * */
    @XxlJob("getInnerMarketInfos")
    public void getInnerMarketInfos() {
        stockTimerTaskService.collertInnerMarketInfo();
    }

    /*
     * 采集A股数据
     * */
    @XxlJob("getAShareInfo")
    public void getAShareInfo() {
        stockTimerTaskService.collectAShareInfo();
    }

    /*
     * 采集板块数据
     * */
    @XxlJob("getBlockInfo")
    public void getBlockInfo() {
        stockTimerTaskService.collectBlockInfo();
    }

    /*
     * 采集板块数据
     * */
    @XxlJob("getOuterMarketInfo")
    public void getOuterMarketInfo() {
        stockTimerTaskService.collectOuterMarketInfo();
    }

    /*
    * 采集股票数据
    * **/
    @XxlJob("getAllData")
    public void getAllData() {
        preStockService.getAllData();
    }


    /*
     * 采集股票新闻
     * **/
    @XxlJob("getStockNews")
    public void getStockNews() throws IOException {
        stockTimerTaskService.collectNews();
    }


    /*
     * 训练模型
     * **/
    @XxlJob("trainStock")
    public void trainStock(){
        preStockService.trainStock();
    }


}
