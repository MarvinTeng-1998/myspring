package com.marvin.springframework.aop;

import com.marvin.springframework.util.ClassUtils;

/**
 * @TODO: 包装了被代理类的相关信息：被代理类、被代理类实现的接口
 * @author: dengbin
 * @create: 2023-07-04 18:01
 **/
public class TargetSource {
    private final Object target;

    public TargetSource(Object target){
        this.target = target;
    }

    /*
     * @Description: TODO 返回被代理类的接口
     * @Author: dengbin
     * @Date: 4/7/23 18:03

     * @return: java.lang.Class<?>[]
     **/
    public Class<?>[] getTargetClass(){
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    /*
     * @Description: TODO 返回被代理类
     * @Author: dengbin
     * @Date: 4/7/23 18:03

     * @return: java.lang.Object
     **/
    public Object getTarget(){
        return this.target;
    }
}
