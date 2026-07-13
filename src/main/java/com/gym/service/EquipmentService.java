package com.gym.service;

import com.gym.pojo.Equipment;

import java.util.List;

public interface EquipmentService {

    List<Equipment> findAll();

    Boolean deleteByEquipmentId(Integer equipmentId);

    Boolean insertEquipment(Equipment equipment);

    Boolean updateEquipmentByEquipmentId(Equipment equipment);

    List<Equipment> selectByEquipmentId(Integer equipmentId);

    Integer selectTotalCount();

}
