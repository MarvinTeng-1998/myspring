package com.marvin.springframework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @TODO: Bean对象的工厂，可以存放BeanDefinition到Map中以及获取。
 * @author: dengbin
 * @create: 2023-06-27 18:45
 **/
public class BeanFactory {
    // 用来存放bean对象的容器，key是beanName，value是BeanDefinition
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /*
     * @Description: TODO 用来获取Bean对象的方法，这里用来获取Bean实例。
     * @Author: dengbin
     * @Date: 27/6/23 18:50
     * @param beanName: Bean的名字
     * @return: java.lang.Object
     **/
    public Object getBean(String beanName){
        return beanDefinitionMap.get(beanName).getBean();
    }

    /*
     * @Description: TODO 用来将Bean的定义存到Map中的方法
     * @Author: dengbin
     * @Date: 27/6/23 18:51
     * @param beanName: Bean的名字
     * @param beanDefinition: Bean的定义，包含Bean的相关信息
     * @return: void
     **/
    public void registerBeanDefinition(String beanName,BeanDefinition beanDefinition){
        beanDefinitionMap.put(beanName,beanDefinition);
    }
}
