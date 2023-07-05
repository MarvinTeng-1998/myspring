package com.marvin.springframework.aop;


import java.lang.reflect.Method;

/**
 * @TODO: 方法前置通知接口
 * @author: dengbin
 * @create: 2023-07-05 15:57
 **/
public interface MethodBeforeAdvice extends BeforeAdvice {

    /*
     * @Description: TODO 前置环绕的方法，可以实现前置环绕的切面来进行AOP处理
     * @Author: dengbin
     * @Date: 5/7/23 15:57
     * @param method: 实现的方法
     * @param args: 被代理类实现的接口
     * @param target: 被代理类
     * @return: void
     **/
    void before(Method method, Object[] args, Object target) throws Throwable;
}

