package com.tzadok.spring.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/6 14:26:38
 * 1、在切面中，需要通过指定的注解将方法标识为通知方法
 * @Before：标识前置通知的注解，在目标对象方法执行之前执行
 * @After:后置通知，在目标对象方法的finally字句中执行的
 * @AfterReturning:返回通知，在目标对象方法返回值之后执行
 * @AfterThrowing:异常通知，在目标对象方法的catch字句中执行
 *
 * 2、切入点表达式：设置在标识通知的注解的value属性中
 * @Before("execution(public int com.tzadok.spring.aop.annotation.CalculatorImpl.add(int ,int ))")
 * @Before("execution(* com.tzadok.spring.aop.annotation.CalculatorImpl.*(..))")
 *
 * 第一个 * 标识任意的访问修饰符和返回值类型
 * 第二个 * 表示类中任意的方法
 * .. 表示任意的参数列表
 * 类的地方使用*，表示包下所有的类
 *
 * 3、重用切入点表达式
 *     //@Pointcut声明一个公共的切入点表达式
 *     @Pointcut("execution(* com.tzadok.spring.aop.annotation.CalculatorImpl.*(..))")
 *     public void pointCut(){
 *         //公共的切入点表达式
 *     }
 * 使用方式：@Before("pointCut()")、@After("pointCut()")
 *
 * 4、获取连接点的信息
 * 在通知方法的参数位置，设置JoinPoint类型的参数，就可以获取连接点所对应方法的信息
 *
 * 5、切面的优先级
 * 可以通过@Order注解value属性设置优先级，默认值Integer的最大值
 * @Order注解value属性值越小，优先级越高
 *
 */
@Component
@Aspect //将当前组件标识为切面组件
public class LoggerAspect{

    @Pointcut("execution(* com.tzadok.spring.aop.annotation.CalculatorImpl.*(..))")
    public void pointCut(){
        //公共的切入点表达式
    }

    //@Before("execution(public int com.tzadok.spring.aop.annotation.CalculatorImpl.add(int ,int ))")
    @Before("pointCut()")
    public void beforeAdviceMethod(JoinPoint joinPoint){

        //获取连接点对应的签名信息
        Signature signature = joinPoint.getSignature();
        //获取连接点所对应的方法的参数
        Object[] args = joinPoint.getArgs();

        System.out.println("LoggerAspect,前置通知,方法：" + signature.getName() + ",参数:" + Arrays.toString(args));
    }


    @After("pointCut()")
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
    @AfterReturning(value = "pointCut()",returning = "result")
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
    @AfterThrowing(value = "pointCut()",throwing = "ex")
    public void afterThrowingAdviceMethod(JoinPoint joinPoint,Throwable ex){

        Signature signature = joinPoint.getSignature();

        System.out.println("LoggerAspect,异常通知,方法：" + signature.getName() + ",异常:" + ex);

    }

    /**
     * 环绕通知
     */
    @Around("pointCut()")
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
