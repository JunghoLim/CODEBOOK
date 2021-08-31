package com.codebook.handler;

import com.codebook.security.authentication.token.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler {
    private final JwtTokenProvider jwtTokenProvider;

    public Authentication getAuth(HttpServletRequest req, HttpServletResponse res){
        String token = jwtTokenProvider.resolveTokenFromRequest(req);
        Authentication auth = null;
        try{
            auth = jwtTokenProvider.getAuthentication(token);
        }catch (ExpiredJwtException e){
            token = jwtTokenProvider.resolveTokenFromResponse(res);
            auth = jwtTokenProvider.getAuthentication(token);
        }
        return auth;
    }
}
