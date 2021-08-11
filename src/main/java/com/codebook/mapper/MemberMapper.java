package com.codebook.mapper;

import com.codebook.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

<<<<<<< HEAD
    void memberSignUp(MemberDTO memberDTO);
    int duplicateCheck(String email);
=======
    void memberSignUp(@Param("member") MemberDTO memberDTO);
    int duplicateCheck(String email);//중복체-크
>>>>>>> 37e84a700e4174864c2337bc5456b8453f0c21f8
    MemberDTO findMemberByEmail(String email);
}
