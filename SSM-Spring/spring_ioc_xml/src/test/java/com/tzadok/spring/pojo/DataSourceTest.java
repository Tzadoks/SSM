package com.tzadok.spring.pojo;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 15:31:31
 */
public class DataSourceTest {

    @Test
    public void testDataSource() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-datasource.xml");

        DruidDataSource dataSource = context.getBean(DruidDataSource.class);

        System.out.println(dataSource.getConnection());

    }
}
