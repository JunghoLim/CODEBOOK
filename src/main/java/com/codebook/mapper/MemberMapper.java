package com.codebook.mapper;

import com.codebook.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    void memberSignUp(@Param("member") MemberDTO memberDTO);
    int duplicateCheck(String email);
    MemberDTO findMemberByEmail(String email);
}
