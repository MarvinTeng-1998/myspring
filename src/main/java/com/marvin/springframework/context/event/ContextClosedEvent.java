package com.marvin.springframework.context.event;

import com.marvin.springframework.context.ApplicationContext;
import com.marvin.springframework.context.ApplicationEvent;

/**
 * @TODO: Context关闭Event
 * @author: dengbin
 * @create: 2023-07-04 14:37
 **/
public class ContextClosedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
