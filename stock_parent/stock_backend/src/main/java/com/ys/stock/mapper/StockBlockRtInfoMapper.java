package com.ys.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ys.stock.common.domain.StockBlockDomain;
import com.ys.stock.pojo.StockBlockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author YS
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2023-06-15 18:06:09
* @Entity com.ys.stock.pojo.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper{

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockBlockDomain> sectorAll(@Param("timePoint") Date timePoint);

    void insertBatch(@Param("list") List<StockBlockRtInfo> list);

}
