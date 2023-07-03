package com.marvin.testNew;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-03 18:37
 **/
public class ProxyBeanFactory implements FactoryBean<IUserDao>{

    @Override
    public IUserDao getObject() throws BeansException {
        InvocationHandler handler = (proxy, method, args) -> {
            Map<String,String> hashMap = new HashMap<>();
            hashMap.put("10001","abc");
            hashMap.put("10002","efg");
            hashMap.put("10003","hij");

            return hashMap.get("10001");
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
