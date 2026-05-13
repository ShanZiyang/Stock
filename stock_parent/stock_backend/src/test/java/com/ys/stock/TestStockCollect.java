package com.ys.stock;

import com.ys.stock.controller.OrderInfoController;
import com.ys.stock.controller.PreStockController;
import com.ys.stock.controller.ProductController;
import com.ys.stock.service.StockTimerTaskService;
import com.ys.stock.service.impl.OrderInfoServiceImpl;
import com.ys.stock.service.impl.RefundInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/4 18:02
 */
@SpringBootTest
public class TestStockCollect {
    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    @Test
    public void testCollectInner(){
        stockTimerTaskService.collertInnerMarketInfo();
    }

    @Test
    public void testCollectAStock(){
        stockTimerTaskService.collectAShareInfo();
    }

    @Test
    public void collectBlockInfo(){
        stockTimerTaskService.collectBlockInfo();
    }

    @Test
    public void collectOuterMarketInfo(){
        stockTimerTaskService.collectOuterMarketInfo();
    }

    @Test
    public void collectNews() throws IOException {
        stockTimerTaskService.collectNews();
    }
    @Test
    public void getCpu(){
        int cors = Runtime.getRuntime().availableProcessors();
        System.out.println(cors);
    }

    @Resource
    PreStockController preStockController;

    @Resource
    ProductController productController;
    @Test
    public void testttt(){
        productController.list();
    }

    @Resource
    OrderInfoController orderInfoController;

    @Resource
    RefundInfoServiceImpl refundInfoimpl;

    @Resource
    OrderInfoServiceImpl orderInfoServiceimpl;
    @Test
    public void testorderInfo(){
//        orderInfoController.list();
//        orderInfoController.queryOrderStatus("ORDER_20211214184634520");
//        refundInfoimpl.createRefundByOrderNoForAliPay("ORDER_20220410184105053","不想要了aaa");
        System.out.println(orderInfoServiceimpl.getOrderByOrderNo("ORDER_20230824164822008"));


    }

}
