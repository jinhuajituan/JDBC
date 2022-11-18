package com.jinxujtuan.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期转换
 * <p>
 * 字符串转换为UtilDate
 * 字符串转换为SqlDate
 * UtilDate转换为SqlDate
 * 注意：SqlDate转换为UtilDate是不可以的，因为UtilDate是SqlDate的父类
 */
public class DateUtilsAll {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /*
    * UtilDate转换为SqlDate
    * */
    public static java.sql.Date utilToSqlDate(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }

    /*
    * 字符串转换为SqlDate
    * 此转换不常用，而在JDBC中常用的转换只是前两个
     * */
    public static java.sql.Date strToSqlDate(String string){
        try{
            java.util.Date date = SIMPLE_DATE_FORMAT.parse(string);
            return new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
