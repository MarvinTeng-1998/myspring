package com.marvin.springframework.aop.framework;

import com.marvin.springframework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @TODO: JDK代理模式的方式实现AOP
 * @author: dengbin
 * @create: 2023-07-04 18:06
 **/
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    // 一个包装了所有切点相关信息的类：目标对象、方法拦截器、方法匹配器
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /*
     * @Description: TODO 使用JDK代理创建代理类
     * @Author: dengbin
     * @Date: 4/7/23 18:18

     * @return: java.lang.Object
     **/
    @Override
    public Object getProxy() {

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), // 类加载器
                advised.getTargetSource().getTargetClass(), // 被代理对象实现的接口
                this); // InvocationHandler，用来处理invoke方法
    }

    /*
     * @Description: TODO 调用目标方法
     * @Author: dengbin
     * @Date: 4/7/23 18:19
     * @param proxy:
     * @param method:
     * @param args:
     * @return: java.lang.Object
     **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 首先判断这个方法是否是在切入点定义范围内的方法和类，如果是的话则调用methodInterceptor的invoke方法
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            // invoke方法的参数要传入MethodInvocation，包含了被代理对象、执行的方法和方法的参数。
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }
}
