package com.gym.mapper;

import com.gym.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<Employee> findAll();

    Boolean deleteByEmployeeAccount(Integer employeeAccount);

    Boolean insertEmployee(Employee employee);

    Boolean updateMemberByEmployeeAccount(Employee employee);

    List<Employee> selectByEmployeeAccount(Integer employeeAccount);

    Integer selectTotalCount();

}
