package com.gym.service.impl;

import com.gym.mapper.EquipmentMapper;
import com.gym.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;


    @Override
    public Integer selectTotalCount() {
        return equipmentMapper.selectTotalCount();
    }
}
