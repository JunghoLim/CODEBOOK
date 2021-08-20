package com.codebook.security.authentication;

import com.codebook.security.config.CustomLogoutHandler;
import com.codebook.security.config.CustomLogoutSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutFilter extends LogoutFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public CustomLogoutFilter(JwtTokenProvider jwtTokenProvider, LogoutSuccessHandler logoutSuccessHandler, CustomLogoutHandler customLogoutHandler, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        super(logoutSuccessHandler, customLogoutHandler);
        this.jwtTokenProvider = jwtTokenProvider;
        this.customLogoutHandler = customLogoutHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       //토큰이 유효한 상태에서 로그아웃을 시도했는지
        String token = jwtTokenProvider.resolveToken(request);
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        System.out.println(auth);
//        if(jwtTokenProvider.validateToken(token)){
//
//        }

        customLogoutHandler.logout(request,response,auth);
        customLogoutSuccessHandler.onLogoutSuccess(request,response,auth);
        chain.doFilter(request,response);
    }
}
