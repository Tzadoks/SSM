package com.tzadok.spring.service;

/**
 * @author Tzadok
 * @date 2022/8/7 15:42:44
 * @project SSM-Spring
 * @description:
 */
public interface CheckoutService {

    /**
     * 结账
     * @param userId
     * @param bookIds
     */
    void checkout(Integer userId, Integer[] bookIds);
}
