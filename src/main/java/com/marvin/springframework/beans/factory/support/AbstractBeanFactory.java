package com.marvin.springframework.beans.factory.support;

import cn.hutool.core.util.ClassLoaderUtil;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.FactoryBean;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.BeanPostProcessor;
import com.marvin.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.marvin.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @TODO: Bean对象的工厂，可以存放BeanDefinition到Map中以及获取。
 * @author: dengbin
 * @create: 2023-06-27 18:45
 **/
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    // 一个放着BeanPostProcessor的容器
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    // 一个存放BeanClassLoader的容器
    private ClassLoader beanClassLoader = ClassLoaderUtil.getClassLoader();

    // 一个存放StringResolver的容器
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    /*
     * @Description: TODO 添加BeanPostProcessor到容器中去
     * @Author: dengbin
     * @Date: 30/6/23 16:44
     * @param beanPostProcessor:
     * @return: void
     **/
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /*
     * @Description: TODO 获取BeanPostProcessors的容器
     * @Author: dengbin
     * @Date: 30/6/23 16:46

     * @return: java.util.List<com.marvin.springframework.beans.factory.config.BeanPostProcessor>
     **/
    public List<BeanPostProcessor> getBeanPostProcessors(){
        return beanPostProcessors;
    }

    /*
     * @Description: TODO 用来获取Bean对象的方法，这里用来获取Bean实例。
     * @Author: dengbin
     * @Date: 27/6/23 18:50
     * @param beanName: Bean的名字
     * @return: java.lang.Object
     **/
    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName,null);
    }

    /*
     * @Description: TODO 对具有参数的Bean进行构造
     * @Author: dengbin
     * @Date: 28/6/23 15:18
     * @param beanName:
     * @param args:
     * @return: java.lang.Object
     **/
    @Override
    public Object getBean(String beanName, Object... args) {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return (T) getBean(beanName);
    }

    private <T> T doGetBean(final String beanName, final Object[] args) {
        Object singleton = getSingleton(beanName);
        if(singleton != null){
            // 需要去判断这个singleton是不是FactoryBean，实际上我们需要返回的是这个FactoryBean给getObject出来的对象，不一定是这个FactoryBean本身。
            return (T) getObjectForBeanInstance(singleton, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName,beanDefinition,args);
        // 这里会进行判断这里的Bean是否是一个FactoryBean，如果是的话则去用代理模式实例化FactoryBean中的对象，并且放在CacheObject中。
        return (T) getObjectForBeanInstance(bean,beanName);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName){
        // 判断获得的这个Bean是不是FactoryBean，如果是则需要去调用FactoryBean的getObject方法
        if(!(beanInstance instanceof FactoryBean)){
            return beanInstance;
        }
        // 这里是去拿到在FactoryBean缓存中的Bean对象。
        Object object = getCacheObjectForFactoryBean(beanName);
        if(object == null){
            // 这里是FactoryBean缓存中没有这个Bean对象，然后需要去重新getObject
            FactoryBean factoryBean = (FactoryBean) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
     }
    /*
     * @Description: TODO 主要是获取BeanDefinition，也就是说我们把BeanDefinition的容器放到抽象工厂的子类下去了，使用了模版模式。
     * @Author: dengbin
     * @Date: 27/6/23 23:05
     * @param beanName: Bean在容器中的名字
     * @return: com.marvin.springframework.beans.factory.config.BeanDefinition
     **/
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;


    /*
     * @Description: TODO 使用Bean的带参构造器去实例化一个Bean对象 的抽象方法。具体由AbstractAutowireBeanFactory实现。
     * @Author: dengbin
     * @Date: 28/6/23 15:22
     * @param beanName:
     * @param beanDefinition:
     * @param args:
     * @return: java.lang.Object
     **/
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition,Object[] args);


    public ClassLoader getBeanClassLoader(){
        return this.beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for(StringValueResolver resolver : this.embeddedValueResolvers){
            result = resolver.resolveStringValue(result);
        }
        return result;
    }
}
