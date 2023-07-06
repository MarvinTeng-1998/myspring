package com.marvin.springframework.aop.framework.adapter;

import com.marvin.springframework.aop.MethodBeforeAdvice;
import com.marvin.springframework.context.annotation.Component;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-05 16:32
 **/
@Component
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(){}

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
