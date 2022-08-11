package com.tzadok.controller;

import com.github.pagehelper.PageInfo;
import com.tzadok.pojo.Emp;
import com.tzadok.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:19:37
 * @project SSM-SpringMVC
 * @description:
 * 查询所有的员工信息-->/employee-->get
 * 查询分页的员工信息-->/employee/page/1-->get
 * 根据id查询员工信息-->/employee/id-->get
 * 跳转到添加页面-->/to/Add-->get
 * 添加员工信息-->/employee-->post
 * 修改员工信息-->/employee-->put
 * 删除员工信息-->/employee-->delete
 */
@Controller
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping ("/employee/page/{pageNum}")
    public String getEmployeePage(@PathVariable("pageNum") Integer pageNum,Model model){
        //获取员工的分页信息
        PageInfo<Emp> page = empService.getEmployeePage(pageNum);
        //将分页数据共享到请求域
        model.addAttribute("page",page);
        //跳转到employee-list页面
        return "employee-list";
    }

    @GetMapping ("/employee")
    public String getAllEmployee(Model model){
        //查询所有的员工信息
        List<Emp> list = empService.getAllEmployee();
        //将所有的员工信息共享到请求域
        model.addAttribute("list",list);
        //跳转到employee-list页面
        return "employee-list";
    }

    @GetMapping("/to/Add")
    public String toAdd(){
        return "employee-add";
    }

    @PostMapping("/employee")
    public String saveEmployee(Emp emp){
        empService.saveEmployee(emp);
        return "redirect:/employee/page/1";
    }

    @GetMapping("/employee/{id}")
    public String getEmployeeById(@PathVariable("id") Integer id,Model model){
        Emp emp = empService.getEmployeeById(id);
        model.addAttribute("emp",emp);
        return "employee-update";
    }

    @GetMapping("/to/Update")
    public String toUpdate(){
        return "employee-update";
    }

    @PutMapping ("/updateEmployee")
    public String updateEmployee(Emp emp){
        System.out.println(emp);
        empService.updateEmployee(emp);
        return "redirect:/employee/page/1";
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable Integer id){
        empService.deleteEmp(id);
        return "redirect:/employee/page/1";

    }
}
