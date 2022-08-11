package com.tzadok.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzadok.mapper.EmpMapper;
import com.tzadok.pojo.Emp;
import com.tzadok.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:29:15
 * @project SSM-SpringMVC
 * @description:
 */
@Service
@Transactional
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Emp> getAllEmployee() {
        return empMapper.getAllEmployee();
    }

    @Override
    public PageInfo<Emp> getEmployeePage(Integer pageNum) {
        //开启分页功能
        PageHelper.startPage(pageNum,5);
        //查询所有的员工信息
        List<Emp> list = empMapper.getAllEmployee();
        //获取分页相关数据
        PageInfo<Emp> page = new PageInfo<>(list,5);
        return page;
    }

    @Override
    public void saveEmployee(Emp emp) {
        empMapper.saveEmployee(emp);
    }

    @Override
    public Emp getEmployeeById(Integer id) {
        return empMapper.getEmployeeById(id);
    }

    @Override
    public void updateEmployee(Emp emp) {
        System.out.println(emp);
        empMapper.updateEmployee(emp);
    }

    @Override
    public void deleteEmp(Integer id) {
        empMapper.deleteEmp(id);
    }
}
