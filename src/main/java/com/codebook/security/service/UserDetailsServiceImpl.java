package com.codebook.security.service;

import com.codebook.domain.MemberDTO;
import com.codebook.mapper.MemberMapper;
import com.codebook.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberDTO member = memberMapper.findMemberByEmail(email);
        return new UserDetailsImpl(member);
    }

}
