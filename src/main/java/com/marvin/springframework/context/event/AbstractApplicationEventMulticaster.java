package com.marvin.springframework.context.event;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.BeanFactoryAware;
import com.marvin.springframework.context.ApplicationEvent;
import com.marvin.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @TODO: 抽象的事件广播器
 * @author: dengbin
 * @create: 2023-07-04 14:45
 **/
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster , BeanFactoryAware {
    // 一个存放事件监听器的容器
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    // 使用Aware拿到的BeanFactory容器
    private BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    /*
     * @Description: TODO 返回某个事件的所有监听器
     * @Author: dengbin
     * @Date: 4/7/23 15:06
     * @param event:
     * @return: java.util.Collection<com.marvin.springframework.context.ApplicationListener>
     **/
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event){
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for(ApplicationListener<ApplicationEvent> listener : applicationListeners){
            if(supportsEvent(listener,event)){
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /*
     * @Description: TODO 判断容器中的这个监听器是否支持监听这个事件。
     * @Author: dengbin
     * @Date: 4/7/23 15:06
     * @param listener: 容器中的监听器
     * @param event: 事件类型
     * @return: boolean
     **/
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event){
        Class<? extends ApplicationListener> listenerClass = listener.getClass();

        // 按照CglibSubclassingInstantiatingStrategy\SimpleInstantiatingStrategy实例化对象
        Class<?> targetClass = listenerClass;
        // 拿到监听器实现的接口，得到ApplicationListener<ApplicationEvent>
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        // 获取接口的泛型参数，根据泛型参数获取到实际监听的Event，获得里面的ApplicationEvent
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];

        // 获取到实际上监听的事件名称
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name:" + className);
        }
        // 实际上监听的事件是不是要比较的事件的父类。
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
