package com.marvin.springframework.context.event;


import com.marvin.springframework.context.ApplicationEvent;
import com.marvin.springframework.context.ApplicationListener;

/**
 * @TODO: 事件广播器
 * @author: dengbin
 * @create: 2023-07-04 14:40
 **/
public interface ApplicationEventMulticaster {

    /*
     * @Description: TODO 添加事件监听器
     * @Author: dengbin
     * @Date: 4/7/23 14:41

     * @return: void
     **/
    void addApplicationListener(ApplicationListener<?> listener);

    /*
     * @Description: TODO 删除一个事件监听器
     * @Author: dengbin
     * @Date: 4/7/23 14:43
     * @param listener:
     * @return: void
     **/
    void removeApplicationListener(ApplicationListener<?> listener);

    /*
     * @Description: TODO 事件广播 这个方法来推送对应的事件消息。
     * @Author: dengbin
     * @Date: 4/7/23 14:44
     * @param event:
     * @return: void
     **/
    void multicastEvent(ApplicationEvent event);
}
