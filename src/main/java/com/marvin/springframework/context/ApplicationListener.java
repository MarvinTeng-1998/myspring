package com.marvin.springframework.context;

import java.util.EventListener;

/**
 * @TODO: 事件监听器，用来监听事件。
 * @author: dengbin
 * @create: 2023-07-04 14:42
 **/
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /*
     * @Description: TODO 处理一个应用事件。
     * @Author: dengbin
     * @Date: 4/7/23 14:43
     * @param event:
     * @return: void
     **/
    void onApplicationEvent(E event);
}
