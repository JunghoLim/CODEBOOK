package com.codebook.security.config;

import com.codebook.security.authentication.JwtAuthenticationFilter;
import com.codebook.security.authentication.JwtTokenProvider;
import com.codebook.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.header-name}")
    private String HEADER_NAME;

    private static final String[] PUBLIC = new String[]{
            "/resources/**","/api/member/duplicate","/api/test","/logout","/api/member/new"
    };
    private static final String[] ADMIN = new String[]{
            "/admin/**"
    };
    private static final String[] USER = new String[]{
            "/api/member/**"
    };

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        * [환경 설정]
        * csrf 보안 비활성화 (개발 편의성을 위해).
        * session 생성과 사용을 사용하지 않음.
        * httpBasic 사용하지 않음.
        * */
        http
                .cors()
                .disable()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .httpBasic()
                .disable();

        /*
        * [요청에 대한 권한 설정]
        * */
        http
                .authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(USER).access("hasRole('ROLE_USER')")
                .antMatchers(ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();

        /*
        * [로그아웃 설정]
        * 인증 정보 삭제, 세션 무효화.
        * */
        http
                .logout().logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .deleteCookies(HEADER_NAME)
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        /*
        * [필터]
        * Cors 필터 등록.
        * UsernamePasswordAuthenticationFilter 를 커스터마이징한 UsernamePasswordAuthenticationFilter 등록,
        * 파라미터는 WebSecurityConfigurerAdapter 의 기본 authenticationManager 메서드 사용.
        * */
        http
                .addFilter(corsFilter)//교차 출처 리소스
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider));//제이슨 웹 토큰을 필터에 넣는다.
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .authenticationProvider(authenticationProvider());
    }
    private DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
