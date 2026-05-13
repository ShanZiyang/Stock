package com.ys.stock.service.impl;

import com.google.gson.Gson;
import com.ys.stock.enums.PayType;
import com.ys.stock.mapper.PaymentInfoMapper;
import com.ys.stock.pojo.PaymentInfo;
import com.ys.stock.service.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author YS
* @description 针对表【t_payment_info】的数据库操作Service实现
* @createDate 2023-08-22 12:32:45
*/
@Service
@Slf4j
public class PaymentInfoServiceImpl implements PaymentInfoService{

    @Resource
    private PaymentInfoMapper paymentInfoMapper;

    /**
     * 记录支付日志：支付宝
     * @param params
     */
    @Override
    public void createPaymentInfoForAliPay(Map<String, String> params) {

        log.info("记录支付日志");

        //获取订单号
        String orderNo = params.get("out_trade_no");
        //业务编号
        String transactionId = params.get("trade_no");
        //交易状态
        String tradeStatus = params.get("trade_status");
        //交易金额
        String totalAmount = params.get("total_amount");
        int totalAmountInt = new BigDecimal(totalAmount).multiply(new BigDecimal("100")).intValue();


        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNo(orderNo);
        paymentInfo.setPaymentType(PayType.ALIPAY.getType());
        paymentInfo.setTransactionId(transactionId);
        paymentInfo.setTradeType("电脑网站支付");
        paymentInfo.setTradeState(tradeStatus);
        paymentInfo.setPayerTotal(totalAmountInt);
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setUpdateTime(new Date());

        Gson gson = new Gson();
        String json = gson.toJson(params, HashMap.class);
        paymentInfo.setContent(json);

        paymentInfoMapper.insert(paymentInfo);
    }
}
