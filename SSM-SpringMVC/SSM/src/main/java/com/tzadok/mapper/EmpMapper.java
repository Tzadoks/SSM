package com.tzadok.mapper;

import com.tzadok.pojo.Emp;

import java.util.List;

/**
 * @author Tzadok
 * @date 2022/8/10 22:44:02
 * @project SSM-SpringMVC
 * @description:
 */
public interface EmpMapper {

    /**
     * 查询所有的员工信息
     * @return
     */
    List<Emp> getAllEmployee();

    /**
     * 添加员工
     */
    void saveEmployee(Emp emp);

    /**
     * 根据id查找员工
     * @param id
     * @return
     */
    Emp getEmployeeById(Integer id);

    /**
     * 修改用户信息
     * @param emp
     */
    void updateEmployee(Emp emp);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteEmp(Integer id);
}
