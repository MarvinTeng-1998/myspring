package com.marvin.reference;

import com.marvin.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-07 22:26
 **/
public class Test_Circular {
    @Test
    public void test_circular() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-reference.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇:" + husband.queryWife());
        System.out.println("媳妇的老公:" + wife.queryHusband());
    }
}
