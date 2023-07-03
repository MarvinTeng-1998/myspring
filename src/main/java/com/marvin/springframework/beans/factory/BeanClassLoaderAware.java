package com.marvin.springframework.beans.factory;

/**
 * @TODO: 一个BeanClassLoader的感知器
 * @author: dengbin
 * @create: 2023-07-03 16:31
 **/
public interface BeanClassLoaderAware extends Aware{

    /*
     * @Description: TODO 感知到所属的ClassLoader
     * @Author: dengbin
     * @Date: 3/7/23 16:32
     * @param classLoader:
     * @return: void
     **/
    void setBeanClassLoader(ClassLoader classLoader);
}
