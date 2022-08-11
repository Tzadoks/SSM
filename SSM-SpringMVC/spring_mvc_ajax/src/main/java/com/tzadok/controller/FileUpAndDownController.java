package com.tzadok.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

/**
 * @author Tzadok
 * @date 2022/8/10 08:41:57
 * @project SSM-SpringMVC
 * @description:
 * ResponseEntity:可以作为控制器方法的返回值，表示响应到浏览器的完整的响应报文
 *
 * 文件上传的要求：
 * 1、form表单的请求方式必须为post
 * 2、form表单必须设置属性 enctype="multipart/form-data"
 */
@Controller
public class FileUpAndDownController {

    @RequestMapping("/test/down")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径
         String realPath = servletContext.getRealPath("/images/1.jpg");
        // 创建输入流
         InputStream is = new FileInputStream(realPath);
        // 创建字节数组，is.available()获取输入流所对应的字节数
         byte[] bytes = new byte[is.available()];
        // 将流读到字节数组中
         is.read(bytes);
        // 创建HttpHeaders对象设置响应头信息
         MultiValueMap<String, String> headers = new HttpHeaders();
        // 设置要下载方式以及下载文件的名字
         headers.add("Content-Disposition", "attachment;filename=1.jpg");
        // 设置响应状态码
         HttpStatus statusCode = HttpStatus.OK;
        // 创建ResponseEntity对象
         ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        // 关闭输入流 is.close();
         return responseEntity;
    }

    @RequestMapping("/test/up")
    public String testUp(MultipartFile photo,HttpSession session) throws IOException {
        //获取上传的文件的文件名
        String filename = photo.getOriginalFilename();
        //获取上传的文件的后缀名
        String substring = filename.substring(filename.lastIndexOf("."));
        //获取UUID
        String uuid = UUID.randomUUID().toString();
        //拼接一个新的文件名
        filename = uuid + substring;
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取当前工程下photo目录的真实路径
        String photoPath = servletContext.getRealPath("photo");
        //创建photoPath所对应的file对象
        File file = new File(photoPath);
        //判断file所对应目录是否存在
        if (!file.exists()){
            file.mkdir();
        }
        String finaPath = photoPath + File.separator + filename;
        //实现文件上传
        photo.transferTo(new File(finaPath));
        return "success";
    }
}
