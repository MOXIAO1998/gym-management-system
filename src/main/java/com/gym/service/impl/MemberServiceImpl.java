package com.gym.service.impl;

import com.gym.mapper.MemberMapper;
import com.gym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Integer selectTotalCount() {
        return memberMapper.selectTotalCount();
    }
}
