package com.gym.mapper;

import com.gym.pojo.ClassOrder;
import com.gym.pojo.ClassTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ClassTableMapper {

    List<ClassTable> findAll();

    Boolean deleteClassByClassId(Integer classId);

    Boolean insertClass(ClassTable classTable);

    ClassTable selectByClassId(Integer classId);

    Boolean deleteOrderByClassId(Integer classId);

}
