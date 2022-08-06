package com.tzadok.spring.proxy;

/**
 * @author Tzadok
 * @version 1.0
 * @project SSM-Spring
 * @date 2022/8/6 10:13:18
 */
public class CalculatorStaticProxy implements Calculator{

    // 将被代理的目标对象声明为成员变量
    private Calculator target;

    public CalculatorStaticProxy(Calculator target) {
        this.target = target;
    }

    @Override
    public int add(int i, int j) {
        int addResult = 0;
        try {
            // 附加功能由代理类中的代理方法来实现
            System.out.println("[日志] add 方法开始了，参数是：" + i + "," + j);

            addResult = target.add(i, j);

            // 通过目标对象来实现核心业务逻辑
            System.out.println("[日志] add 方法结束了，结果是：" + addResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return addResult;
    }

    @Override
    public int sub(int i, int j) {

        System.out.println("[日志] sub 方法开始了，参数是：" + i + "," + j);

        int subResult = target.sub(i, j);

        System.out.println("[日志] sub 方法结束了，结果是：" + subResult);

        return subResult;
    }

    @Override
    public int mul(int i, int j) {

        System.out.println("[日志] mul 方法开始了，参数是：" + i + "," + j);

        int mulResult = target.mul(i, j);

        System.out.println("[日志] mul 方法结束了，结果是：" + mulResult);

        return mulResult;
    }

    @Override
    public int div(int i, int j) {

        System.out.println("[日志] div 方法开始了，参数是：" + i + "," + j);

        int divResult = target.div(i, j);

        System.out.println("[日志] div 方法结束了，结果是：" + divResult);

        return divResult;
    }
}
