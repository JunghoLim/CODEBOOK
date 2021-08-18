package com.codebook.mapper;

import com.codebook.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void insert_member(MemberDTO memberDTO);
    int duplicateCheck(String email);
    MemberDTO findMemberByEmail(String email);
    void updateToken(String token, String email);
}
