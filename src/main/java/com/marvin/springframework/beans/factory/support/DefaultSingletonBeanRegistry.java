package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.DisposableBean;
import com.marvin.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @TODO: 实现获取单例的方法
 * @author: dengbin
 * @create: 2023-06-27 22:43
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 需要一个单例的容器，来存放所有的单例对象，通常情况下，这个单例类只需要被取而不是被修改，所以这里用的是HashMap。
    private final Map<String,Object> singletonObjects = new HashMap<>();
    // 一个存放具备销毁方法的Bean的容器 来用来执行所有的DisposableBean方法。
    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    /*
     * @Description: TODO 从单例容器中获取一个Singleton对象
     * @Author: dengbin
     * @Date: 27/6/23 22:51
     * @param beanName: bean的名字
     * @return: java.lang.Object
     **/
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /*
     * @Description: TODO 单例容器注册singleton对象。
     * @Author: dengbin
     * @Date: 27/6/23 22:51
     * @param beanName:
     * @param singletonObject:
     * @return: void
     **/
    protected void addSingleton(String beanName,Object singletonObject){
        singletonObjects.put(beanName,singletonObject);
    }

    /*
     * @Description: TODO 将读取到的registerDisposableBean的DisposableBean存放到DisposableBean的容器中，在Spring关闭的时候需要执行这里的逻辑。
     * @Author: dengbin
     * @Date: 30/6/23 23:12
     * @param beanName:
     * @param bean:
     * @return: void
     **/
    public void registerDisposableBean(String beanName, DisposableBean bean){
        disposableBeanMap.put(beanName, bean);
    }

    @Override
    public void destroySingletons(){
        Set<String> keySet = disposableBeanMap.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0 ; i--) {
            Object beanName = disposableBeanNames[i];
            // 这里拿到的其实是DisposableAdapter,DisposableAdapter继承了DisposableBean
            DisposableBean disposableBean = disposableBeanMap.remove(beanName);
            try{
                disposableBean.destroy();
            }catch (Exception e){
                throw new BeansException("Destroy Method on bean with name' + " + beanName + "' threw an Exception");
            }
        }
    }
}
