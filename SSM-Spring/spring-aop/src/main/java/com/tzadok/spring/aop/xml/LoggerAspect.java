package com.tzadok.spring.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class LoggerAspect{


    public void beforeAdviceMethod(JoinPoint joinPoint){

        //获取连接点对应的签名信息
        Signature signature = joinPoint.getSignature();
        //获取连接点所对应的方法的参数
        Object[] args = joinPoint.getArgs();

        System.out.println("LoggerAspect,前置通知,方法：" + signature.getName() + ",参数:" + Arrays.toString(args));
    }

    public void afterAdviceMethod(JoinPoint joinPoint){

        Signature signature = joinPoint.getSignature();

        System.out.println("LoggerAspect,后置通知,方法：" + signature.getName() + ",执行完毕");
    }

    /**
     * 在返回通知中若要获取目标对象方法的返回值，只需要通过@AfterReturning注解的returning属性，
     * 就可以将通知方法的某个参数指定为接收目标对象方法的返回值的参数
     * @param joinPoint
     * @param result
     */
    public void afterReturningAdviceMethod(JoinPoint joinPoint,Object result){

        Signature signature = joinPoint.getSignature();

        System.out.println("LoggerAspect,返回通知,方法：" + signature.getName() + ",结果:" + result);

    }

    /**
     * 在返回通知中若要获取目标对象方法的返回值，只需要通过@AfterThrowing注解的throwing属性，
     * 就可以将通知方法的某个参数指定为接收目标对象方法的异常的参数
     * @param joinPoint
     * @param ex
     */
    public void afterThrowingAdviceMethod(JoinPoint joinPoint,Throwable ex){

        Signature signature = joinPoint.getSignature();

        System.out.println("LoggerAspect,异常通知,方法：" + signature.getName() + ",异常:" + ex);

    }

    /**
     * 环绕通知
     */
    //环绕通知的方法的返回值一定要和目标对象方法的返回值一致
    public Object aroundAdviceMethod(ProceedingJoinPoint joinPoint){
        Object result = null;
        try {
            System.out.println("环绕通知-->前置通知");
            //表示目标对象的执行
            result = joinPoint.proceed();
            System.out.println("环绕通知-->返回通知");
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("环绕通知-->异常通知");
        } finally {
            System.out.println("环绕通知-->后置通知");
        }
        return result;
    }

}
