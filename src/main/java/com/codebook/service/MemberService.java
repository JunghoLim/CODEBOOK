package com.codebook.service;

import com.codebook.domain.MemberDTO;
import com.codebook.mapper.MemberMapper;
import com.codebook.security.authentication.token.JwtTokenProvider;
import com.codebook.security.user.Role;
import com.codebook.security.user.Status;
import com.codebook.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUp(MemberDTO member){
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setStatus(Status.NONBLOCKED.name());
        member.setRole(Role.ROLE_USER.name());
        member.setToken("");
        memberMapper.insert_member(member);
    }

    public int duplicateCheck(String email){
        return memberMapper.duplicateCheck(email);
    }

    public Map<String, Object> extractMemberData(HttpServletRequest req, HttpServletResponse res){
        String token = jwtTokenProvider.resolveToken(req);
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        UserDetailsImpl member = (UserDetailsImpl) auth.getPrincipal();
        Map<String, Object> memberData = new HashMap<>();
        memberData.put("email", member.getUsername());
        memberData.put("nickname", member.getNickname());
        return memberData;
    }
}
