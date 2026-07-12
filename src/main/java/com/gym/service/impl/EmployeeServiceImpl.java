package com.gym.service.impl;

import com.gym.mapper.EmployeeMapper;
import com.gym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Integer selectTotalCount() {
        return employeeMapper.selectTotalCount();
    }
}
