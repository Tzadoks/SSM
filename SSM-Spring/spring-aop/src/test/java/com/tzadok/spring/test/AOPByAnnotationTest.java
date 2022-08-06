package com.tzadok.spring.test;

import com.tzadok.spring.aop.annotation.Calculator;
import com.tzadok.spring.aop.annotation.LoggerAspect;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/6 14:42:02
 */
public class AOPByAnnotationTest {

    @Test
    public void testAOPByAnnotation(){
        ApplicationContext context = new ClassPathXmlApplicationContext("aop-annotation.xml");

        Calculator bean = context.getBean(Calculator.class);

        bean.div(10,1);


    }
}
