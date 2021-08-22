package com.codebook.security.config;

import com.codebook.mapper.MemberMapper;
import com.codebook.security.authentication.filter.JwtAuthenticationFilter;
import com.codebook.security.authentication.filter.JwtTokenFilter;
import com.codebook.security.authentication.token.JwtTokenProvider;
import com.codebook.security.authentication.handler.CustomLogoutHandler;
import com.codebook.security.authentication.handler.CustomLogoutSuccessHandler;
import com.codebook.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.header-name}")
    private String HEADER_NAME;

    private static final String[] PUBLIC = new String[]{
            "/resources/**","/api/member/duplicate","/logout","/api/member/new","/api/auth/member"
    };
    private static final String[] ADMIN = new String[]{
            "/api/admin/**"
    };
    private static final String[] USER = new String[]{
            "/api/member/**","/api/test"
    };

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;
    private final CorsFilter corsFilter;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final JwtTokenFilter jwtTokenFilter;


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
                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .headers()
                .httpStrictTransportSecurity()
                .disable();

        /*
         * [요청에 대한 권한 설정]
         * */
        http
                .authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(USER).hasRole("USER")
                .antMatchers(ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();

        /*
         * [로그아웃 설정]
         * 인증 정보 삭제, 세션 무효화.
         * */
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/member", HttpMethod.DELETE.name()))
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .invalidateHttpSession(true);

        /*
         * [필터]
         * UsernamePasswordAuthenticationFilter 를 커스터마이징한 UsernamePasswordAuthenticationFilter 등록,
         * 파라미터는 WebSecurityConfigurerAdapter 의 기본 authenticationManager 메서드 사용.
         * */
        http
                .addFilter(corsFilter)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(getJWTAuthenticationFilter(jwtTokenProvider));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .authenticationProvider(authenticationProvider());
    }

    private DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public JwtAuthenticationFilter getJWTAuthenticationFilter(JwtTokenProvider jwtTokenProvider) throws Exception {
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider, userDetailsService, passwordEncoder());
        filter.setFilterProcessesUrl("/api/auth/member");
        return filter;
    }
}
