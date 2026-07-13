package com.gym.mapper;

import com.gym.pojo.Equipment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EquipmentMapper {

    List<Equipment> findAll();

    Boolean deleteByEquipmentId(Integer equipmentId);

    Boolean insertEquipment(Equipment equipment);

    Boolean updateEquipmentByEquipmentId(Equipment equipment);

    List<Equipment> selectByEquipmentId(Integer equipmentId);

    Integer selectTotalCount();

}
