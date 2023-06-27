package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @TODO: 实现获取单例的方法
 * @author: dengbin
 * @create: 2023-06-27 22:43
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 需要一个单例的容器，来存放所有的单例对象，通常情况下，这个单例类只需要被取而不是被修改，所以这里用的是HashMap。
    private final Map<String,Object> singletonObjects = new HashMap<>();

    /*
     * @Description: TODO 从单例容器中获取一个Singleton对象
     * @Author: dengbin
     * @Date: 27/6/23 22:51
     * @param beanName: bean的名字
     * @return: java.lang.Object
     **/
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /*
     * @Description: TODO 单例容器注册singleton对象。
     * @Author: dengbin
     * @Date: 27/6/23 22:51
     * @param beanName:
     * @param singletonObject:
     * @return: void
     **/
    protected void addSingleton(String beanName,Object singletonObject){
        singletonObjects.put(beanName,singletonObject);
    }
}
