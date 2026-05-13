package com.ys.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.ys.stock.common.domain.*;
import com.ys.stock.config.vo.StockInfoConfig;
import com.ys.stock.mapper.*;
import com.ys.stock.pojo.StockBusiness;
import com.ys.stock.pojo.StockNews;
import com.ys.stock.service.StockService;
import com.ys.stock.utils.DateTimeUtil;
import com.ys.stock.vo.resp.PageResult;
import com.ys.stock.vo.resp.R;
import com.ys.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 杨森
 * @description:
 * @date 2023年06月15日 18:11
 */
@Service("stockService")
@Slf4j
public class StockServiceImpl implements StockService {
    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Resource
    private StockNewsMapper stockNewsMapper;


    @Override
    public List<StockBusiness> findAll() {
        return stockBusinessMapper.findAll();
    }

    /**
     * 获取最新的A股大盘信息
     * 如果不在股票交易日，则显示最近最新的交易数据信息
     */
    @Override
    public R<List<InnerMarketDomain>> getNewAMarketInfo() {
        //1.获取国内A股大盘的ID集合
        List<String> inners = stockInfoConfig.getInner();
        //2.获取最近股票交易日期
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //3.转Java中的Date
        Date lastDate = lastDateTime.toDate();

        //TODO mock测试数据,后期通过第三方接口动态获取实时数据 可删除

        lastDate = DateTime.parse("2024-01-03 11:15:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //4.将获取的Java Date传入接口
        List<InnerMarketDomain> list = stockMarketIndexInfoMapper.getMarketInfo(inners, lastDate);
        //5.返回查询结果
        return R.ok(list);
    }

    /**
     * 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * 原始：查询全部数据然后排序
     * 优化：避免全表查询 根据时间范围查询，提高查询效率
     */
    @Override
    public R<List<StockBlockDomain>> sectorAll() {
        //获取最近股票交易日期
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //转Java中的Date
        Date lastDate = lastDateTime.toDate();
        //TODO mock测试数据,后期通过第三方接口动态获取实时数据 可删除
        lastDate = DateTime.parse("2023-07-10 15:02:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //1.调用mapper接口获取数据 TODO 优化 避免全表查询 根据时间范围查询，提高查询效率
        List<StockBlockDomain> infos = stockBlockRtInfoMapper.sectorAll(lastDate);
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    /**
     * 沪深两市个股涨幅分时行情数据查询，以时间顺序和涨幅查询前10条数据
     *
     * @return com.ys.stock.vo.resp.R < java.util.List < com.ys.stock.common.domain.StockUpdownDomain > >
     * @author 杨森
     * @date 2023/6/24 15:34
     */
    @Override
    public R<List<StockUpdownDomain>> stockIncrease() {
//        涨跌：当前价-前收盘价
//        涨幅：（当前价-前收盘价）/前收盘价 * 100%
//        振幅：（最高成交价-最低成交价）/前收盘价 * 100%
        //1.直接调用mapper查询前10的数据
        //优化：
        //获取最近最新的股票有效交易时间点（精确到分钟）
        Date curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //mock数据 TODO mock数据
        String mockStr = "2023-12-30 13:30:00";
        curDateTime = DateTime.parse(mockStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        List<StockUpdownDomain> infos = stockRtInfoMapper.stockIncreaseLimit(curDateTime);
        //2.判断是否有数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }

    /**
     * 沪深两市个股行情列表查询 ,以日期和涨幅分页查询
     *
     * @param page     当前页
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public R<PageResult<StockUpdownDomain>> getStockPage(Integer page, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(page, pageSize);
        //2.通过mapper查询
        List<StockUpdownDomain> infos = stockRtInfoMapper.stockAll();
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        //3.封装到PageResult下
        //3.1 封装PageInfo对象
        PageInfo<StockUpdownDomain> listPageInfo = new PageInfo<StockUpdownDomain>(infos);
        //3.2 将PageInfo转PageResult
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(listPageInfo);
        //4.封装R响应对象
        return R.ok(pageResult);
    }

    /**
     * 功能描述：沪深两市涨跌停分时行情数据查询，查询T日每分钟的涨跌停数据（T：当前股票交易日）
     * 查询每分钟的涨停和跌停的数据的同级；
     * 如果不在股票的交易日内，那么就统计最近的股票交易下的数据
     * 从当天开盘日期到当天最近的有效交易日期
     */
    @Override
    public R<Map> upDownCount() {
        //1.获取股票最近的有效交易日期,精确到秒
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //当前最近有效期
        Date curTime = curDateTime.toDate();
        //开盘日期
        Date openTime = DateTimeUtil.getOpenDate(curDateTime).toDate();
        //关盘日期
        Date closeTime = DateTimeUtil.getCloseDate(curDateTime).toDate();

        //TODO mock_data 后续数据实时获取时，注释掉
        String curTimeStr = "20240106182500";
        //对应开盘日期 TODO mock_data
        String openTimeStr = "20240106092500";
        String closeTimeStr = "20240106150000";
        //如果现在时间大于关盘日期 以关盘日期为查询区间，否则以当前日期为查询区间
        if (curTimeStr.compareTo(closeTimeStr) > 0) {
            curTimeStr = closeTimeStr;
        }
//        curTime = DateTime.parse(curTimeStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        closeTime = DateTime.parse(curTimeStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        openTime = DateTime.parse(openTimeStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();

        //2.统计涨停的数据 约定：1代表涨停 0：跌停
        List<Map> upCount = stockRtInfoMapper.upDownCount(openTime, closeTime, 1);
        //3.统计跌停的数据
        List<Map> downCount = stockRtInfoMapper.upDownCount(openTime, closeTime, 0);
        //4.组装数据到map
        HashMap<String, List<Map>> info = new HashMap<>();
        info.put("upList", upCount);
        info.put("downList", downCount);
        //5.响应
        return R.ok(info);
    }

    /**
     * 将指定页的股票数据导出到excel表下
     *
     * @param response 响应体
     * @param page     当前页
     * @param pageSize 每页大小
     */
    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            //1.设置响应数据的类型:excel
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //4.分页查询股票数据
            PageHelper.startPage(page, pageSize);
            List<StockUpdownDomain> infos = this.stockRtInfoMapper.stockAll();
            Gson gson = new Gson();
            if (CollectionUtils.isEmpty(infos)) {
                R<String> error = R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
                //将错误信息转化成json字符串响应前端
                String jsonData = gson.toJson(error);
                //响应前端错误数据
                response.getWriter().write(jsonData);
                //终止程序
                return;
            }
            //将List<StockUpdownDomain>转化为<StockExcelDomain>
            List<StockExcelDomain> excelDomains = infos.stream().map(info -> {
                StockExcelDomain domain = new StockExcelDomain();
                BeanUtils.copyProperties(info, domain);
                return domain;
            }).collect(Collectors.toList());
            //5.导出
            EasyExcel.write(response.getOutputStream(), StockExcelDomain.class)
                    .sheet("股票数据")
                    .doWrite(excelDomains);
        } catch (IOException e) {
            log.info("股票excel数据导出异常，当前页：{}，每页大小：{}，异常信息：{}", page, pageSize, e.getMessage());
        }
    }

    /**
     * 功能描述：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * 如果当前日期不在股票交易日，则按照前一个有效股票交易日作为T日查询
     * map结构示例：
     * {
     * "volList": [{"count": 3926392,"time": "202112310930"},......],
     * "yesVolList":[{"count": 3926392,"time": "202112310930"},......]
     * }
     *
     * @return
     */
    @Override
    public R<Map> stockTradeCmp() {
        //1.获取T日和T-1日的开始时间和结束时间
        //1.1获取最近股票有效交易时间点
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateTime);

        //转换成java中Date，jdbc识别
        Date startTime4T = openDateTime.toDate();
        Date endTime4T = lastDateTime.toDate();
        //TODO mock数据
        String tDateStr = "20240103093000";
        startTime4T = DateTime.parse(tDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDateStr = "20240103143000";
        endTime4T = DateTime.parse(openDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();

        //1.2获取T-1日的区间时间
        //先获取lastDateTime的上一个股票有效交易日
        DateTime preLastDateTime = DateTimeUtil.getPreviousTradingDay(lastDateTime);
        DateTime preOpenDateTime = DateTimeUtil.getOpenDate(preLastDateTime);
        //转换成java中Date，jdbc识别
        Date startTime4PreT = preOpenDateTime.toDate();
        Date endTime4PreT = preLastDateTime.toDate();

        //TODO mock数据
        String tDateStr2 = "20240102093000";
        startTime4PreT = DateTime.parse(tDateStr2, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDateStr2 = "20240102143000";
        endTime4PreT = DateTime.parse(openDateStr2, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();

        //2.获取上证和深证的配置的大盘id
        //2.1获取大盘id集合
        List<String> marketIds = stockInfoConfig.getInner();

        //3.分别查询T日和T-1日的交易量数据，得到两个集合
        //3.1查询T日大盘交易统计数据
        List<Map> date4T = stockMarketIndexInfoMapper.getStockTradeVol(marketIds, startTime4T, endTime4T);
        if (CollectionUtils.isEmpty(date4T)) {
            date4T = new ArrayList<>();
        }
        //3.2查询T-1日大盘交易统计数据
        List<Map> date4PreT = stockMarketIndexInfoMapper.getStockTradeVol(marketIds, startTime4PreT, endTime4PreT);
        if (CollectionUtils.isEmpty(date4PreT)) {
            date4PreT = new ArrayList<>();
        }
        //4.组装响应数据
        HashMap<String, List> info = new HashMap<>();
        info.put("volList", date4T);
        info.put("yesVolList", date4PreT);
        //5.返回数据
        return R.ok(info);
    }


    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     *
     * @return
     */
    @Override
    public R<Map> getStockUpDown() {
        //1.获取股票最新交易时间
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock
        lastDate = DateTime.parse("2023-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.插入mapper接口获取统计数据
        List<Map> info = stockRtInfoMapper.getStockUpDown(lastDate);
        if (CollectionUtils.isEmpty(info)) {
            info = new ArrayList<>();
        }
        //保证涨幅区间按照从小到大排序，且对于没有数据的涨幅区间默认0
        //按照遍历自定的涨幅区间集合，然后获取对应的map
        //2.1 获取涨幅区间顺序集合
        List<String> upDownRangeList = stockInfoConfig.getUpDownRange();
        //通过Stream流map映射和过滤完成转换
        List<Map> finalInfo = info;
        List<Map> collect = upDownRangeList.stream().map(item -> {
            Optional<Map> optional = finalInfo.stream().filter(map -> map.get("title").equals(item)).findFirst();
            Map tmp = null;

            //判断结果是否有map
            if (optional.isPresent()) {
                tmp = optional.get();
            } else {
                tmp = new HashMap();
                tmp.put("title", item);
                tmp.put("count", 0);
            }
            return tmp;
        }).collect(Collectors.toList());

//        List<Map> newMaps = new ArrayList<>();
//        for (String item : upDownRangeList) {
//            //循环查询的info集合，找到item对应的map
//            Map tmp = null;
//            for (Map infos : info) {
//                if (infos.get("title").equals(item)) {
//                    tmp = infos;
//                }
//            }
//            if (tmp == null) {
//                tmp = new HashMap<>();
//                tmp.put("title", item);
//                tmp.put("count", 0);
//            }
//            newMaps.add(tmp);
//        }
        //3.组装数据 响应
        HashMap<String, Object> data = new HashMap<>();
        //获取日期格式
        String stringDate = dateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        data.put("time", stringDate);
        data.put("infos", collect);
        return R.ok(data);
    }

    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     *
     * @param code 股票编码
     * @return
     */
    @Override
    public R<List<Stock4MinuteDomain>> getStockMinute(String code) {
        //1.获取最近有效的股票交易时间
        DateTime curTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //获取当前日期
        Date curDate = curTime.toDate();
        //获取当前日期对应的开盘日期
        Date openDate = DateTimeUtil.getOpenDate(curTime).toDate();
        //TODO mock数据
        String mockDate = "20240106142500";
        curDate = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDateStr = "20240106093000";
        openDate = DateTime.parse(openDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        List<Stock4MinuteDomain> maps = stockRtInfoMapper.stockMinute(code, openDate, curDate);
        if (CollectionUtils.isEmpty(maps)) {
            maps = new ArrayList<>();
        }
        return R.ok(maps);
    }

    /**
     * 功能描述：单个个股日K数据查询 ，可以根据时间区间查询数日的K线数据
     * 默认查询历史20天的数据；
     *
     * @param code 股票编码
     * @return
     */
    @Override
    public R<List<Stock4EvrDayDomain>> getStockDkLine(String code) {
        //1.获取查询的日期范围
        //1.1获取截止时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = endDateTime.toDate();
        //TODO moke数据后续删除
        endTime = DateTime.parse("2024-01-06 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2获取开始时间
        DateTime startDateTime = endDateTime.minusDays(30);
        Date startTime = startDateTime.toDate();
        startTime = DateTime.parse("2024-01-01 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //获取指定范围的收盘日期集合
        List<Date> closeDates = stockRtInfoMapper.getCloseDates(code, startTime, endTime);
        //根据收盘日期精准查询，如果不在收盘日期，则查询最新数据
        List<Stock4EvrDayDomain> infos = stockRtInfoMapper.stockDkLine(code, closeDates);
//        List<Stock4EvrDayDomain> infos= stockRtInfoMapper.stockDkLine(code,startTime,endTime);
        if (CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<>();
        }
        return R.ok(infos);
    }

    @Override
    public R<List<Stock4WeekDomain>> getStockWkLine(String code) {

        DateTime curTime = DateTime.parse("2024-01-05 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        DateTime curDate = DateTimeUtil.getLastDate4Stock(curTime.minusSeconds(60));
//        DateTime curDate = DateTimeUtil.getLastDate4Stock(DateTime.now().minusSeconds(60));
        Date startDate = curDate.minusDays(140).toDate();
        Date endDate = curDate.toDate();
        List<Stock4WeekDomain> data = stockRtInfoMapper.getWkLine(code, startDate, endDate);
        return R.ok(data);
    }

    @Override
    public R<List<OuterMarketDomain>> getOutMarketInfo() {
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date lastDate = lastDateTime.toDate();
        //TODO mock测试数据,后期通过第三方接口动态获取实时数据 可删除
        lastDate = DateTime.parse("2023-01-02 12:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
//        System.out.println(lastDate);
        List<OuterMarketDomain> list = stockMarketIndexInfoMapper.getOuterMarket(lastDate);
        return R.ok(list);
    }

    @Override
    public R<List<StockCodeLenovoDomain>> findById(String searchStr) {
        if (StringUtils.isBlank(searchStr)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        List<StockCodeLenovoDomain> info = stockBusinessMapper.selectById(searchStr);
        return R.ok(info);
    }

    @Override
    public R<Map> getDescribe(String code) {
        Map info = stockBusinessMapper.getDescribe(code);
        return R.ok(info);
    }

    @Override
    public R<Map> getDetail(String code) {
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now().minusSeconds(60)).toDate();
        //TODO mock时间
        curDate = DateTime.parse("2024-01-05 09:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        Map info = stockRtInfoMapper.getDetail(code, curDate);
        return R.ok(info);
    }

    @Override
    public R<List<Map>> getSecond(String code) {
        List<Map> data = stockRtInfoMapper.selectStockTradingFlow(code);
        return R.ok(data);
    }


    @Override
    public R<List<StockNews>> getStockNews() {
        List<StockNews> news = stockNewsMapper.selectAll();
        return R.ok(news);
    }
}
