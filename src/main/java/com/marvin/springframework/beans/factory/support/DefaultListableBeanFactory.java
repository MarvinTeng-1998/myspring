package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.marvin.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-27 23:24
 **/
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    // 用来存放BeanDefinition的容器
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /*
     * @Description: TODO 获取BeanDefinition
     * @Author: dengbin
     * @Date: 27/6/23 23:27
     * @param beanName:
     * @return: com.marvin.springframework.beans.factory.config.BeanDefinition
     **/
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }


    /*
     * @Description: TODO
     * @Author: dengbin
     * @Date: 30/6/23 15:58

     * @return: void
     **/
    @Override
    public void preInstantiateSingletons() throws BeansException {
        // beanDefinitionMap.keySet().forEach(this::getBean);
        for (String s : beanDefinitionMap.keySet()) {
            this.getBean(s);
        }
    }

    /*
     * @Description: TODO 将beanDefinition注册到beanDefinitionMap中。
     * @Author: dengbin
     * @Date: 27/6/23 23:27
     * @param beanName:
     * @param beanDefinition:
     * @return: void
     **/
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    /*
     * @Description: TODO 获取value为传入的class类型的Map
     * @Author: dengbin
     * @Date: 30/6/23 15:52
     * @param type:
     * @return: java.util.Map<java.lang.String,T>
     **/
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String ,T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if(type.isAssignableFrom(beanClass)){
                result.put(beanName,(T) getBean(beanName));
            }
        });
        return result;
    }


    /*
     * @Description: TODO ConfigurableListableBeanFactory中获取BeanDefinitions的方法在这里实现。
     * @Author: dengbin
     * @Date: 30/6/23 15:52

     * @return: java.lang.String[]
     **/
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
