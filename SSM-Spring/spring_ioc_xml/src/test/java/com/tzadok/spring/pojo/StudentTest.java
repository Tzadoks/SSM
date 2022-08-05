package com.tzadok.spring.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 09:48:14
 */
public class StudentTest {

    /**
     * 获取bean的三种方式：
     * 1、根据bean的id获取
     * 2、根据bean的类型获取
     *   注意：根据类型获取bean时，要求IOC容器中有且只有一个类型匹配的bean
     *   若没有任何一个类型匹配的bean，此时抛出异常：org.springframework.beans.factory.NoSuchBeanDefinitionException
     *   若存在多个类型匹配的bean，此时抛出异常：org.springframework.beans.factory.NoUniqueBeanDefinitionException
     * 3、根据bean的id和类型获取
     * 结论：
     * 根据类型来获取bean时，在满足bean唯一性的前提下，其实只是看：『对象 instanceof 指定的类型』的返回结果，
     * 只要返回的是true就可以认定为和类型匹配，能够获取到。
     * 即通过bean的类型、bean所继承的类的类型、bean所实现的接口的类型都可以获取bean
     */

    @Test
    public void testIOC(){
        //获取容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

        //获取bean
        //根据id获取
//        Student bean = (Student) ioc.getBean("studentOne");
        //根据类型获取
//        Student bean = ioc.getBean(Student.class);
        //根据类型和id获取
//        Student bean = ioc.getBean("studentOne", Student.class);
        //根据接口获取bean
        Person bean = ioc.getBean(Person.class);

        System.out.println(bean);
    }

    @Test
    public void testDI(){
        //获取容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

        //根据类型和id获取
        Student bean = ioc.getBean("studentSix", Student.class);

        System.out.println(bean);

//        Clazz bean = ioc.getBean("clazzInner", Clazz.class);
//
//        System.out.println(bean);
    }

    @Test
    public void testClazz(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Clazz bean = context.getBean("clazzOne", Clazz.class);

        System.out.println(bean);
    }


}