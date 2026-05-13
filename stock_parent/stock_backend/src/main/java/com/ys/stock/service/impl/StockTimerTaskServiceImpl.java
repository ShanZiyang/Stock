package com.ys.stock.service.impl;

import com.google.common.collect.Lists;
import com.ys.stock.config.vo.StockInfoConfig;
import com.ys.stock.constants.ParseType;
import com.ys.stock.constants.StockConstants;
import com.ys.stock.exception.BusinessException;
import com.ys.stock.face.StockCacheFace;
import com.ys.stock.mapper.StockBlockRtInfoMapper;
import com.ys.stock.mapper.StockMarketIndexInfoMapper;
import com.ys.stock.mapper.StockNewsMapper;
import com.ys.stock.mapper.StockRtInfoMapper;
import com.ys.stock.pojo.StockBlockRtInfo;
import com.ys.stock.pojo.StockNews;
import com.ys.stock.service.StockTimerTaskService;
import com.ys.stock.utils.IdWorker;
import com.ys.stock.utils.ParserStockInfoUtil;
import com.ys.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/4 17:49
 * @description 定义采集股票接口实现
 */
@Service("stockTimerTaskService")
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private StockCacheFace stockCacheFace;

    @Autowired
    private StockNewsMapper stockNewsMapper;

    @Autowired
    private IdWorker idWorker;

    /*
     * 采集国内大盘数据实现
     * */
    @Override
    public void collertInnerMarketInfo() {
        //1.定义采集的url接口
        String innerUrl = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
        //2.调用restTemplate采集数据
        //2.1设置请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        //2.2发起请求
        String result = restTemplate.postForObject(innerUrl, new HttpEntity<>(headers), String.class);
//        log.info("当前采集的数据：{}", result);
        //3.数据采集
        List marketInfo = parserStockInfoUtil.parser4StockOrMarketInfo(result, ParseType.INNER);
        log.info("国内大盘数据：{}", marketInfo);
        //4.数据插入
        stockMarketIndexInfoMapper.insertBatch(marketInfo);

        //2.3获取公共采集时间点
//        Date curTime = DateTimeUtil.getDateTimeWithoutSecond(DateTime.now()).toDate();
//        //3.数据采集
//        //3.1编写正则表达式
//        String reg = "var hq_str_s_(.+)=\"(.+)\";";
//        //3.2获取编译对象
//        Pattern pattern = Pattern.compile(reg);
//        //3.3匹配字符串
//        Matcher matcher = pattern.matcher(result);
//
//        //4.收集大盘封装后的对象
//        ArrayList<StockMarketIndexInfo> arrayList = new ArrayList<>();
//        while (matcher.find()) {
//            //获取大盘id
//            String marketId = matcher.group(1);
//            System.out.println(marketId);
//            //其它信息
//            String other = matcher.group(2);
//            String[] others = other.split(",");
//            //大盘名称
//            String marketName = others[0];
////            System.out.println(marketName);
//            //当前点
//            BigDecimal curPoint = new BigDecimal(others[1]);
//            //当前价格
//            BigDecimal currentPrice = new BigDecimal(others[2]);
//            //涨跌率
//            BigDecimal upDownRate = new BigDecimal(others[3]);
//            //成交量
//            Long tradeAccount = Long.valueOf(others[4]);
//            //成交金额
//            Long tradeVolume = Long.valueOf(others[5]);
//
//            StockMarketIndexInfo build = StockMarketIndexInfo.builder()
//                    .id(idWorker.nextId() + "")
//                    .markId(marketId)
//                    .markName(marketName)
//                    .curPoint(curPoint)
//                    .currentPrice(currentPrice)
//                    .updownRate(upDownRate)
//                    .tradeAccount(tradeAccount)
//                    .tradeVolume(tradeVolume)
//                    .curTime(curTime).build();
//            arrayList.add(build);
//        }
////        log.info("集合长度：{}，内容：{}", arrayList.size(), arrayList);
//        //批量插入
//        if (CollectionUtils.isEmpty(arrayList)) {
//            log.info("");
//            return;
//        }
//        stockMarketIndexInfoMapper.insertBatch(arrayList);
//        String curTime1 = DateTime.now().toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"));
//        log.info("采集的大盘数据：{},当前时间：{}", arrayList, curTime1);

    }

    /*
     * 采集国内A股股票详细信息
     * http://hq.sinajs.cn/list=sz000002,sh600015
     * */
    @Override
    public void collectAShareInfo() {
        //1.获取所有的股票code的集合

//        List<String> stockList = stockBusinessMapper.getStockCode();
        List<String> stockList = stockCacheFace.getAShareStockCodes();

        //1.1转化集合中的code编码，添加前缀
        stockList = stockList.stream().map(id -> {
            if (id.startsWith("6")) {
                id = "sh" + id;
            } else {
                id = "sz" + id;
            }
            return id;
        }).collect(Collectors.toList());
        //1.2设置公共请求头对象
        HttpHeaders headers = new HttpHeaders();
        //1.3设置请求头数据
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //2.将股票集合分片处理，均等分
//        Lists.partition(stockList, StockConstants.INSERTNUM).forEach(list -> {
//            //3.为每一份动态拼接url地址，再拉取数据
//            String url = stockInfoConfig.getMarketUrl() + String.join(",", list);
//            //3.1相应数据
//            String result = restTemplate.postForObject(url, entity, String.class);
//            //4.解析数据，封装
//            List infos = parserStockInfoUtil.parser4StockOrMarketInfo(result, ParseType.ASHARE);
//            log.info("当前解析的股票集合数据：{}", infos);
////            System.out.println(infos);
//            //5.批量插入
//            stockRtInfoMapper.insertBatch(infos);
//        });

        Lists.partition(stockList, StockConstants.INSERTNUM).forEach(list -> {
            //加入线程池后，异步多线程处理数据采集，提高了操作效率和数据库插入效率（数据库io增加）
            threadPoolTaskExecutor.execute(() -> {
                //3. 为每一份动态拼接url地址，再拉取数据
                String url = stockInfoConfig.getMarketUrl() + String.join(",", list);
                //3.1相应数据
                String result = restTemplate.postForObject(url, entity, String.class);
                //4.解析数据，封装
                List infos = parserStockInfoUtil.parser4StockOrMarketInfo(result, ParseType.ASHARE);
                log.info("当前解析的股票集合数据：{}", infos);
                //System.out.println(infos);
                //5.批量插入
                stockRtInfoMapper.insertBatch(infos);
            });
        });

    }

    /*
     * 采集板块信息
     * http://vip.stock.finance.sina.com.cn/q/view/newSinaHy.php
     * */
    @Override
    public void collectBlockInfo() {
        //1.获取板块地址
        String url = stockInfoConfig.getBlockUrl();
        //2.响应数据请求
        String result = restTemplate.getForObject(url, String.class);
        //3.数据转换
        List<StockBlockRtInfo> infos = parserStockInfoUtil.parse4StockBlock(result);
        log.info("板块化数据：{}", infos);
        //4.批量插入
        Lists.partition(infos, StockConstants.INSERTNUM).forEach(list -> {
            threadPoolTaskExecutor.execute(() -> {
                stockBlockRtInfoMapper.insertBatch(list);

            });
        });
    }

    /*
     * 采集国外板块
     * http://hq.sinajs.cn/list=int_dji,int_nasdaq,int_hangseng,int_nikkei,b_TWSE,b_FSSTI
     * */
    @Override
    public void collectOuterMarketInfo() {
        //1.获取板块地址
        String outerUrl = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getOuter());
        //2设置请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String result = restTemplate.postForObject(outerUrl, new HttpEntity<>(headers), String.class);
        //3.响应数据
        List marketInfo = parserStockInfoUtil.parser4StockOrMarketInfo(result, ParseType.OUTER);
        System.out.println(marketInfo);
        //4.批量插入
        stockMarketIndexInfoMapper.insertBatch(marketInfo);
    }

    @Override
    public void collectNews() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建GET请求
        HttpGet httpGet = new HttpGet("http://finance.sina.com.cn/stock/hangqing/index.html");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        // 获取响应结果
        CloseableHttpResponse response = httpClient.execute(httpGet);

        if (!(response.getStatusLine().getStatusCode() == 200)) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }

        String html = EntityUtils.toString(response.getEntity(), "UTF-8");

        List<StockNews> newsList = new ArrayList<>();

        // 创建Document对象
        Document document = Jsoup.parse(html);

        Element list = document.getElementsByClass("list_009").first();
        Elements items = list.select("li");

        for (Element item : items) {
            Element link = item.select("a[href]").first();
            Element timestamp = item.select("span").first();
            String url = link.attr("href");
            String text = link.text();
            String time = timestamp.text().replaceAll("[(|)]","");
            StockNews newsinfo = StockNews.builder()
                    .id(idWorker.nextId().toString())
                    .url(url)
                    .info(text)
                    .time(time).build();
            newsList.add(newsinfo);
//                System.out.println("链接：" + url);
//                System.out.println("中文字段：" + text);
//                System.out.println("时间戳：" + time);
        }
//            System.out.println(lists);
//            System.out.println(html);
//            return news;
        httpClient.close();
        response.close();
        stockNewsMapper.insertBatch(newsList);
//        System.out.println(newsinfo);

    }


}
