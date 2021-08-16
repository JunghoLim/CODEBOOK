package com.codebook.service;

import com.codebook.domain.MemberDTO;
import com.codebook.mapper.MemberMapper;
import com.codebook.security.user.Role;
import com.codebook.security.user.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(MemberDTO member){
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setStatus(Status.NONBLOCKED.name());
        member.setRole(Role.USER.name());
        member.setToken("");
        memberMapper.insert_member(member);
    }

    public int duplicateCheck(String email){
        return memberMapper.duplicateCheck(email);
    }

}
