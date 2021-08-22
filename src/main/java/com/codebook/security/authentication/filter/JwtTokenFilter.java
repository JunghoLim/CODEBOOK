package com.codebook.security.authentication.filter;

import com.codebook.mapper.MemberMapper;
import com.codebook.security.authentication.token.JwtTokenProvider;
import com.codebook.security.user.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String[] excludeUrl =  new String[]{
            "/resources/**","/api/member/duplicate","/api/auth/member","/api/member/new"
    };

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;

    @Value("${jwt.header-name}")
    private String HEADER_NAME;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveTokenFromRequest(req);
        String refreshToken = null;
        //access 토큰 검증
        try{
            //발급된 토근이 존재하고 검증을 마쳤을때의 조건식
            if(StringUtils.isNotBlank(accessToken) && jwtTokenProvider.validateToken(accessToken)){
                Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (ExpiredJwtException e){
            //토큰의 만료기간이 지난 경우의 예외에서 claims 를 추출 후 username 으로 토큰 재발급.
            UserDetailsImpl member = new UserDetailsImpl(memberMapper.findMemberByEmail(e.getClaims().getAudience()));
            if (ObjectUtils.isNotEmpty(member)){
                refreshToken = member.getToken();
            }
        }catch (Exception e){
            SecurityContextHolder.clearContext();
        }

        //refresh 토큰을 통해 access 토큰 재 발급
        if(StringUtils.isNotBlank(refreshToken)){
            try{
                try{
                    if(jwtTokenProvider.validateToken(refreshToken)){
                        Authentication auth = jwtTokenProvider.getAuthentication(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        // 새로운 access 토큰 발급
                        String newAccessToken = jwtTokenProvider.createToken(jwtTokenProvider.getClaims(refreshToken, "aud")).getAccessToken();
                        if(StringUtils.isEmpty(res.getHeader(HEADER_NAME))){
                        jwtTokenProvider.saveToken(res, newAccessToken);
                        }
                    }
                }catch (ExpiredJwtException e) {
                    SecurityContextHolder.clearContext();
                }
            } catch (Exception e){
                //워의 과정에서 예외가 발생했을시 해당 스레드의 security context 를 비움
                SecurityContextHolder.clearContext();
                return;
            }
        }
            filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
        Collection<String> excludeUrlPatterns = new LinkedHashSet<>(Arrays.asList(excludeUrl));

        return excludeUrlPatterns.stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, req.getServletPath()));
    }
}
