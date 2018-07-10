package com.bigdata.utils;

import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.Serializable;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/10 17:32
 */
public class DataUtils implements Serializable {

    private static final long serialVersionUID = -8230203929547124640L;

    public static String getDateTime(String formatter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        return simpleDateFormat.format(new Date());
    }

    public static String getDateTime() {
        return DataUtils.getDateTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getDate() {
        return DataUtils.getDateTime("yyyy-MM-dd");
    }

    public static String getDate(String formatter) {
        return DataUtils.getDateTime(formatter);
    }

    public static String removeTime(String dateTime) {
        return dateTime.substring(0, dateTime.indexOf(" "));
    }

    /**
     * 获取指定时间之前minute的时间  例如:minute = 30, 2014-07-15 12:00:00 ->  2014-07-15 11:30:00
     *
     * @param time
     * @return
     */
    public static String getBeforeMinute(String time, int minute) {
        String result = time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date myDate = simpleDateFormat.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(myDate);
            c.add(Calendar.MINUTE, -minute);
            myDate = c.getTime();
            result = simpleDateFormat.format(myDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 截取日期 yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String splitDate(String date) {
        return date.substring(0, date.indexOf("")).replace("-", "");
    }

    /**
     * 替换{}中的变量
     *
     * @param data
     * @param key
     * @param newData
     * @return
     */
    public static String replaceParentheses(String data, String key, String newData) {
        return data.replace("\\{" + key + "\\}", newData);
    }

    /**
     * 格式化double，不使用科学计数法
     *
     * @param doubleValue
     * @param fractionDigits
     * @return
     */
    public static String formatDouble(String doubleValue, int fractionDigits) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(fractionDigits);
        return nf.format(Double.parseDouble(doubleValue));
    }

    public static String formatDouble(double doubleValue, int fractionDigits) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(fractionDigits);
        return nf.format(doubleValue);
    }

    public static String formatDouble(String doubleValue) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(2);
        return nf.format(Double.parseDouble(doubleValue));
    }

    public static String formatDouble(double doubleValue) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(2);
        return nf.format(doubleValue);
    }

    public static String getInt(Object str) {
        return Integer.toString(Integer.parseInt(str.toString().replaceAll("\\.\\d+", "")));
    }

    public static String getYesterday(String formatter) {
        SimpleDateFormat df = new SimpleDateFormat(formatter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return df.format(calendar.getTime());
    }


    public static void main(String[] args) {
        System.out.println(getDate());
    }

}
