package com.marvin.springframework.context.support;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-30 16:20
 **/
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    private String[] configLocations;


    public ClassPathXmlApplicationContext(){}
    public ClassPathXmlApplicationContext(String configLocation){
        configLocations = new String[]{configLocation};
        refresh();
    }
    public ClassPathXmlApplicationContext(String[] configLocations){
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
