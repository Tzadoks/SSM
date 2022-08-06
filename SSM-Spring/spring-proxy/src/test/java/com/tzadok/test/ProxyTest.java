package com.tzadok.test;

import com.tzadok.spring.proxy.Calculator;
import com.tzadok.spring.proxy.CalculatorImpl;
import com.tzadok.spring.proxy.CalculatorStaticProxy;
import com.tzadok.spring.proxy.ProxyFactory;
import org.junit.Test;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/6 10:32:25
 */
public class ProxyTest {

    /**
     * 静态代理
     */
    @Test
    public void testProxy(){
        CalculatorStaticProxy proxy = new CalculatorStaticProxy(new CalculatorImpl());

        proxy.add(1,2);
    }

    /**
     * 动态代理有两种：
     * 1、jdk动态代理，要求必须有接口，最终生成的代理类在和目标实现相同的接口
     * 在com.sun.proxy包下，类型为$proxy2
     * 2、cglib动态代理，最终生成的代理类会继承目标类，并且和目标类在相同的包下
     *
     */
    @Test
    public void testProxyFactory(){
        ProxyFactory proxyFactory = new ProxyFactory(new CalculatorImpl());

        Calculator proxy = (Calculator) proxyFactory.getProxy();

        proxy.div(1,0);
    }
}