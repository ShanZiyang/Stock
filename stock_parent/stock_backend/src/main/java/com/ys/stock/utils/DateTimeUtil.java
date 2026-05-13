package com.ys.stock.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author 杨森
 * @date 2023/6/23 21:45
 * @description 日期时间工具类
 */
public class DateTimeUtil {
    /**
     * 获取指定日期下股票的上一个有效交易日时间
     *
     * @return
     */
    public static DateTime getPreviousTradingDay(DateTime dateTime) {
        //获取指定日期对应的工作日
        int weekNum = dateTime.dayOfWeek().get();
        //判断所属工作日
        DateTime preDateTime = null;
        //周一，那么T-1就是周五
        if (weekNum == 1) {
            //日期后退3天
            preDateTime = dateTime.minusDays(3);
        }
        //周末，那么T-1就是周五
        else if (weekNum == 7) {
            preDateTime = dateTime.minusDays(2);
        } else {
            preDateTime = dateTime.minusDays(1);
        }
        return getDateTimeWithoutSecond(preDateTime);
    }


    /**
     * 判断是否是工作日
     *
     * @return true：在工作日 false:不在工作日
     */
    public static boolean isWorkDay(DateTime dateTime) {
        //获取工作日
        int weekNum = dateTime.dayOfWeek().get();
        return weekNum >= 1 && weekNum <= 5;
    }

    /**
     * 获取上一天日期
     *
     * @param dateTime
     * @return
     */
    public static DateTime getPreDateTime(DateTime dateTime) {
        return dateTime.minusDays(1);
    }

    /**
     * 日期转String
     *
     * @param dateTime 日期
     * @param pattern  日期正则格式
     * @return
     */
    public static String parseToString(DateTime dateTime, String pattern) {
        return dateTime.toString(DateTimeFormat.forPattern(pattern));
    }

    /**
     * 获取股票日期格式字符串
     *
     * @param dateTime
     * @return
     */
    public static String parseToString4Stock(DateTime dateTime) {
        return parseToString(dateTime, "yyyyMMddHHmmss");
    }


    /**
     * 获取指定日期的收盘日期
     *
     * @param dateTime
     * @return
     */
    public static DateTime getCloseDate(DateTime dateTime) {
        return dateTime.withHourOfDay(14).withMinuteOfHour(58).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    /**
     * 获取指定日期的开盘日期
     *
     * @param dateTime
     * @return
     */
    public static DateTime getOpenDate(DateTime dateTime) {
        return dateTime.withHourOfDay(9).withMinuteOfHour(30).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    /**
     * 获取最近的股票有效时间，精确到分钟
     *
     * @param target
     * @return
     */
    public static String getLastDateString4Stock(DateTime target) {
        DateTime dateTime = getLastDate4Stock(target);
        dateTime = getDateTimeWithoutSecond(dateTime);
        return parseToString4Stock(dateTime);
    }

    /**
     * 获取最近的股票有效时间，精确到分钟
     *
     * @param target
     * @return
     */
    public static DateTime getLastDate4Stock(DateTime target) {
        //判断是否是工作日
        if (isWorkDay(target)) {
            //当前日期开盘前
            if (target.isBefore(getOpenDate(target))) {
                target = getCloseDate(getPreviousTradingDay(target));
            } else if (isMarketOffTime(target)) {
                target = target.withHourOfDay(11).withMinuteOfHour(30).withSecondOfMinute(0).withMillisOfSecond(0);
            } else if (target.isAfter(getCloseDate(target))) {
                //当前日期收盘后
                target = getCloseDate(target);
            }
        } else {
            //非工作日
            target = getCloseDate(getPreviousTradingDay(target));
        }
        target = getDateTimeWithoutSecond(target);
        return target;
    }

    /**
     * 判断当前时间是否在大盘的中午休盘时间段
     *
     * @return
     */
    public static boolean isMarketOffTime(DateTime target) {
        //上午休盘开始时间
        DateTime start = target.withHourOfDay(11).withMinuteOfHour(30).withSecondOfMinute(0).withMillisOfSecond(0);
        //下午开盘时间
        DateTime end = target.withHourOfDay(13).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        if (target.isAfter(start) && target.isBefore(end)) {
            return true;
        }
        return false;
    }

    /**
     * 将秒时归零
     *
     * @param dateTime 指定日期
     * @return
     */
    public static DateTime getDateTimeWithoutSecond(DateTime dateTime) {
        DateTime newDate = dateTime.withSecondOfMinute(0).withMillisOfSecond(0).withMillisOfSecond(0);
        return newDate;
    }


    /**
     * 将秒时归零
     *
     * @param dateTime 指定日期字符串，格式必须是：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static DateTime getDateTimeWithoutSecond(String dateTime) {
        DateTime parse = DateTime.parse(dateTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        return getDateTimeWithoutSecond(parse);
    }

    /**
     * 获取指定日期下股票的上一个周的周五时间
     *
     * @return
     */
    public static DateTime getPreviousFriDay(DateTime dateTime) {
        //获取指定日期对应的工作日
        int weekNum = dateTime.dayOfWeek().get();
        //判断所属工作日
        DateTime preDateTime = null;
        switch (weekNum) {
            case 1:
                preDateTime = dateTime.minusDays(3);
                break;
            case 2:
                preDateTime = dateTime.minusDays(4);
                break;
            case 3:
                preDateTime = dateTime.minusDays(5);
                break;
            case 4:
                preDateTime = dateTime.minusDays(6);
                break;
            case 5:
                preDateTime = dateTime.minusDays(0);
                break;
            case 6:
                preDateTime = dateTime.minusDays(1);
                break;
            case 7:
                preDateTime = dateTime.minusDays(2);
                break;
        }
        return getDateTimeWithoutSecond(preDateTime);
    }


    public static String dealDateFormat(String oldDateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); //yyyy-MM-dd'T'HH:mm:ss.SSSZ

        Date date = df.parse(oldDateStr);

        SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);

        Date date1 = df1.parse(date.toString());

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df2.format(date1);
    }
}
