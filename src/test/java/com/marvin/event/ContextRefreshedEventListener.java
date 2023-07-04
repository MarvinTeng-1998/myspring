package com.marvin.event;

import com.marvin.springframework.context.ApplicationListener;
import com.marvin.springframework.context.event.ContextRefreshEvent;

import java.util.Date;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 15:34
 **/
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshEvent event) {
        System.out.println("容器刷新事件监听到并处理和触发" + new Date().toString());
    }
}
