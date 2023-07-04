package com.marvin.springframework.aop;

/**
 * @TODO: 切面 切入点
 * @author: dengbin
 * @create: 2023-07-04 17:32
 **/
public interface Pointcut {

    /*
     * @Description: TODO 获取类匹配器
     * @Author: dengbin
     * @Date: 4/7/23 17:43

     * @return: com.marvin.springframework.aop.ClassFilter
     **/
    ClassFilter getClassFilter();

    /*
     * @Description: TODO 获取方法匹配器
     * @Author: dengbin
     * @Date: 4/7/23 17:43

     * @return: com.marvin.springframework.aop.MethodMatcher
     **/
    MethodMatcher getMethodMatcher();
}
