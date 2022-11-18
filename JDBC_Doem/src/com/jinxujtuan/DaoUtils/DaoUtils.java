package com.jinxujtuan.DaoUtils;

import com.jinxujtuan.druidPooled.DBPoolUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
  * 公共处理增. 删. 改方法
  * sql语句，参数列表
  * sql 执行的sql语句
  * args 参数列表，占位符赋值
  * @return 受影响行数
  */
public class DaoUtils {
    public static int update(String sql,Object... args){
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DBPoolUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1,args[i]);
            }
            int i = preparedStatement.executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBPoolUtils.closeAll(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    /**
         * 公共查询方法
         * 实现了复用性
         *
         * @param sql       执行的sql语句
         * @param ct 封装集合对象的类型
         * @param args     参数列表，占位符赋值
         * @return 返回查询到的对象的集合
         */
    public static <T> List<T> queryList(Class<T> ct, String sql, Object... args){
        List<T> tList = new ArrayList<>();
        Connection connection = DBPoolUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData = null;

        try{
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            metaData = preparedStatement.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()){
                T t = ct.newInstance();
                for (int i = 1; i <= columnCount ; i++) {
                    String clumnName = metaData.getCatalogName(i);
                    Object clumnValue = resultSet.getObject(i);
                    Field field = ct.getDeclaredField(clumnName);
                    field.setAccessible(true);
                    field.set(t,clumnValue);
                }
                tList.add(t);
            }
            return tList;
        } catch (Exception e) {

        } finally {
            DBPoolUtils.closeAll(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /*
    * 公共查询方法
     * */
    public static <T> T queryOne(Class<T> ct, String sql, Object... args){
        Connection connection = DBPoolUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData = null;

        try{
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            metaData = preparedStatement.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()){
                T t = ct.newInstance();
                for (int i = 1; i <= columnCount ; i++) {
                    String clumnName = metaData.getCatalogName(i);
                    Object clumnValue = resultSet.getObject(i);
                    Field field = ct.getDeclaredField(clumnName);
                    field.setAccessible(true);
                    field.set(t,clumnValue);
                }
                return t;
            }
        } catch (Exception e) {

        } finally {
            DBPoolUtils.closeAll(connection,preparedStatement,resultSet);
        }
        return null;
    }

}
