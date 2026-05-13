package com.ys.stock.mapper;

import com.ys.stock.common.domain.StockCodeLenovoDomain;
import com.ys.stock.pojo.StockBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author YS
 * @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
 * @createDate 2023-06-15 18:06:09
 * @Entity com.ys.stock.pojo.StockBusiness
 */
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    List<StockBusiness> findAll();

    /*
     * 获取所有股票的code码
     * */
    List<String> getStockCode();

    List<StockCodeLenovoDomain> selectById(@Param("code") String searchStr);

    Map getDescribe(@Param("code") String code);


    List<Map> findAllName();

    String findId(@Param("name") String name);

    List<Map> findAllCode();

}
