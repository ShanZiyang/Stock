package com.ys.stock.service;

import java.util.Map;

/**
* @author YS
* @description 针对表【t_payment_info】的数据库操作Service
* @createDate 2023-08-22 12:32:45
*/
public interface PaymentInfoService{

    void createPaymentInfoForAliPay(Map<String, String> params);

}
