package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.DisposableBean;
import com.marvin.springframework.beans.factory.ObjectFactory;
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

    // 一级缓存容器：来存放所有单例对象，通常情况下，这个单例类只需要被取而不是被修改，所以这里用的是HashMap。
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    // 二级缓存容器：提前暴露对象，没有实例化完成的对象
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    // 三级缓存容器：存放代理对象
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

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
        Object singletonObject = singletonObjects.get(beanName);
        if (null == singletonObject) {
            singletonObject = earlySingletonObjects.get(beanName);
            // 判断二级缓存中是否有对象，这个对象就是代理对象，因为只有代理对象会放到三级缓存
            if (null == singletonObject) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (null != singletonFactory) {
                    // 如果代理工厂不是空的话，则从代理工厂中拿到这个对象
                    singletonObject = singletonFactory.getObjects();
                    // 拿到对象后，再把这个对象丢到半成品容器中
                    earlySingletonObjects.put(beanName, singletonObject);
                    // 从代理工厂中移除这个beanName
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    /*
     * @Description: TODO 添加singletonFactory，就是代理对象，同时删除掉半成品容器中这个bean
     * @Author: dengbin
     * @Date: 7/7/23 20:44
     * @param beanName:
     * @param singletonFactory:
     * @return: void
     **/
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        // 如果singletonObject中不包含这个bean的话，则在代理工厂中加入这个对象的对象工厂，并且从半成品容器中删除掉这个bean
        if(!this.singletonObjects.containsKey(beanName)){
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }
    /*
     * @Description: TODO 单例容器注册singleton对象。同时在半成品容器中删除beanName，再从singleton工厂中去除掉这个对象。
     * @Author: dengbin
     * @Date: 27/6/23 22:51
     * @param beanName:
     * @param singletonObject:
     * @return: void
     **/
    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    /*
     * @Description: TODO 将读取到的registerDisposableBean的DisposableBean存放到DisposableBean的容器中，在Spring关闭的时候需要执行这里的逻辑。
     * @Author: dengbin
     * @Date: 30/6/23 23:12
     * @param beanName:
     * @param bean:
     * @return: void
     **/
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeanMap.put(beanName, bean);
    }

    @Override
    public void destroySingletons() {
        Set<String> keySet = disposableBeanMap.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            // 这里拿到的其实是DisposableAdapter,DisposableAdapter继承了DisposableBean
            DisposableBean disposableBean = disposableBeanMap.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy Method on bean with name' + " + beanName + "' threw an Exception");
            }
        }
    }
}
