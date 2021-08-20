package com.codebook.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomLogoutFilter2 extends LogoutFilter {
    private final AuthenticationManager authenticationManager;
    private RequestMatcher logoutRequestMatcher;
    private final LogoutHandler handler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    public CustomLogoutFilter2(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler handlers, AuthenticationManager authenticationManager) {
        super(logoutSuccessHandler, handlers);
        this.handler = handlers;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        ObjectMapper om = new ObjectMapper();
        if (requiresLogout(req, res)) {
            Map userJson = om.readValue(req.getInputStream(), Map.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userJson.get("email"), userJson.get("password"));
            Authentication auth = authenticationManager.authenticate(token);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Logging out [%s]", auth));
            }
            this.handler.logout(req, res, auth);
            this.logoutSuccessHandler.onLogoutSuccess(req, res, auth);
            return;
        }
        chain.doFilter(req, res);
    }
}
