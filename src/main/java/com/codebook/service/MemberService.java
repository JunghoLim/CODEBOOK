package com.codebook.service;

import com.codebook.domain.MemberDTO;
import com.codebook.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public void signUp(Map<String, String> param){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(param.get("email"));
        memberDTO.setName(param.get("name"));
        memberDTO.setPassword(param.get("password"));
        memberMapper.memberSignUp(memberDTO);
    }
    public int login(Map<String,String> param){
        if(memberMapper.login(param.get("email"),param.get("password"))==1){

            return 1;
        }
        return 0;
    }
}
