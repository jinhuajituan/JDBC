package com.jinxujtuan.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
* 新增数据
* */
public class jdbcTest {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf8","root","123456");

        Statement statement = conn.createStatement();

        int count = statement.executeUpdate("INSERT INTO user(username,password,phone)VALUE ('root','123','123')");

        if(count>0){
            System.out.println("新增失败");
        }else{
            System.out.println("新增成功");
        }

        statement.close();
        conn.close();
    }
}
