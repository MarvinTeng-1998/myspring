package com.marvin.springframework.context;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.Aware;

/**
 * @TODO: 一个所属的ApplicationContextAware的感知器
 * @author: dengbin
 * @create: 2023-07-03 16:33
 **/
public interface ApplicationContextAware extends Aware {

    /*
     * @Description: TODO 感知所属的ApplicationContext
     * @Author: dengbin
     * @Date: 3/7/23 16:34
     * @param applicationContext:
     * @return: void
     **/
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
