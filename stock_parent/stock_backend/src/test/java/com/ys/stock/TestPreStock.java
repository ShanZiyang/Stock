package com.ys.stock;

import com.ys.stock.controller.PreStockController;
import com.ys.stock.job.StockJob;
import com.ys.stock.service.impl.PreStockServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/8/25 17:06
 */
@SpringBootTest
public class TestPreStock {
    @Resource
    PreStockController preStockController;

    @Resource
    PreStockServiceImpl preStockService;

    @Resource
    StockJob stockJob;

    @Test
    public void getList(){
//        stockJob.getAllData();
            stockJob.trainStock();
    }
}
