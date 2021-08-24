package com.codebook.mapper;

import com.codebook.domain.MemberDTO;
import com.codebook.domain.MemberProfileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void insert_member(MemberDTO memberDTO);
    void insert_profile(MemberDTO memberDTO);
    int duplicateCheckMemberEmail(String email);
    MemberDTO findMemberByEmail(String email);
    void update_Token(String token, String email);
    MemberProfileDTO findProfileByEmail(String email);
    void update_Profile(MemberProfileDTO profile);
}
