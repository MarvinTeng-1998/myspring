package com.marvin.springframework.context;

import com.marvin.springframework.beans.BeansException;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-30 15:36
 **/
public interface ConfigurableApplicationContext extends ApplicationContext{

    /*
     * @Description: TODO ApplicationContext的第一步，刷新容器。
     * @Author: dengbin
     * @Date: 30/6/23 15:36

     * @return: void
     **/
    void refresh() throws BeansException;
}
