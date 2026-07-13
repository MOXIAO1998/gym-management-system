package com.gym.mapper;

import com.gym.pojo.ClassOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassOrderMapper {

    List<ClassOrder> findAll();

    Boolean insertClassOrder(ClassOrder classOrder);

    List<ClassOrder> selectClassOrderByMemberAccount(Integer memberAccount);

    Boolean deleteByClassOrderId(Integer classOrderId);

    ClassOrder selectMemberByClassIdAndMemberAccount(Integer classId, Integer memberAccount);

    List<ClassOrder> selectMemberOrderList(Integer classId);



}
