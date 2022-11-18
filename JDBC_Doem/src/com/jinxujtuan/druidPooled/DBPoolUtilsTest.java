package com.jinxujtuan.druidPooled;

import java.sql.Connection;

public class DBPoolUtilsTest {
    public static void main(String[] args){
        for (int i = 0; i < 32; i++) {
            Connection connection = DBPoolUtils.getConnection();
            System.out.println(i + "-" + connection);
        }
    }


}
