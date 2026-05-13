package com.ys.stock;

import com.ys.stock.exception.BusinessException;
import com.ys.stock.pojo.StockNews;
import com.ys.stock.vo.resp.ResponseCode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/26 17:37
 */
@SpringBootTest
public class TestHttpClient {

    @Test
    public void testLinked() throws IOException {
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

        StockNews newsinfo = null;
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
            newsinfo = StockNews.builder()
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
        for (StockNews news : newsList) {
            System.out.println(news);
        }
//        System.out.println(newsList);
//        return newsinfo;

    }

}
