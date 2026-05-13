package com.ys.stock.service.impl;

import com.ys.stock.enums.OrderStatus;
import com.ys.stock.mapper.OrderInfoMapper;
import com.ys.stock.mapper.ProductMapper;
import com.ys.stock.mapper.SysUserMapper;
import com.ys.stock.pojo.OrderInfo;
import com.ys.stock.pojo.Product;
import com.ys.stock.service.OrderInfoService;
import com.ys.stock.utils.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author YS
 * @description 针对表【t_order_info】的数据库操作Service实现
 * @createDate 2023-08-22 12:32:45
 */
@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    //查询订单列表，并倒序查询
    @Override
    public List<OrderInfo> listOrderByCreateTimeDesc() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userid = sysUserMapper.getUserId(username);
        if (username.startsWith("admin")){
            return orderInfoMapper.listOrderByCreateTimeDesc();
        }

        return orderInfoMapper.getOrderList(userid);
    }

    //根据订单号获取订单状态
    @Override
    public String getOrderStatus(String orderNo) {
        return orderInfoMapper.getOrderStatus(orderNo);
    }


    /**
     * 根据订单号获取订单
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getOrderByOrderNo(String orderNo) {

        return orderInfoMapper.getOrderByOrderNo(orderNo);
    }

    @Override
    public OrderInfo  createOrderByProductId(Long productId, String paymentType) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userId = sysUserMapper.getUserId(userName);
        //查找已存在但未支付的订单
        OrderInfo orderInfo = this.getNoPayOrderByProductId(productId, paymentType);
        if( orderInfo != null){
            return orderInfo;
        }

        //获取商品信息
        Product product = productMapper.selectById(productId);

        //生成订单
        orderInfo = new OrderInfo();
        orderInfo.setTitle(product.getTitle());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo()); //订单号
        orderInfo.setUserId(Long.valueOf(userId));
        orderInfo.setProductId(productId);
        orderInfo.setTotalFee(product.getPrice()); //分
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType()); //未支付
        orderInfo.setPaymentType(paymentType);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfoMapper.insert(orderInfo);

        return orderInfo;
    }


    /**
     * 根据订单号更新订单状态
     * @param orderNo
     * @param orderStatus
     */
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {

        log.info("更新订单状态 ===> {}", orderStatus.getType());

        String status = orderStatus.getType();
        System.out.println("status:"+status);

        orderInfoMapper.updateStatus(orderNo,status);
//        if (values)
//        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     * @param minutes
     * @return
     */
    @Override
    public List<OrderInfo> getNoPayOrderByDuration(int minutes, String paymentType) {

        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));
        String order_status = OrderStatus.NOTPAY.getType();


        List<OrderInfo> orderInfoList = orderInfoMapper.selectList(order_status,instant,paymentType);

        return orderInfoList;
    }

    /**
     * 根据商品id查询未支付订单
     * 防止重复创建订单对象
     * @param productId
     * @return
     */
    private OrderInfo getNoPayOrderByProductId(Long productId, String paymentType) {

        String orderStatus = OrderStatus.NOTPAY.getType();
        return orderInfoMapper.getNoPayOrderByProductId(productId,paymentType,orderStatus);
    }
}
