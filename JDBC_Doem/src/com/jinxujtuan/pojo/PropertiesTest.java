package com.jinxujtuan.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/*
* 数据库工具类
* 1.获取连接对象--Connection
* 2.提供统一释放资源
* 可跨平台性方案
 * */
public class PropertiesTest {
    private static final Properties PROPERTIES = new Properties();
    static {
        try{
            InputStream inputStream = PropertiesTest.class.getResourceAsStream("/db.properties");
            PROPERTIES.load(inputStream);//通过流将配置信息的内容分割成键值对
            Class.forName(PROPERTIES.getProperty("jdbc.driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    * 获取连接对象
    * @return 返回连接对象
     * */
    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(PROPERTIES.getProperty("jdbc.url"),
                    PROPERTIES.getProperty("jdbc.username"),
                    PROPERTIES.getProperty("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /*
    * 释放资源
    * @param connection 连接对象
    * @param statement 发送SQL语句的对象，是PreparedStatement的父接口，即使PreparedStatement用的广泛，父接口中也包含了它
    * @param resultSet 存储结果的结果集对象
     * */
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet){
        try{
            if(resultSet != null){
                resultSet.close();
            }if(statement != null){
                statement.close();
            }if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
