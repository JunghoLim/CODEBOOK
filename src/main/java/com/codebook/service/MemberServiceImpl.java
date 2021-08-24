package com.codebook.service;

import com.codebook.domain.MemberDTO;
import com.codebook.domain.MemberProfileDTO;
import com.codebook.mapper.MemberMapper;
import com.codebook.repository.MemberRepository;
import com.codebook.security.authentication.token.JwtTokenProvider;
import com.codebook.security.user.Role;
import com.codebook.security.user.Status;
import com.codebook.security.user.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
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
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void signUp(MemberDTO member){
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setStatus(Status.NONBLOCKED.name());
        member.setRole(Role.ROLE_USER.name());
        member.setToken("");
        memberRepository.insertMemberAndProfile(member);
    }

    @Override
    public int duplicateCheck(String email){
        return memberMapper.duplicateCheckMemberEmail(email);
    }

    @Override
    public Map<String, Object> extractMemberData(HttpServletRequest req, HttpServletResponse res) {
        String token = jwtTokenProvider.resolveTokenFromRequest(req);
        Authentication auth = null;
        try{
            auth = jwtTokenProvider.getAuthentication(token);
        }catch (ExpiredJwtException e){
            token = jwtTokenProvider.resolveTokenFromResponse(res);
            auth = jwtTokenProvider.getAuthentication(token);
        }
        UserDetailsImpl member = (UserDetailsImpl) auth.getPrincipal();
        MemberProfileDTO profile = memberMapper.findProfileByEmail(member.getUsername());
        Map<String, Object> memberData = new HashMap<>();
        memberData.put("email", member.getUsername());
        memberData.put("profile", profile);
        return memberData;
    }
}
