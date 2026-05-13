package com.ys.stock.mapper;

import com.ys.stock.common.domain.InnerMarketDomain;
import com.ys.stock.common.domain.OuterMarketDomain;
import com.ys.stock.pojo.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author YS
 * @description 针对表【stock_market_index_info(股票大盘数据详情表)】的数据库操作Mapper
 * @createDate 2023-06-15 18:06:09
 * @Entity com.ys.stock.pojo.StockMarketIndexInfo
 */
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /*
     * 根据大盘的id和时间查询大盘信息
     * @author 杨森
     * @date 2023/6/24 10:12
     * @param marketIds 大盘id集合
     * @param timePoint 当前时间点（精确到分钟）
     * @return java.util.List<com.ys.stock.common.domain.InnerMarketDomain>
     */
    List<InnerMarketDomain> getMarketInfo(@Param("marketIds") List<String> marketIds, @Param("timePoint") Date timePoint);

    /**
     * 查询指定大盘下的指定日期下小于等于指定时间的数据，结果包含：每分钟内，整体大盘的交易量的统计
     *
     * @param marketIds 股票大盘的编码code集合
     * @param startTime 开盘时间
     * @param endTime   日期时间，精确到秒
     * @return
     */
    List<Map> getStockTradeVol(@Param("marketIds") List<String> marketIds, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * 批量保存大盘数据
     * @param arrayList 大盘数据集合
     * */
    void insertBatch(@Param("marketInfo") List marketInfo);


//    List getOuterMarket(@Param("lastDate") String lastDate);
    List<OuterMarketDomain> getOuterMarket(@Param("lastDate") Date lastDate);

}
