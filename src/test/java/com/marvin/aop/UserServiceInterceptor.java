package com.marvin.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 18:38
 **/
public class UserServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            // 这里是执行被代理类的方法的逻辑。
            return invocation.proceed();
        } finally {
            System.out.println("监控 - Begin ByAOP");
            System.out.println("方法名称" + invocation.getMethod());
            System.out.println("方法耗时" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("监控 -End\r\n");
        }
    }
}
