package com.jinxujtuan.System;

public class AccountTest {
    public static void main(String[] args) {
        AccountSystem acc = new AccountSystem();
        String cardid = "acc_001";
        String password = "123456789";
        String username = "张三";
        double count = 1000.0;
        String phone = "15145678925";

        //注册账户
        acc.register(cardid, password, username, count, phone);
        //存款
        //acc.saveMoney(cardid,password,20000);
        //取款
        //acc.takeMoney(cardid, password, 20000);
        //修改密码
        //acc.updatePassword(cardid, password, "123456789");
        //注销
        //acc.logout(cardid,password);

        String cardid2 = "acc_002";
        String password2 = "123456";
        String username2 = "李四";
        double count2 = 1000.0;
        String phone2 = "15935846666";

        //注册账户
        //acc.register(cardid2, password2, username2, count2, phone2);
        //acc.transfers2(cardid,password,500,"acc_002");
    }
}
