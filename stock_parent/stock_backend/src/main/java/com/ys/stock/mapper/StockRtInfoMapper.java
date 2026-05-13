package com.ys.stock.mapper;

import com.ys.stock.common.domain.*;
import com.ys.stock.pojo.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author YS
 * @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
 * @createDate 2023-06-15 18:06:09
 * @Entity com.ys.stock.pojo.StockRtInfo
 */
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpdownDomain> stockIncreaseLimit(@Param("timePoint") Date timePoint);


    List<StockUpdownDomain> stockAll();

    List<Map> upDownCount(@Param("startTime") Date openTime, @Param("endTime") Date closeTime, @Param("flag") Integer flag);

    List<Map> getStockUpDown(@Param("timePoint") Date timePoint);

    List<Stock4MinuteDomain> stockMinute(@Param("code") String code, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Stock4EvrDayDomain> stockDkLine(@Param("code") String code, @Param("dates") List<Date> dates);

//    List<Stock4EvrDayDomain> stockDkLine(@Param("code") String code, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 获取指定日期范围内的收盘价格
     *
     * @param code      股票编码
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return
     */
    List<Date> getCloseDates(@Param("code") String code, @Param("startTime") Date startTime, @Param("endTime") Date endTime);


    String getClosePrice(@Param("code") String code, @Param("closeDateStr") String closeDateStr);

    String getOpenPrice(@Param("code") String code, @Param("openDate") String openDate4CloseStr);

    List<Stock4WeekDomain> getWkLine(@Param("stockCode") String stockCode, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    void insertBatch(@Param("stockInfos") List<StockRtInfo> infos);


    Map getDetail(@Param("code") String code, @Param("curDate") Date curDate);

    List<Map> selectStockTradingFlow(@Param("code") String code);


}
