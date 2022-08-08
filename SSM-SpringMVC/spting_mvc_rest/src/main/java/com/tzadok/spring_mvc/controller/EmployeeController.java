package com.tzadok.spring_mvc.controller;

import com.tzadok.spring_mvc.dao.EmployeeDao;
import com.tzadok.spring_mvc.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * @author Tzadok
 * @date 2022/8/8 21:28:31
 * @project SSM-SpringMVC
 * @description:
 * 1、查询所有员工 -->/employee --> get
 * 2、跳转添加员工页面 -->/to/add --> get
 * 3、添加员工 -->/employee --> post
 * 4、跳转到修改页面 -->/employee/1 --> get
 * 5、修改员工信息 -->/employee --> get
 * 6、删除员工信息 -->/employee/1 --> get
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;




    /**
     * 查询所有员工
     * @param model
     * @return
     */
    @GetMapping("/employee")
    public String getAllEmployee(Model model){
        //获取所有的员工信息
        Collection<Employee> allEmployee = employeeDao.getAll();
        //将员工信息存到请求域中
        model.addAttribute("allEmployee",allEmployee);
        return "employee_list";
    }

    @PostMapping("/employee")
    public String saveEmployee(Employee employee){
        //保存员工信息
        employeeDao.save(employee);
        //重定向到列表功能：/employee
        return "redirect:/employee";
    }

    @GetMapping("/employee/{id}")
    public String ToUpdate(@PathVariable("id") Integer id,Model model){
        //根据id查询员工信息
        Employee employee = employeeDao.get(id);
        //将员工信息共享到请求域
        model.addAttribute("employee",employee);
        //跳转到employee_update.html
        return "employee_update";
    }

    @PutMapping ("/employee")
    public String updateEmployee(Employee employee){
        //修改员工信息
        employeeDao.save(employee);
        //重定向到列表功能
        return "redirect:/employee";
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){

        employeeDao.delete(id);

        return "redirect:/employee";
    }


}
