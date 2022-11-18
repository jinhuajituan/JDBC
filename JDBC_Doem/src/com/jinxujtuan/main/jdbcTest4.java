package com.jinxujtuan.main;


import java.sql.*;

/*
 * SQL注入案例 (PreparedStatement: 预编译 )
 *  PreparedStatement preparedStatement = connection.prepareStatement(sql);
 * */
public class jdbcTest4 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String URL = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf8";
        String User = "root";
        String Password = "123456";
        Connection conn = DriverManager.getConnection(URL,User,Password);
        String sql = "select * from user where username = ? and password = ?";

        //预编译SQL语句————提前把SQL语句编译为字符串，其中用到了转义字符防止个别符号注入SQL
        PreparedStatement prst = conn.prepareStatement(sql);
        System.out.println(prst);

        String  username = "张三";
        prst.setString(1,username);
        String password = "123456";
        prst.setString(2,password);

        System.out.println(prst);

        ResultSet resultSet = prst.executeQuery();
        if(resultSet.next()){
            System.out.println("登陆成功！");
        }else{
            System.out.println("登陆失败！");
        }
        resultSet.close();
        prst.close();
        conn.close();
    }

}
