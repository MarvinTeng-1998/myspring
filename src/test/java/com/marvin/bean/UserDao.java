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

    static {
        map.put("1001", "上海");
        map.put("1002", "北京");
        map.put("1003", "新加坡");
    }

    public String queryUsername(String uId) {
        return map.get(uId);
    }
}
