package com.ys.stock.service.impl;

import com.ys.stock.exception.BusinessException;
import com.ys.stock.mapper.RefundInfoMapper;
import com.ys.stock.pojo.OrderInfo;
import com.ys.stock.pojo.RefundInfo;
import com.ys.stock.service.OrderInfoService;
import com.ys.stock.service.RefundInfoService;
import com.ys.stock.utils.OrderNoUtils;
import com.ys.stock.vo.resp.ResponseCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author YS
* @description 针对表【t_refund_info】的数据库操作Service实现
* @createDate 2023-08-22 12:32:45
*/
@Service
public class RefundInfoServiceImpl implements RefundInfoService{

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private RefundInfoMapper refundInfoMapper;


    /**
     * 根据订单号创建退款订单
     * @param orderNo
     * @return
     */
    @Override
    public RefundInfo createRefundByOrderNoForAliPay(String orderNo, String reason) {

        //根据订单号获取订单信息
        OrderInfo orderInfo = orderInfoService.getOrderByOrderNo(orderNo);

        //根据订单号生成退款订单
        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setOrderNo(orderNo);//订单编号
        refundInfo.setRefundNo(OrderNoUtils.getRefundNo());//退款单编号

        refundInfo.setTotalFee(orderInfo.getTotalFee());//原订单金额(分)
        refundInfo.setRefund(orderInfo.getTotalFee());//退款金额(分)
        refundInfo.setReason(reason);//退款原因
        refundInfo.setCreateTime(new Date());
        refundInfo.setUpdateTime(new Date());

        //保存退款订单
//        baseMapper.insert(refundInfo);
        int count = refundInfoMapper.insert(refundInfo);
        System.out.println(count);
        if (count==0){
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return refundInfoMapper.select(orderNo);
    }

    /**
     * 更新退款记录
     * @param refundNo
     * @param content
     * @param refundStatus
     */
    @Override
    public void updateRefundForAliPay(String refundNo, String content, String refundStatus) {

        //根据退款单编号修改退款单
        //设置要修改的字段
        // 退款状态
        // 将全部响应结果存入数据库的content字段
        //更新退款单
        System.out.println("contest:"+content);
        refundInfoMapper.update(refundNo,content,refundStatus);

    }

}
