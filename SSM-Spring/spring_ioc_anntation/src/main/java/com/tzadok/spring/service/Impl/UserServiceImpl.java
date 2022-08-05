package com.tzadok.spring.service.Impl;

import com.tzadok.spring.dao.UserDao;
import com.tzadok.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/5 20:22:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
//    @Qualifier("userDaoImpl")
    private UserDao userDao;
    @Override
    public void saveUser() {
        userDao.saveUser();
    }
}
