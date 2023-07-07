package com.marvin.reference;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.FactoryBean;
import com.marvin.springframework.beans.factory.ObjectFactory;

import java.lang.reflect.Proxy;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-07 20:55
 **/
public class HusbandMother implements FactoryBean<IMother> {

    @Override
    public IMother getObject() {
        return (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IMother.class}, (proxy, method, args) -> "婚后媳妇妈妈的职责被婆婆代理了！" + method.getName());
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
