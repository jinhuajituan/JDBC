package com.jinxujtuan.EntityClass;

import com.jinxujtuan.druidPooled.DBPoolUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OtmTest {
    public static void main(String[] args) {
        Connection connection = DBPoolUtils.getConnection();
        String sql = "select id,username,password,sex,email,address from t_user";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String sex = resultSet.getString("sex");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                //将一行中零散的数据，封装在一个User对象里
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setSex(sex);
                user.setEmail(email);
                user.setAddress(address);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBPoolUtils.closeAll(connection,preparedStatement,resultSet);
        }
    }
}
