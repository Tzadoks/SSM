package com.tzadok.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tzadok
 * @date 2022/8/10 10:11:31
 * @project SSM-SpringMVC
 * @description:
 */
@Controller
public class TestController {

    @RequestMapping("/test/hello")
    public String testHello(){
        System.out.println(1/0);
        return "success";
    }
}
