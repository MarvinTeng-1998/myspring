package com.marvin.dao;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.marvin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.marvin.springframework.beans.factory.config.BeanPostProcessor;
import com.marvin.springframework.context.annotation.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 16:17
 **/
@Component("userDao")
public class UserDao {
    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001","小傅哥，北京，亦庄");
        hashMap.put("10002","八杯水，上海，尖沙咀");
        hashMap.put("10003","阿毛，香港，铜锣湾");
    }

    public String queryName(String uId){
        return hashMap.get(uId);
    }
}
