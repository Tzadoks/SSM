package com.tzadok.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tzadok
 * @date 2022/8/8 15:33:14
 * @project SSM-SpringMVC
 * @description:
 */
@Controller
public class TestViewController {

    @RequestMapping("/test/view/thymeleaf")
    public String testThymeleafView(){
        return "success";
    }

    @RequestMapping("/test/view/forward")
    public String InternalResourceView(){
        return "forward:/test/model";
    }

    @RequestMapping("/test/view/rediect")
    public String RedirectView(){
        return "redirect:/test/model";
    }
}
