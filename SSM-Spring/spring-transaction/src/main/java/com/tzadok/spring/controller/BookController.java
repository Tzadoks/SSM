package com.tzadok.spring.controller;

import com.tzadok.spring.service.BookService;
import com.tzadok.spring.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/7 14:33:59
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CheckoutService checkoutService;

    public void buyBook(Integer userId,Integer bookId){

        bookService.buyBook(userId,bookId);
    }

    public void checkOut(Integer userId,Integer[] bookIds){
        checkoutService.checkout(userId,bookIds);
    }
}
