package com.marvin.springframework.aop;

import java.lang.reflect.Method;

/**
 * @TODO: 方法匹配，找到表达式范围内匹配下的目标类和方法
 * @author: dengbin
 * @create: 2023-07-04 17:34
 **/
public interface MethodMatcher {

    /*
     * @Description: TODO 根据方法，判断被代理类下是否有这个方法
     * @Author: dengbin
     * @Date: 4/7/23 17:34
     * @param method:
     * @param targetClass:
     * @return: boolean 如果有返回true，如果没有返回false
     **/
    boolean matches(Method method, Class<?> targetClass);
}
