package com.ys.stock.service;

import com.ys.stock.enums.OrderStatus;
import com.ys.stock.pojo.OrderInfo;

import java.util.List;

/**
* @author YS
* @description 针对表【t_order_info】的数据库操作Service
* @createDate 2023-08-22 12:32:45
*/
public interface OrderInfoService{

    List<OrderInfo> listOrderByCreateTimeDesc();

    String getOrderStatus(String orderNo);

    OrderInfo getOrderByOrderNo(String outTradeNo);

    OrderInfo createOrderByProductId(Long productId, String type);

    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    List<OrderInfo> getNoPayOrderByDuration(int minutes, String paymentType);

}
