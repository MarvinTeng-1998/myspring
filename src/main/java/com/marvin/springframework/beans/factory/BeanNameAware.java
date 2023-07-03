package com.marvin.springframework.beans.factory;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-03 16:32
 **/
public interface BeanNameAware extends Aware{

    /*
     * @Description: TODO 一个感知到所属的BeanName
     * @Author: dengbin
     * @Date: 3/7/23 16:32
     * @param name:
     * @return: void
     **/
    void setBeanName(String name);
}
