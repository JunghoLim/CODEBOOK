package com.codebook.service;

import com.codebook.dto.Member;
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
        Member member = new Member();
        member.setEmail(param.get("email"));
        member.setName(param.get("name"));
        member.setPassword(param.get("password"));
        memberMapper.memberSignUp(member);
    }
    public int login(Map<String,String> param){
        if(memberMapper.login(param.get("email"),param.get("password"))==1){

            return 1;
        }
        return 0;
    }
}
