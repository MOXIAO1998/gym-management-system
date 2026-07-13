package com.gym.service;

import com.gym.pojo.ClassOrder;

import java.util.List;

public interface ClassOrderService {

    List<ClassOrder> findAll();


    Boolean insertClassOrder(ClassOrder classOrder);


    List<ClassOrder> selectClassOrderByMemberAccount(Integer memberAccount);


    Boolean deleteByClassOrderId(Integer classOrderId);


    ClassOrder selectMemberByClassIdAndMemberAccount(Integer classId, Integer memberAccount);


    List<ClassOrder> selectMemberOrderList(Integer classId);

}
