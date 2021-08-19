package com.codebook.security.authentication;

import com.codebook.security.config.CustomLogoutHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutFilter extends LogoutFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomLogoutHandler customLogoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    public CustomLogoutFilter(JwtTokenProvider jwtTokenProvider, LogoutSuccessHandler logoutSuccessHandler, CustomLogoutHandler customLogoutHandler) {
        super(logoutSuccessHandler, customLogoutHandler);
        this.jwtTokenProvider = jwtTokenProvider;
        this.customLogoutHandler = customLogoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (requiresLogout(request, response)) {
            String token = jwtTokenProvider.resolveToken(request);
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            this.customLogoutHandler.logout(request, response, auth);
            this.logoutSuccessHandler.onLogoutSuccess(request, response, auth);
            return;
        }
        chain.doFilter(request, response);
    }
}
