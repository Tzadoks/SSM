package com.tzadok.controller;

import com.tzadok.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Tzadok
 * @date 2022/8/9 11:11:47
 * @project SSM-SpringMVC
 * @description:
 * 1、@RequestBody:将请求体的内容和控制器方法的形参进行绑定
 * 2、使用@RequestBody注解将json格式的请求参数转换成为java对象
 * a>导入jackjson的依赖
 * b>在SpringMVC的配置文件中设置<mvc:annotation-driven/>
 * c>在处理请求的控制器方法的形参位置，直接设置json格式的请求参数要转换的java类型的形参，使用@RequestBody注解表明即可
 * 3、@ResponseBody：将所标识的控制器方法的返回值作为响应报文的响应体响应到浏览器
 * 4、使用@ResqponseBody注解响应浏览器的json格式的数据
 * a>导入jackjson的依赖
 * b>在SpringMVC的配置文件中设置<mvc:annotation-driven/>
 * c>将需要转换为json字符串的java对象直接作为控制器方法的返回值，使用@ResopnseBody注解标识控制器方法
 * 就可以将java对象直接转换为json字符串，并响应到浏览器
 *
 * 常用的java对象转换成为json的结果：
 * 实体类-->json对象
 * map-->json对象
 * list-->json数组
 */
@Controller
//@RestController  @ResponseBody + @Controller
public class TestAjaxController {

    @RequestMapping("/test/ajax")
    public void testAjax(Integer id,@RequestBody String requestBody,HttpServletResponse response) throws IOException {
        System.out.println("requestBody" +requestBody);
        System.out.println("id:"+id);
        response.getWriter().write("hello,axios");
    }

    @RequestMapping("/test/RequestBody/json")
    public void testRequestBody(@RequestBody Map<String,Object> map, HttpServletResponse response) throws IOException {
        System.out.println(map);
        response.getWriter().write("hello,RequestBody");

    }

    public void testRequestBody(@RequestBody User user, HttpServletResponse response) throws IOException {
        System.out.println(user);
        response.getWriter().write("hello,RequestBody");

    }

    @RequestMapping("/test/ResponseBody")
    @ResponseBody
    public String testResponseBody(){
        return "success";
    }

    @RequestMapping("/test/Response/json")
    @ResponseBody
    public List<User> testResponseBodyJson(){
        User user1 = new User(1001,"admin1","123456",23,"男");
        User user2 = new User(1002,"admin2","123456",23,"男");
        User user3 = new User(1003,"admin3","123456",23,"男");
        List<User> users = Arrays.asList(user1, user2, user3);
        return users;
    }

    /*public HashMap<String, Object> testResponseBodyJson(){
        User user1 = new User(1001,"admin1","123456",23,"男");
        User user2 = new User(1002,"admin2","123456",23,"男");
        User user3 = new User(1003,"admin3","123456",23,"男");
        HashMap<String, Object> map = new HashMap<>();
        map.put("1001",user1);
        map.put("1002",user2);
        map.put("1003",user3);
        return map;
    }*/
    /*public User testResponseBodyJson(){
        User user = new User(1001,"admin","123456",23,"男");
        return user;
    }*/
}
