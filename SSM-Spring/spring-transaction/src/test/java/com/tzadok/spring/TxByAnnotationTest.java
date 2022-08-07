package com.tzadok.spring;

import com.tzadok.spring.controller.BookController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/7 14:52:06
 *
 * 声明式事务的配置步骤
 * 1、在spring的配置文件中配置事务管理器
 *     <!-- 配置事务管理器   -->
 *     <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
 *         <property name="dataSource" ref="dataSource"></property>
 *     </bean>
 *
 * 2、开启事务的注解驱动
 * <tx:annotation-driven transaction-manager="transactionManager"/>
 *
 * 在需要被事务管理的方法上，添加@Transactional注解，该方法就会被事务管理
 *
 * @Transactional注解标识的位置：
 * 1、标识在方法上
 * 2、标识在类上，则类中所有的方法都会被事务管理
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx-xml.xml")
public class TxByAnnotationTest {

    @Autowired
    public BookController bookController;

    @Test
    public void testBuyBook(){
        bookController.buyBook(1,1);
    }

    @Test
    public void testBuyBooks(){
        /**
         * 使用的结账的事务，一本买不了就结账失败
         */
        bookController.checkOut(1,new Integer[]{1,2});
    }
}
