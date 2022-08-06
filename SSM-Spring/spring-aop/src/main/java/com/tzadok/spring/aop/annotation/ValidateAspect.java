package com.tzadok.spring.aop.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/6 21:07:07
 */
@Component
@Aspect
@Order(1)
public class ValidateAspect {

//    @Before("execution(* com.tzadok.spring.aop.annotation.CalculatorImpl.*(..))")
    @Before("com.tzadok.spring.aop.annotation.LoggerAspect.pointCut()")
    public void beforeMethod(){
        System.out.println("ValidateAspect---->前置通知");
    }
}
