package com.marvin.springframework.context.event;

import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.context.ApplicationEvent;
import com.marvin.springframework.context.ApplicationListener;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 15:13
 **/
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory){
        setBeanFactory(beanFactory);
    }
    /*
     * @Description: TODO 广播事件，监听器处理。
     * @Author: dengbin
     * @Date: 4/7/23 15:14
     * @param event:
     * @return: void
     **/
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for(final ApplicationListener listener : getApplicationListeners(event)){
            listener.onApplicationEvent(event);
        }
    }
}
