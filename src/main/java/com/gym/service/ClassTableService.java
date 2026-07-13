package com.gym.service;

import com.gym.pojo.ClassTable;

import java.util.List;

public interface ClassTableService {

    List<ClassTable> findAll();

    Boolean deleteClassByClassId(Integer classId);

    Boolean insertClass(ClassTable classTable);

    ClassTable selectByClassId(Integer classId);

    Boolean deleteOrderByClassId(Integer classId);

}
