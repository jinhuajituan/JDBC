package com.jinxujtuan.main;

import java.sql.*;

/*
* 根据数据库列的名称来获取数据
* 例 : int id = resultSet.getInt("id");
*
* 根据数据库列的下标来获取数据
* 例 : int id = resultSet.getInt(1);
* */
public class jdbcTest2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        String URL = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf8";
        String User = "root";
        String Password = "123456";
        Connection conn = DriverManager.getConnection(URL,User,Password);
        Statement statement = conn.createStatement();
        String sql = "select id, username, password, phone from \n" + "user";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            /*根据列的名称获取数据*/
            /*int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String phone = resultSet.getString("phone");*/

            /*根据列的下标来获取数据*/
            int id = resultSet.getInt(1);
            String username = resultSet.getString(2);
            String password = resultSet.getString(3);
            String phone = resultSet.getString(4);


            System.out.println(id + "\t" + username + "\t" + password + "\t" + phone);
        }
        resultSet.close();
        statement.close();
        conn.close();
    }
}
