package com.codebook.security.authentication;

import com.codebook.security.service.UserDetailsServiceImpl;
import com.codebook.security.user.UserDetailsImpl;
import com.codebook.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        try {
            Map userJson = om.readValue(req.getInputStream(), Map.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userJson.get("email"), userJson.get("password"));
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LockedException e){
            res.setStatus(e.hashCode());
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        JwtModel jwtToken = jwtTokenProvider.createToken(userDetails.getUsername());
        //응답 해더에 토큰을 담아 응답.
        jwtTokenProvider.saveToken(res, jwtToken.getAccessToken());
        //DB에 refresh token 저장
        userDetailsService.issuedRefreshToken(jwtToken.getRefreshToken(), userDetails.getUsername());
    }
}
