package com.gym.service;

import com.gym.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    Integer selectTotalCount();

    List<Employee> findAll();

    Boolean insertEmployee(Employee employee);

    Boolean deleteEmployeeAccount(Integer employeeAccount);

    Boolean updateMemberByEmployeeAccount(Employee employee);

    List<Employee> selectByEmployeeAccount(Integer employeeAccount);
}
