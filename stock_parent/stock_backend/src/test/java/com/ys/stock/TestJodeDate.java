package com.ys.stock;

import com.ys.stock.config.CommonConfig;
import com.ys.stock.utils.DateTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 *
 * @author 杨森
 * @date 2023/6/23 21:46
 * @Description
 */
@SpringBootTest(classes = CommonConfig.class)
//@SpringBootTest({})
public class TestJodeDate {

    @Test
    public void testJode(){
        //获取指定时间下最近的股票有效交易时间
        //周末--->20230623145800
        DateTime target = DateTime.now().withDate(2023, 6, 23);
        //周六--->20230623145800
//        target = DateTime.now().withDate(2023, 6, 22);
        //周一 上午九点--->20230622145800
        target= DateTime.now().withDate(2023, 6, 23).withHourOfDay(9).withMinuteOfHour(0);
        //开盘 上午 9:30 到11点半--->20230623094000
        target=DateTime.now().withDate(2023, 6, 23).withHourOfDay(9).withMinuteOfHour(40);
        //中午休盘：11:30 到 13:00之间--->20230623113000
        target=DateTime.now().withDate(2023, 6, 23).withHourOfDay(12).withMinuteOfHour(40);
        //开盘 下午13:00 到15:00-->20230623144000
        target=DateTime.now().withDate(2023, 6, 23).withHourOfDay(14).withMinuteOfHour(40);
        //停盘 15:00后-->20230623145800
        target=DateTime.now().withDate(2023, 6, 23).withHourOfDay(15).withMinuteOfHour(40);
        String leastDateTime = DateTimeUtil.getLastDateString4Stock(target);
        System.out.println(leastDateTime);
    }

    @Test
    public void testApi(){
        //获取jode下的当前时间
        DateTime now = DateTime.now();
        //日期后退指定的时间
        DateTime plusDay = now.plusDays(1);
        System.out.println(plusDay);
        //前推指定的时间
        DateTime preDate = now.minusDays(5);
        System.out.println(preDate);
        //随意指定日期
        DateTime dateTime = now.withMonthOfYear(8);
        System.out.println(dateTime);
        //转java中的date
        Date date = dateTime.toDate();
        System.out.println(date);
        //日期格式化
        String strDate = dateTime.toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"));
        System.out.println(strDate);
    }

}
