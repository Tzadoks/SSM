package com.tzadok.spring;

import com.tzadok.spring.pojo.User1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/7 10:02:49
 */
//指定当前测试类在Spring的测试环境中执行，此时就可以通过注入直接获取IOC容器的bean
@RunWith(SpringJUnit4ClassRunner.class)
//设置Spring测试环境的配置文件
@ContextConfiguration("classpath:spring-jdbc.xml")
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testInsert(){

        String sql = "insert into t_user values(null,?,?,?,?,?)";

        jdbcTemplate.update(sql,"cc","123456",23,"女","123@qq.com");

    }

    @Test
    public void testGetUserById(){

        String sql = "select * from t_user where id = ?";

        User1 user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User1.class), 1008);

        System.out.println(user1);
    }

    @Test
    public void testGetAllUser(){

        String sql = "select * from t_user";

        List<User1> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User1.class));

        list.forEach(System.out::println);
    }

    @Test
    public void testGetCount(){

        String sql = "select count(*) from t_user";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        System.out.println(count);
    }
}
