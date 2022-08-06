package com.tzadok.spring.test;

import com.tzadok.spring.aop.xml.Calculator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/6 21:31:12
 */
public class AOPByXMLTest {

    @Test
    public void testAOPByXML(){
        ApplicationContext context = new ClassPathXmlApplicationContext("aop-xml.xml");

        Calculator calculator = context.getBean(Calculator.class);

        calculator.add(10,1);
    }
}
