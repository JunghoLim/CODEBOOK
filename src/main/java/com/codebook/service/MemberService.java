package com.codebook.service;

import com.codebook.domain.MemberDTO;
import com.codebook.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void signUp(Map<String, String> param){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(param.get("email"));
        memberDTO.setName(param.get("name"));
        memberDTO.setPassword(new BCryptPasswordEncoder().encode(param.get("password")));
        memberDTO.setNickname(param.get("nickname"));
        memberMapper.memberSignUp(memberDTO);
    }

    public int duplicateCheck(String email){
        return memberMapper.duplicateCheck(email);
    }

}
