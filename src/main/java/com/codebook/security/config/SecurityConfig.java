package com.codebook.security.config;

import com.codebook.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.header-name}")
    private String HEADER_NAME;

    private static final String[] ANONYMOUS = new String[]{
            "/api/member/new","/api/member"
    };
    private static final String[] PUBLIC = new String[]{
            "/resources/**","/api/member/duplicate","/api/test","/logout"
    };
    private static final String[] ADMIN = new String[]{
            "/admin/**"
    };

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        * [환경 설정]
        * cors 필터 활성화
        * csrf 보안 비활성화 (개발 편의성을 위해)
        * session 생성과 사용을 사용하지 않음
        * */
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable();

        /*
        * [요청에 대한 권한 설정]
        * */
        http
                .authorizeRequests()
                .antMatchers(ANONYMOUS).anonymous()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();

        /*
        * [로그아웃 설정]
        * 인증 정보 삭제, 세션 무효화
        * */
        http
                .logout().logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .deleteCookies(HEADER_NAME)
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        /*
        * [인증 관련 설정]
        * */
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
