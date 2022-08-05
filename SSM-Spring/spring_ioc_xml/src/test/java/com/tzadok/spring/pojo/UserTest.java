package com.tzadok.spring.pojo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 16:01:16
 */
public class UserTest {

    /**
     * bean的生命周期
     * 1、实例化
     * 2、依赖注入
     * 3、后置处理器的postProcessBeforeInitialization
     * 4、初始化,需要通过bean的init-method属性指定初始化的方法
     * 5、后置处理器的postProcessAfterInitialization
     * 6、IOC容器关闭时销毁，需要通过bean的destroy-method属性指定销毁的方法
     *
     * bean的后置处理器会在生命周期的初始化前后添加额外的操作，需要实现BeanPostProcessor接口，且配置到IOC容器中，
     * 需要注意的是，bean后置处理器不是单独针对某一个bean生效，而是针对IOC容器中所有bean都会执行
     *
     * 注意：
     * 若bean的作用域为单例时，生命周期的前三个步骤会在获取IOC容器时执行
     * 若bean的作用域为多例时，生命周期的前三个步骤不会在获取IOC容器时执行，会在获取bean时执行
     */

    /**
     * ApplicationContext中并没有提供关闭容器的方法
     * ConfigurableApplicationContext中提供了关闭容器的方法
     *
     * ConfigurableApplicationContext是ApplicationContext的子接口，其中扩展了刷新和关闭容器的方法
     */

    @Test
    public void testUser(){

        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-lifecycle.xml");

        User user = context.getBean(User.class);

        System.out.println(user);

        context.close();
    }
}
