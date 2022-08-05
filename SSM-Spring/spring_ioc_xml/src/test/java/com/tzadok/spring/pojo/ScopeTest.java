package com.tzadok.spring.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 15:45:56
 */
public class ScopeTest {

    @Test
    public void testScope(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-scope.xml");

        Student student1 = context.getBean(Student.class);

        Student student2 = context.getBean(Student.class);

        System.out.println(student1.equals(student2));

        System.out.println(student1 == student2);
    }
}
