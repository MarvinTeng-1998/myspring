package com.marvin.aop;

import com.marvin.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-05 16:28
 **/
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法：" + method.getName());
    }
}
