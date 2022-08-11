package com.tzadok.service;

import com.github.pagehelper.PageInfo;
import com.tzadok.pojo.Emp;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:28:57
 * @project SSM-SpringMVC
 * @description:
 */
public interface EmpService {

    /**
     * 查询所有的员工信息
     * @return
     */
    List<Emp> getAllEmployee();

    /**
     * 获取员工的分页信息
     * @param pageNum
     * @return
     */
    PageInfo<Emp> getEmployeePage(Integer pageNum);

    /**
     * 添加员工
     */
    void saveEmployee(Emp emp);

    /**
     * 根据员工号查找员工
     * @param id
     * @return
     */
    Emp getEmployeeById(Integer id);

    /**
     * 修改用户
     * @param emp
     */
    void updateEmployee(Emp emp);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteEmp(Integer id);
}
