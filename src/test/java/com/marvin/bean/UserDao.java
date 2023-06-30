package com.marvin.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-28 17:25
 **/
public class UserDao {
    private static Map<String, String> map = new HashMap<>();

    public void initDataMethod() {
        System.out.println("Bean初始化函数：数据注入成功");
        map.put("1001", "上海");
        map.put("1002", "北京");
        map.put("1003", "新加坡");
    }


    public String queryUsername(String uId) {
        return map.get(uId);
    }

    public void destroyDataMethod() {
        System.out.println("执行：destroy-method");
        map.clear();
        System.out.println("数据清空完毕");
    }
}
