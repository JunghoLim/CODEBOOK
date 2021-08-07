package com.codebook.mapper;

import com.codebook.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    void memberSignUp(@Param("member") Member member);
    int login(String email,String password);
}
