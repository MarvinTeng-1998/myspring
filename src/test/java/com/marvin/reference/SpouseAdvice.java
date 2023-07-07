package com.marvin.reference;

import com.marvin.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-07 22:20
 **/
public class SpouseAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("关怀小两口（切面）" + method);
    }
}
