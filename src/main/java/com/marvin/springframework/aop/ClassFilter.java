package com.marvin.springframework.aop;

/**
 * @TODO:定义类匹配器，用于切点找到给定的接口和目标类
 * @author: dengbin
 * @create: 2023-07-04 17:33
 **/
public interface ClassFilter {

    /*
     * @Description: TODO 根据类的类型来匹配类。
     * @Author: dengbin
     * @Date: 4/7/23 17:33
     * @param clazz:
     * @return: boolean
     **/
    boolean matches(Class<?> clazz);
}
