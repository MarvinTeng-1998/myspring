package com.marvin.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.DisposableBean;
import com.marvin.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @TODO: 销毁方法适配器
 * @author: dengbin
 * @create: 2023-06-30 22:06
 **/
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {

        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean) && "destroyDataMethod".equals(this.destroyMethodName)) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method '" + destroyMethodName + "'on bean with name '" + beanName);
            }
            destroyMethod.invoke(bean);

        }
    }
}
