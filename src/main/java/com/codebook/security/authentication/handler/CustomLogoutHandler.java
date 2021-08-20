package com.codebook.security.authentication.handler;

import com.codebook.security.authentication.token.JwtTokenProvider;
import com.codebook.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getClaims(token, "aud");
        userDetailsService.issuedRefreshToken(null, username);
    }
}
