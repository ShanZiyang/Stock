package com.ys.stock.controller;

import com.ys.stock.common.domain.*;
import com.ys.stock.pojo.StockBusiness;
import com.ys.stock.pojo.StockNews;
import com.ys.stock.service.StockService;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 杨森
 * @description:
 * @date 2023年06月15日 18:12
 */
@RestController
@Api(tags = "股票控制")
@RequestMapping("/api/quot")
public class StockController {
    @Autowired
    private StockService stockService;


    @ApiOperation("查询所有的主营业务信息")
    @GetMapping("/stock/business/all")
    public List<StockBusiness> findAllBusinessInfo() {
        return stockService.findAll();
    }


    @ApiOperation("获取最新的A股大盘信息")
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getNewAMarketInfo() {
        return stockService.getNewAMarketInfo();
    }


    @ApiOperation("沪深两市板块分时行情数据查询")
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll() {
        return stockService.sectorAll();
    }

    @ApiOperation("沪深板块涨幅数据查询")
    @GetMapping("/stock/increase")
    public R<List<StockUpdownDomain>> stockIncrease() {
        return stockService.stockIncrease();
    }


    @ApiOperation("沪深个股行情列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")
    })
    @GetMapping("/stock/all")
    public R<PageResult<StockUpdownDomain>> stockPage(Integer page, Integer pageSize) {
        return stockService.getStockPage(page, pageSize);
    }

    @ApiOperation("某天的涨跌停数")
    @GetMapping("/stock/updown/count")
    public R<Map> upDownCount() {
        return stockService.upDownCount();
    }


    @ApiOperation("Excel导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "response", value = "请求体"),
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")
    })
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        stockService.stockExport(response, page, pageSize);
    }


    @ApiOperation("成交量对比")
    @GetMapping("/stock/tradevol")
    public R<Map> stockTradeCmp() {
        return stockService.stockTradeCmp();
    }

    @ApiOperation("涨跌幅区间")
    @GetMapping("/stock/updown")
    public R<Map> stockUpDowm() {
        return stockService.getStockUpDown();
    }

    @ApiOperation("个股分时数据")
    @ApiImplicitParam(name = "code", value = "股票编码")
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> getStockMinute(String code) {
        return stockService.getStockMinute(code);
    }


    @ApiOperation("个股日K线")
    @ApiImplicitParam(name = "code", value = "股票编码")
    @GetMapping("/stock/screen/dkline")
    public R<List<Stock4EvrDayDomain>> getStockDkLine(String code) {
        return stockService.getStockDkLine(code);
    }


    @ApiOperation("个股周K线")
    @ApiImplicitParam(name = "code", value = "股票编码")
    @GetMapping("/stock/screen/weekkline")
    public R<List<Stock4WeekDomain>> getStockWkLine(String code) {
        return stockService.getStockWkLine(code);
    }

    @ApiOperation("外盘数据获取")
    @GetMapping("/external/index")
    public R<List<OuterMarketDomain>> getExternalInfo() {
        return stockService.getOutMarketInfo();
    }

    @ApiOperation("股票搜索")
    @ApiImplicitParam(name = "searchStr", value = "搜索值")
    @GetMapping("/stock/search")
    public R<List<StockCodeLenovoDomain>> stockSearch(String searchStr) {
        return stockService.findById(searchStr);
    }

    @ApiOperation("个股详情数据")
    @ApiImplicitParam(name = "code", value = "股票编码")
    @GetMapping("/stock/describe")
    public R<Map> getDescribe(String code) {
        return stockService.getDescribe(code);
    }

    @ApiOperation("个股分时详情数据")
    @ApiImplicitParam(name = "code", value = "股票编码")
    @GetMapping("/stock/screen/second/detail")
    public R<Map> getDetail(String code) {
        return stockService.getDetail(code);
    }

    @ApiOperation("个股实时交易流水")
    @ApiImplicitParam(name = "code", value = "股票编码")
    @GetMapping("/stock/screen/second")
    public R<List<Map>> getSecond(String code) {
        return stockService.getSecond(code);
    }

    @ApiOperation(("股票新闻展示"))
    @GetMapping("/stock/news")
    public R<List<StockNews>> getStockNews(){
        return stockService.getStockNews();
    }

}
