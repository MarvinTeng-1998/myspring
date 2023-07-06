package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.marvin.springframework.core.io.DefaultResourceLoader;
import com.marvin.springframework.core.io.Resource;
import com.marvin.springframework.util.StringValueResolver;

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
            // 加载属性文件
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
                    StringBuilder buffer = resolvePlaceholder((String) value, properties);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                }
            }

            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(valueResolver);
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private static StringBuilder resolvePlaceholder(String value, Properties properties) {
        String strVal = value;
        StringBuilder buffer = new StringBuilder(strVal);
        // BeanDefinition中的属性值确定后，需要使用properties中对应的value进行填充。
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int endIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
            String propKey = strVal.substring(startIdx + 2, endIdx);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIdx, endIdx + 1, propVal);
        }
        return buffer;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties).toString();
        }
    }


    public void setLocation(String location) {
        this.location = location;
    }
}
