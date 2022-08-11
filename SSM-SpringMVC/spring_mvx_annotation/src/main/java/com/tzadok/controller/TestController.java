package com.tzadok.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tzadok
 * @date 2022/8/10 16:26:30
 * @project SSM-SpringMVC
 * @description:
 */
@Controller
public class TestController {

    @RequestMapping("/hello")
    public String testHello(){
        System.out.println(1/0);
        return "success";
    }
}
