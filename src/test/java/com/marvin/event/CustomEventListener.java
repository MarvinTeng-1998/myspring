package com.marvin.event;

import com.marvin.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 15:31
 **/
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到" + event.getSource() + "消息；事件：" + new Date());
        System.out.println("消息" + event.getId() + ":" + event.getMessage());
    }
}
