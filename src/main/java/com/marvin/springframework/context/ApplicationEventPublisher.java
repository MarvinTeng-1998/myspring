package com.marvin.springframework.context;

/**
 * @TODO: 事件发布者
 * @author: dengbin
 * @create: 2023-07-04 15:08
 **/
public interface ApplicationEventPublisher {

    /*
     * @Description: TODO 发布事件
     * @Author: dengbin
     * @Date: 4/7/23 15:09
     * @param event:
     * @return: void
     **/
    void publishEvent(ApplicationEvent event);

}
