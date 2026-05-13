package com.ys.stock.mapper;


import com.ys.stock.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

/**
* @author YS
* @description 针对表【t_order_info】的数据库操作Mapper
* @createDate 2023-08-22 12:32:45
* @Entity com.ys.stock.pojo.OrderInfo
*/
public interface OrderInfoMapper{


    List<OrderInfo> listOrderByCreateTimeDesc();

    String getOrderStatus(@Param("orderNo") String orderNo);

    OrderInfo getOrderByOrderNo(@Param("orderNo") String orderNo);

    OrderInfo getNoPayOrderByProductId(@Param("productId") Long productId, @Param("paymentType") String paymentType, @Param("orderStatus") String orderStatus);

    int insert(OrderInfo orderInfo);

    void updateStatus(@Param("orderNo") String orderNo, @Param("status") String status);

    List<OrderInfo> getOrderList(@Param("userid") String userid);


    List<OrderInfo> selectList(@Param("order_status") String order_status, @Param("instant") Instant instant, @Param("paymentType") String paymentType);

}
