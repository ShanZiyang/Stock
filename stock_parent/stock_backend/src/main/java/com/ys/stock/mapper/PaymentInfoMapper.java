package com.ys.stock.mapper;

import com.ys.stock.pojo.PaymentInfo;

/**
* @author YS
* @description 针对表【t_payment_info】的数据库操作Mapper
* @createDate 2023-08-22 12:32:45
* @Entity com.ys.stock.pojo.PaymentInfo
*/
public interface PaymentInfoMapper{


    void insert(PaymentInfo paymentInfo);

}
