package com.marvin.springframework.context;

import com.marvin.springframework.beans.factory.HierarchicalBeanFactory;
import com.marvin.springframework.beans.factory.ListableBeanFactory;
import com.marvin.springframework.core.io.ResourceLoader;

/**
 * @TODO: 上下文对象的基础接口
 * @author: dengbin
 * @create: 2023-06-30 15:34
 **/
public interface ApplicationContext extends ListableBeanFactory , HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
