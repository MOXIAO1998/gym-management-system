package com.gym.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EquipmentMapper {
    Integer selectTotalCount();
}
