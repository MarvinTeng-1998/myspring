package com.marvin.springframework.context.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.BeanFactoryAware;
import com.marvin.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.marvin.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.marvin.springframework.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 15:53
 **/
@Component
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        // 1.处理注释@Value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 2. 设置Autowired
        for(Field field : declaredFields){
            Autowired autowired = field.getAnnotation(Autowired.class);
            if(null != autowired){
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if(null != qualifier){
                    dependentBeanName = qualifier.value();
                    dependentBean = beanFactory.getBean(dependentBeanName,fieldType);
                }else{
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return propertyValues;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }


}
