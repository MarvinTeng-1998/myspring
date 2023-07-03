package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-03 18:10
 **/
public interface FactoryBean<T> {


    /*
     * @Description: TODO 获取FactoryBean创建的对象
     * @Author: dengbin
     * @Date: 3/7/23 18:11

     * @return: T
     **/
    T getObject() throws BeansException;

    /*
     * @Description: TODO 获取FactoryBean能创建的对象类型
     * @Author: dengbin
     * @Date: 3/7/23 18:11

     * @return: java.lang.Class<?>
     **/
    Class<?> getObjectType();

    /*
     * @Description: TODO 判断创建的对象是否是singleton的
     * @Author: dengbin
     * @Date: 3/7/23 18:12

     * @return: boolean
     **/
    boolean isSingleton();
}
