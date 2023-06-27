package com.marvin.springframework.beans.factory;

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
}
