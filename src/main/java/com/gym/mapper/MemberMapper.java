package com.gym.mapper;

import com.gym.pojo.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<Member> findAll();

    Boolean insertMember(Member member);

    Boolean updateMemberByMemberAccount(Member member);

    Member selectByAccountAndPassword(Member member);

    Boolean deleteByMemberAccount(Integer memberAccount);

    Integer selectTotalCount();

    List<Member> selectByMemberAccount(Integer memberAccount);

}
