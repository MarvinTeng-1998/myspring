package com.marvin.springframework.aop.framework.autoproxy;

import com.marvin.springframework.aop.*;
import com.marvin.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.marvin.springframework.aop.framework.ProxyFactory;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.BeanFactoryAware;
import com.marvin.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.marvin.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.marvin.springframework.context.annotation.Component;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @TODO: 这个类是一个自动代理的类，用来生成自动代理对象。同时因为要用到BeanFactory，所以还继承了一个BeanFactory感知器。
 * @author: dengbin
 * @create: 2023-07-05 16:13
 **/
@Component("defaultAdvisorAutoProxyCreator")
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<Object>());

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    public boolean isInfrastructureClass(Class<?> beanClass){
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    /*
     * @Description: TODO 生成动态代理类的方法
     * @Author: dengbin
     * @Date: 5/7/23 17:47
     * @param beanClass:
     * @param beanName:
     * @return: java.lang.Object
     **/
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        return propertyValues;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        if (isInfrastructureClass(bean.getClass())) return bean;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true);

            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }

    @Override
    public Object getEarlyBeanFactory(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }
}
