package com.tzadok.spring_mvc.controller;

import com.tzadok.spring_mvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Tzadok
 * @date 2022/8/10 20:34:43
 * @project SSM-SpringMVC
 * @description:
 */
@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;
}
