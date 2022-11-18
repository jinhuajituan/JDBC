package com.jinxujtuan.druidPooled;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
* 连接池工具类
* */
public class DBPoolUtils {
    /*
    * 连接池对象
    * */
    private static DruidDataSource druidDataSource;
    static {
        Properties properties = new Properties();
        InputStream inputStream = DBPoolUtils.class.getResourceAsStream("/database.properties");
        try{
            properties.load(inputStream);
            //使用德鲁伊工厂创建连接池
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 获得数据库连接对象
     * */
    public static Connection getConnection(){
        try{
            //在连接池中获得Connection
            return druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 调用的close()方法不是关闭资源，而是将资源放回池中！
     * */
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet){
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
