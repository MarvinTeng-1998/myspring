package com.marvin.springframework.context.event;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 14:38
 **/
public class ContextRefreshEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshEvent(Object source) {
        super(source);
    }
}
