package com.marvin.springframework.context.event;

import com.marvin.springframework.context.ApplicationContext;
import com.marvin.springframework.context.ApplicationEvent;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 14:36
 **/
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /*
     * @Description: TODO 获取ApplicationContext
     * @Author: dengbin
     * @Date: 4/7/23 14:37

     * @return: com.marvin.springframework.context.ApplicationContext
     **/
    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
