package com.tzadok.spring.service.Impl;

import com.tzadok.spring.dao.BookDao;
import com.tzadok.spring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/7 14:34:42
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Override
/*    @Transactional(
            //readOnly = true
            // timeout = 3
            //noRollbackFor = ArithmeticException.class
            //isolation = Isolation.DEFAULT
            propagation = Propagation.REQUIRES_NEW
    )*/
    /**
     * readOnly属性：只读，在查询操作时才可以使用，
     * timeout属性：超时，超时回滚，释放资源
     *      org.springframework.transaction.TransactionTimedOutException: Transaction timed out: deadline was Sun Aug 07 15:18:46 CST 2022
     * noRollbackFor属性：因为什么异常不回滚
     * isolation属性：隔离属性
     * propagation属性：事务的传播行为
     */
    public void buyBook(Integer userId, Integer bookId) {
        //睡眠5秒
        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //查询图书价格
        Integer price = bookDao.getPriceByBookId(bookId);

        //更新图书的库存
        bookDao.updateStock(bookId);

        //更新用户的余额
        bookDao.updateBalance(userId,price);

        //System.out.println(1 / 0);
    }
}
