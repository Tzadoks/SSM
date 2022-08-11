package com.tzadok.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Tzadok
 * @date 2022/8/10 15:53:15
 * @project SSM-SpringMVC
 * @description:
 */
//将当前类标识为异常处理的组件
@ControllerAdvice
public class ExceptionController {

    //设置要处理的异常信息
    @ExceptionHandler(ArithmeticException.class)
    public String handleException(Throwable ex,Model model){

        //ex标识控制器方法要处理的异常
        model.addAttribute("ex",ex);
        return "error";
    }
}
