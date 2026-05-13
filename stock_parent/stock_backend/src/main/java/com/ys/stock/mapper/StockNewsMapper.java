package com.ys.stock.mapper;

import com.ys.stock.pojo.StockNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockNewsMapper {

     void insertBatch(@Param("StockNews") List<StockNews> newsList);


    List<StockNews> selectAll();

}
