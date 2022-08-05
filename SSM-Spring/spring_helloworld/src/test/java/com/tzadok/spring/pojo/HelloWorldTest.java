package com.tzadok.spring.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 09:37:57
 */
public class HelloWorldTest {

    @Test
    public void sayHello() {
        //获取容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

        //获取ioc容器中的bean对象
        HelloWorld bean = (HelloWorld) ioc.getBean("helloWorld");

        bean.sayHello();
    }
}