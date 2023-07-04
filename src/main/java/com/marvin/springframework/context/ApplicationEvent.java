package com.marvin.springframework.context;

import java.util.EventObject;

/**
 * @TODO: 具备事件功能的抽象类，后续所有事件都需要继承这个抽象类
 * @author: dengbin
 * @create: 2023-07-04 14:35
 **/
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
