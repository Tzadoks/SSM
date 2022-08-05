package com.tzadok.spring.dao.Impl;

import com.tzadok.spring.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 20:23:06
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("保存成功");
    }
}
