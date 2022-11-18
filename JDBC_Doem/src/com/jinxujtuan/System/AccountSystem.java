package com.jinxujtuan.System;

import java.sql.*;

/**
 * - 开户：使用PreparedStatement添加记录至t_account表
 * - 存款：根据卡号、 密码、 存储金额进行修改
 * - 取款：根据卡号、密码、 取款金额
 * - 转账：根据卡号、 密码、对方卡号、 转账金额进行修改
 * - 查看余额： 根据卡号、密码，查询余额
 * - 修改密码：根据卡号、密码，再输入新密码进行修改
 * - 注销：根据卡号、密码，删除对应的账户信息
 */
public class AccountSystem {
    private static Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    /*静态方法*/
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String URL = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf8";
            String User = "root";
            String Password = "123456";
            connection = DriverManager.getConnection(URL,User,Password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
    * 关闭数据库连接对象Connection
    * 注意：在程序结束时关闭，如果过早关闭的话，将与数据库断开连接
     * */
    public void closeConnection(){
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //开户
    public void register(String card_id , String password, String username,double balance, String phone){
        String sql = "insert into t_account(card_id, password, username,balance, phone) values (?, ?, ?, ?, ?)";
        try{
            //预编译
            preparedStatement = connection.prepareStatement(sql);
            //为占位符赋值
            preparedStatement.setString(1, card_id);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, username);
            preparedStatement.setDouble(4, balance);
            preparedStatement.setString(5, phone);
            int count = preparedStatement.executeUpdate();
            if(count > 0){
                System.out.println("开户成功！");
            }else{
                System.out.println("开户失败！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //存款
    public void saveMoney(String card_id , String password,double money){
        String sql = "update t_account set balance = balance + ? where card_id = ? and password = ?";

        if (money > 0) {
            try{
                //预编译
                preparedStatement = connection.prepareStatement(sql);
                //为占位符赋值
                preparedStatement.setDouble(1, money);
                preparedStatement.setString(2, card_id);
                preparedStatement.setString(3, password);
                int count = preparedStatement.executeUpdate();
                if(count > 0){
                    System.out.println("存款成功！！");
                }else{
                    System.out.println("卡号或密码错误！！");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("输入存储金额错误！");
        }
    }

    //取款
    public void takeMoney(String card_id , String password,double money){
        double balance = getBalance(card_id, password);

        if(balance == -1){
            System.out.println("获取余额错误");
            return;
        }

        if (money > 0 ) {
            String sql = "update t_account set balance = balance - ? where card_id = ? and password = ?";
            try{
                //预编译
                preparedStatement = connection.prepareStatement(sql);
                //为占位符赋值
                preparedStatement.setDouble(1, money);
                preparedStatement.setString(2, card_id);
                preparedStatement.setString(3, password);
                int count = preparedStatement.executeUpdate();
                if(count > 0){
                    System.out.println("取款成功！");
                }else{
                    System.out.println("卡号或密码错误！！");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("取款失败！");
        }
    }

    //查看余额
    public double getBalance(String card_id, String password){
        String sql = "select balance from t_account where card_id = ? and password = ?";
        double balance = -1;
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, card_id);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                balance = resultSet.getDouble("balance");
                System.out.println("卡内余额为：" + balance);
            }else{
                System.out.println("卡号或密码错误！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return balance;//返回金额
    }

    //修改密码
    public void updatePassword(String card_id, String password, String newPassword){
        String sql = "update t_account set password = ? where card_id = ? and password = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,card_id);
            preparedStatement.setString(3,password);
            int count = preparedStatement.executeUpdate();
            if(count > 0){
                System.out.println("密码修改成功！");
            }else{
                System.out.println("卡号或密码错误！请核对后再修改！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //注销
    public void logout(String card_id, String password){
        String sql = "delete from t_account where card_id = ? and password = ?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, card_id);
                preparedStatement.setString(2, password);
                int count = preparedStatement.executeUpdate();
                if(count > 0){
                    System.out.println("注销成功！");
                }else{
                    System.out.println("卡号或密码错误！");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    //转账
    public void transfers2(String card_id, String password, double money,String toCard_id){
        String srcACcount = "update t_account set balance = balance - ? where card_id = ?";
        String tarACcount = "update t_account set balance = balance + ? where card_id = ?";
        double balance = getBalance(card_id, password);
        if(balance == -1){
            System.out.println("获取余额错误");
            return;
        }
        if (money > 0 && money <= balance) {
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(srcACcount);
                preparedStatement.setDouble(1, money);
                preparedStatement.setString(2, card_id);
                int i = preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement(tarACcount);
                preparedStatement.setDouble(1, money);
                preparedStatement.setString(2, toCard_id);
                int i1 = preparedStatement.executeUpdate();
                if (i == 1 && i1 == 1) {
                    System.out.println("转账成功");
                } else {
                    System.out.println("请检查收款账户");
                    connection.rollback();
                }
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println(card_id + "：余额不足！");
        }
    }

}
