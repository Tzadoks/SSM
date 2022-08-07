package com.tzadok.spring.service;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/7 14:34:22
 */
public interface BookService {

    /**
     * 买书
     * @param userId
     * @param bookId
     */
    void buyBook(Integer userId, Integer bookId);
}
