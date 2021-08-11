package com.codebook.mapper;

import com.codebook.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void memberSignUp(MemberDTO memberDTO);
    int duplicateCheck(String email);
    MemberDTO findMemberByEmail(String email);
}
