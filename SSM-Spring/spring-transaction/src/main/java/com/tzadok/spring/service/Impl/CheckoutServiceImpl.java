package com.tzadok.spring.service.Impl;

import com.tzadok.spring.service.BookService;
import com.tzadok.spring.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tzadok
 * @date 2022/8/7 15:42:59
 * @project SSM-Spring
 * @description:
 */
@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private BookService bookService;

    @Override
    //@Transactional
    public void checkout(Integer userId, Integer[] bookIds) {
        for (Integer bookId:bookIds) {
            bookService.buyBook(userId,bookId);
        }
    }
}
