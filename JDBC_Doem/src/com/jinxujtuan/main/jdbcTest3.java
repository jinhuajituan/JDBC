package com.jinxujtuan.main;


import java.sql.*;

/*
 * SQL注入案例 (PreparedStatement: 预编译 )
 *  PreparedStatement preparedStatement = connection.prepareStatement(sql);
 * */
public class jdbcTest3 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String URL = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf8";
        String User = "root";
        String Password = "123456";
        Connection conn = DriverManager.getConnection(URL,User,Password);
        String username = "'黑客' or 1=1 ; #";
        String password = "123";
        String sql = "select * from user where username = " + username + "and password = " + password;
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            System.out.println("登陆成功！");
        }else{
            System.out.println("登陆失败！");
        }
        resultSet.close();
        statement.close();
        conn.close();
    }

}
