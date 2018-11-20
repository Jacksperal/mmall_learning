package com.mmall.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    //joda-time

    //String->Date
    //Date->String

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /*
    public static Date strToDate(String dateTimeStr,String formatStr){
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(formatStr);
        Date date = null;
        try {
            date = dateFormat.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToStr(Date date, String formatStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(formatStr);
        String str = dateFormat.format(date);
        return str;
    }
    */




    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static Date strToDate(String dateTimeStr){
        return strToDate(dateTimeStr,STANDARD_FORMAT);
    }

    public static String dateToStr(Date date, String formatStr) {
        if(date == null){
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString();
    }

    public static String dateToStr(Date date) {
        return dateToStr(date,STANDARD_FORMAT);
    }

    /*public static void main(String[] args) {
        Date date = DateTimeUtil.strToDate("2018/12/10 12:00:12", "yyyy/MM/dd HH:mm:ss");
        System.out.println(date);
        String s = DateTimeUtil.dateToStr(new Date(), "yyyy/MM/dd HH:mm:ss");
        System.out.println(s);
    }*/
}
