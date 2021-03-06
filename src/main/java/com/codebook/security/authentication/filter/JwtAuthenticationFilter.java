package com.codebook.security.authentication.filter;

import com.codebook.security.authentication.token.JwtModel;
import com.codebook.security.authentication.token.JwtTokenProvider;
import com.codebook.security.service.UserDetailsServiceImpl;
import com.codebook.security.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class   JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        try {
            Map userJson = om.readValue(req.getInputStream(), Map.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userJson.get("email"),userJson.get("password"));
            return authenticationManager.authenticate(token);
        }catch (LockedException e){
            res.setStatus(e.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        JwtModel jwtToken = jwtTokenProvider.createToken(userDetails.getUsername());
        //?????? ????????? ????????? ?????? ??????.
        jwtTokenProvider.saveToken(res, jwtToken.getAccessToken());
        //DB??? refresh token ??????
        userDetailsService.issuedRefreshToken(jwtToken.getRefreshToken(), userDetails.getUsername());
    }
}
