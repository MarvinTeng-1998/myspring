package com.marvin.springframework.beans.factory.config;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-27 22:39
 **/
public interface SingletonBeanRegistry {
    /*
     * @Description: TODO 根据beanName获取一个单例对象
     * @Author: dengbin
     * @Date: 27/6/23 22:40
     * @param beanName: bean在容器中的beanName
     * @return: java.lang.Object
     **/
    Object getSingleton(String beanName);
}
