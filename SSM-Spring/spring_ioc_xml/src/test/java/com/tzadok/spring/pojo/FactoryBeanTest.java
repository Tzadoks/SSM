package com.tzadok.spring.pojo;

import org.junit.Test;
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 16:46:27
 */
public class FactoryBeanTest {

    @Test
    public void testFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-factory.xml");

        User user = context.getBean(User.class);

        System.out.println(user);
    }
}