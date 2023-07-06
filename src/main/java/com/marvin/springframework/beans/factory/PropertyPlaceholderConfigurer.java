package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.marvin.springframework.core.io.DefaultResourceLoader;
import com.marvin.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @TODO: 处理占位符配置，并填充到PropertyValue的Value
 * @author: dengbin
 * @create: 2023-07-05 22:03
 **/
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {
    // 占位符的前缀
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    // 占位符的后缀
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    /*
     * @Description: TODO 读取properties文件中的信息，从而填充到Bean的属性中去。
     *                主要是通过BeanDefinition来进行实现：实现了BeanFactoryPostProcessor，来在Bean注册完成之后，通过修改BeanDefinition来解决。
     * @Author: dengbin
     * @Date: 5/7/23 22:13
     * @param beanFactory:
     * @return: void
     **/
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                // 遍历beanDefinition所有的PropertyValues，然后拿到是占位符属性值。
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) {
                        continue;
                    }
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    // BeanDefinition中的属性值确定后，需要使用properties中对应的value进行填充。
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int endIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
                        String propKey = strVal.substring(startIdx + 2, endIdx);
                        String propVal = properties.getProperty(propKey);
                        buffer.replace(startIdx, endIdx + 1, propVal);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                    }
                }
            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
