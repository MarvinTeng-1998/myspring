package com.marvin.event;

import com.marvin.springframework.context.ApplicationListener;
import com.marvin.springframework.context.event.ContextClosedEvent;

import java.util.Date;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 15:35
 **/
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("容器关闭事件触发并执行+" + new Date().toString());
    }
}
