package com.ys.stock.mapper;

import com.ys.stock.pojo.RefundInfo;
import org.apache.ibatis.annotations.Param;

/**
* @author YS
* @description 针对表【t_refund_info】的数据库操作Mapper
* @createDate 2023-08-22 12:32:45
* @Entity com.ys.stock.pojo.RefundInfo
*/
public interface RefundInfoMapper{


    int insert(RefundInfo refundInfo);

    void update(@Param("refundNo") String refundNo, @Param("content") String content, @Param("refundStatus") String refundStatus);

    RefundInfo select(@Param("orderNo") String orderNo);

}
