package com.codebook.security.authentication;

import com.codebook.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.header-name}")
    private String HEADER_NAME;

    @Value("${jwt.access-token-expire-length}")
    private long ACCESS_VALIDITY_IN_MILLISECONDS;

    @Value("${jwt.refresh-token-expire-length}")
    private long REFRESH_VALIDITY_IN_MILLISECONDS;

    private final UserDetailsServiceImpl userDetailsService;

    //Component 의존성 주입 후 필수적으로 SECRET_KEY Base64로 암호화.
    @PostConstruct
    protected void init() {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    //토큰 초기 생성
    public JwtModel createToken(String username) {
        Date now = new Date();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", HEADER_NAME);
        claims.put("aud", username);
        claims.put("iat", now.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date accessDate = new Date(now.getTime() + ACCESS_VALIDITY_IN_MILLISECONDS);
        Date refreshDate = new Date(now.getTime() + REFRESH_VALIDITY_IN_MILLISECONDS);

        return JwtModel.builder()
                .accessToken(this.generateToken(claims, now, accessDate))
                .refreshToken(this.generateToken(claims, now, refreshDate))
                .accessTokenExpirationDate(sdf.format(accessDate))
                .refreshTokenExpirationDate(sdf.format(refreshDate))
                .build();
    }

    //토큰 발급
    private String generateToken(Map<String, Object> claims, Date now, Date expirationDate) {
        System.out.println(this.SECRET_KEY+"here");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    //토큰 검증
    public boolean validateToken(String token) {
        try {
            this.extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw e;
        }
    }

    //토큰을 헤더에 저장
    public void saveToken(HttpServletResponse response, String token) {
        response.setHeader(HEADER_NAME, token);
    }

    //헤더의 토큰 분석
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_NAME);
        if (StringUtils.isNotBlank(bearerToken)) {
            return bearerToken;
        }
        return null;
    }

    //쿠키에 토큰 저장
    public void createCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from(HEADER_NAME, token)
                .httpOnly(true)
                .sameSite("lax")
                .maxAge(ACCESS_VALIDITY_IN_MILLISECONDS)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    //쿠키의 토큰 분석
    public String resolveCookie(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(HEADER_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    //시큐리티에서 토큰을 검증
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getClaims(token, "sub"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰의 claims 검증
    public Jws<Claims> extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
    }

    //토큰의 claims 추출
    public String getClaims(String token, String key) {
        return this.extractAllClaims(token)
                .getBody()
                .get(key, String.class);
    }
}