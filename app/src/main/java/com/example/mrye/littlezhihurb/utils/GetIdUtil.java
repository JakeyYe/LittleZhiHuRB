package com.example.mrye.littlezhihurb.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;

/**
 * 根据page获取之前的时间日期字符串
 */

public class GetIdUtil {

    public static String getId(int page) {
        String nowString = new SimpleDateFormat("dd").format(new Date());
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);// 当前的年份
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);// 前一个月的月份，月份是从0索引开始的，要获取当前月则要加一
        Calendar c = new GregorianCalendar(nowYear, nowMonth, 0);
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 返回当前月的总天数。
        int now = Integer.parseInt(nowString);
        int x = now - page;
        if (x >= 1) {

            if (x >= 10) {
                return new SimpleDateFormat("yyyyMM").format(new Date()) + String.valueOf(x);
            } else {
                return new SimpleDateFormat("yyyyMM").format(new Date()) + "0" + String.valueOf(x);
            }

        } else {
            return String.valueOf(nowYear) + String.valueOf(nowMonth) + String.valueOf(days + x);
        }
    }

    public static String getWeek(String data) {
        String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();

        String strDate = data;// 定义日期字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");// 定义日期格式
        Date date = null;
        try {
            date = format.parse(strDate);// 将字符串转换为日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }
}
