package com.ys.stock.service;

import com.ys.stock.common.domain.*;
import com.ys.stock.pojo.StockBusiness;
import com.ys.stock.pojo.StockNews;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.R;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 杨森
 * @description: 股票相关服务接口
 * @date 2023年06月15日 18:10
 */
public interface StockService{


    List<StockBusiness> findAll();

    R<List<InnerMarketDomain>> getNewAMarketInfo();

    R<List<StockBlockDomain>> sectorAll();

    R<List<StockUpdownDomain>> stockIncrease();

    R<PageResult<StockUpdownDomain>> getStockPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    R<Map> upDownCount();

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);

    R<Map> stockTradeCmp();

    R<Map> getStockUpDown();

    R<List<Stock4MinuteDomain>> getStockMinute(String code);

    R<List<Stock4EvrDayDomain>> getStockDkLine(String code);

    R<List<Stock4WeekDomain>> getStockWkLine(String code);

    R<List<OuterMarketDomain>> getOutMarketInfo();

    R<List<StockCodeLenovoDomain>> findById(String searchStr);

    R<Map> getDescribe(String code);

    R<Map> getDetail(String code);

    R<List<Map>> getSecond(String code);

    R<List<StockNews>> getStockNews();

}
