package com.tzadok.spring.dao;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/7 14:35:06
 */
public interface BookDao {

    /**
     * 根据图书的id查询图书的价格
     * @param bookId
     * @return
     */
    Integer getPriceByBookId(Integer bookId);

    /**
     * 更新图书的库存
     * @param bookId
     */
    void updateStock(Integer bookId);

    /**
     * 更新用户的余额
     * @param userId
     * @param price
     */
    void updateBalance(Integer userId, Integer price);
}
