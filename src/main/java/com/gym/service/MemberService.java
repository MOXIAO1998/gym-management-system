package com.gym.service;

import com.gym.pojo.Member;

import java.util.List;

public interface MemberService {
    List<Member> findAll();

    Boolean insertMember(Member member);

    Boolean updateMemberByMemberAccount(Member member);

    Member userLogin(Member member);
    //Member selectByAccountAndPassword(Member member);

    Boolean deleteByMemberAccount(Integer memberAccount);

    Integer selectTotalCount();

    List<Member> selectByMemberAccount(Integer memberAccount);

}
