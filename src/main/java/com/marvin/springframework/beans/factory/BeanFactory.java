package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;

/**
 * @TODO: 这是BeanFactory的抽象工厂，定义一个获取Bean的方法
 * @author: dengbin
 * @create: 2023-06-27 22:36
 **/
public interface BeanFactory {

    /*
     * @Description: TODO 根据Bean的名字获取Bean的实例
     * @Author: dengbin
     * @Date: 27/6/23 22:37
     * @param beanName: Bean的名字
     * @return: java.lang.Object
     **/
    Object getBean(String beanName);

    /*
     * @Description: TODO 根据Bean的名字及对象实例化参数来构造Bean对象。
     * @Author: dengbin
     * @Date: 28/6/23 15:17
     * @param beanName:
     * @param args: 对象实例化参数
     * @return: java.lang.Object
     **/
    Object getBean(String beanName,Object... args);

    /*
     * @Description: TODO 根据Bean名字和Bean的类型来获取Bean
     * @Author: dengbin
     * @Date: 30/6/23 16:25
     * @param name:
     * @param requiredType:
     * @return: T
     **/
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;
}
