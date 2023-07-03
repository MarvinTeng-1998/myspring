package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;

/**
 * @TODO: 一个用来感知BeanFactory容器的感知类
 * @author: dengbin
 * @create: 2023-07-03 15:59
 **/
public interface BeanFactoryAware extends Aware {

    /*
     * @Description: TODO 设置BeanFactory，感知到这次Spring服务启动的BeanFactory
     * @Author: dengbin
     * @Date: 3/7/23 16:00
     * @param beanFactory:
     * @return: void
     **/
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
