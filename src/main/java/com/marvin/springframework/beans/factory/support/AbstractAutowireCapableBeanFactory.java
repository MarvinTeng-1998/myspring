package com.marvin.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.*;
import com.marvin.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @TODO: 抽象方法，主要实现createBean的方法。
 * @author: dengbin
 * @create: 2023-06-27 23:17
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    // 创建策略，关于实例化Bean对象的策略：JDK动态代理还是Cglib动态代理
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();


    /*
     * @Description: TODO 给创建好的Bean注入PropertyValue
     * @Author: dengbin
     * @Date: 28/6/23 17:17
     * @param beanName:
     * @param bean:
     * @param beanDefinition:
     * @return: void
     **/
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 这里表示如果这个属性是BeanReference 也就是是一个引用数据类型的话，我们需要去从Spring容器中找到这个引用数据类型。然后注入进去。
                if (value instanceof BeanReference) {
                    value = getBean(((BeanReference) value).getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (BeansException e) {
            throw new BeansException("Error setting property values: " + beanName);
        }
    }

    /*
     * @Description: TODO 创建Bean的方法，当Bean有参数需要入参时。
     * @Author: dengbin
     * @Date: 28/6/23 15:20
     * @param beanName:
     * @param beanDefinition:
     * @param args:  Bean的入参对象
     * @return: java.lang.Object
     **/
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        try {
            // // 判断是否返回代理 Bean 对象
            // bean = resolveBeforeInstantiation(beanName, beanDefinition);
            // if (null != bean) {
            //     return bean;
            // }
            // 实例化Bean
            bean = createBeanInstance(beanDefinition, beanName, args);
            // 实例化后判断
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName,bean);
            if(!continueWithPropertyPopulation){
                return bean;
            }
            // 在设置Bean属性之前，允许BeanPostProcessor 修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            // 这里是主要做了一个属性注入。
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行Bean的初始化方法和BeanPostProcessor的前置和后置方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        if(beanDefinition.isSingleton()){
            addSingleton(beanName, bean);
        }
        return bean;
    }

    /*
     * @Description: TODO 实例化后对于返回false的对象，不再执行后续设置Bean对象属性的操作。
     * @Author: dengbin
     * @Date: 6/7/23 17:51
     * @param beanName:
     * @param bean:
     * @return: boolean
     **/
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()){
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean,beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    /*
     * @Description: TODO 在设置属性前 先调用BeanPostProcessor来对Autowired和Value的属性赋值。
     * @Author: dengbin
     * @Date: 6/7/23 16:16
     * @param beanName:
     * @param bean:
     * @param beanDefinition:
     * @return: void
     **/
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()){
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(),bean,beanName);
                // if(null != propertyValues){
                //     for(PropertyValue propertyValue : propertyValues.getPropertyValues()){
                //         beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                //     }
                // }
            }
        }
    }

    /*
     * @Description: TODO 判断Bean是否应该返回的是代理对象，如果是则直接用代理模式返回即可
     * @Author: dengbin
     * @Date: 5/7/23 17:23
     * @param beanName:
     * @param beanDefinition:
     * @return: java.lang.Object
     **/
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition){
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if(null != bean){
            bean = applyBeanPostProcessorsAfterInitialization(bean,beanName);
        }
        return bean;
    }

    /*
     * @Description: TODO 调用代理模式的BeanPostProcessor
     * @Author: dengbin
     * @Date: 5/7/23 17:25
     * @param beanClass:
     * @param beanName:
     * @return: java.lang.Object
     **/
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName){
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) {
                    return result;
                }
            }
        }
        return null;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructor = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, beanName, constructor, args);
    }

    /*
     * @Description:  TODO 初始化Bean的方法
     * @Author: dengbin
     * @Date: 30/6/23 16:40
     * @param beanName:
     * @param bean:
     * @param beanDefinition:
     * @return: java.lang.Object
     **/
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // invokeAwareMethods
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        if(bean instanceof BeanClassLoaderAware){
            ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
        }
        if(bean instanceof BeanNameAware){
            ((BeanNameAware) bean).setBeanName(beanName);
        }
        // 1. 执行BeanPostProcessor Before处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        // 待完成，invokeInitMethods(beanName,wrappedBean,beanDefinition
        invokeInitMethods(beanName, wrappedBean, beanDefinition);
        // 2. 执行BeanPostProcessor After处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    /*
     * @Description: TODO 调用Bean初始化方法
     * @Author: dengbin
     * @Date: 30/6/23 22:03
     * @param beanName:
     * @param wrappedBean:
     * @param beanDefinition:
     * @return: void
     **/
    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find the init method named + '" + initMethodName + "' on bean with name " + beanName);
            }
            initMethod.invoke(wrappedBean);
        }
    }

    /*
     * @Description: TODO 执行BeanPostProcessor
     * @Author: dengbin
     * @Date: 30/6/23 16:49
     * @param existingBean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /*
     * @Description: TODO 执行BeanPostProcessor
     * @Author: dengbin
     * @Date: 30/6/23 16:50
     * @param existingBean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if(!beanDefinition.isSingleton()) return;
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }


}
