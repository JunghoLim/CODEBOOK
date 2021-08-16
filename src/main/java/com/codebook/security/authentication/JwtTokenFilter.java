package com.codebook.security.authentication;

import com.codebook.mapper.MemberMapper;
import com.codebook.security.user.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String[] excludeUrl =  new String[]{
            "/resources/**","/api/test","/api/member/duplicate","/logout","/api/member/new","/api/member"
    };

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveToken(req);
        String refreshToken = null;

        //access 토큰 검증
        try{
            //발급된 토근이 존재하고 검증을 마쳤을때의 조건식
            if(StringUtils.isNotBlank(accessToken) && jwtTokenProvider.validateToken(accessToken)){
                Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (ExpiredJwtException e){
            UserDetailsImpl member = new UserDetailsImpl(memberMapper.findMemberByEmail(e.getClaims().getSubject()));
            if (ObjectUtils.isNotEmpty(member)){
                refreshToken = member.getToken();
            }
        }catch (Exception e){
            SecurityContextHolder.clearContext();
        }

        //refresh 토큰을 통해 access 토큰 재 발급
        if(StringUtils.isNotBlank(refreshToken)){
                try{
                    if(jwtTokenProvider.validateToken(refreshToken)){
                        Authentication auth = jwtTokenProvider.getAuthentication(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(auth);

                        // 새로운 access 토큰 발급
                        String newAccessToken = jwtTokenProvider.createToken(jwtTokenProvider.getClaims(refreshToken, "sub")).getAccessToken();
                        jwtTokenProvider.createCookie(res, newAccessToken);
                    }
                } catch (Exception e){
                    //워의 과정에서 예외가 발생했을시 해당 스레드의 security context 를 비움
                    SecurityContextHolder.clearContext();
                }
            filterChain.doFilter(req, res);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
        Collection<String> excludeUrlPatterns = new LinkedHashSet<>(Arrays.asList(excludeUrl));

        return excludeUrlPatterns.stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, req.getServletPath()));
    }
}
