package com.ys.stock.service;

import com.ys.stock.pojo.RefundInfo;

/**
* @author YS
* @description 针对表【t_refund_info】的数据库操作Service
* @createDate 2023-08-22 12:32:45
*/
public interface RefundInfoService{

    RefundInfo createRefundByOrderNoForAliPay(String orderNo, String reason);

    void updateRefundForAliPay(String refundNo, String body, String type);

}
