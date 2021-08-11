package com.codebook.security.config;

import com.codebook.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserDetailsServiceImpl userDetailsService;

    private static final String[] PUBLIC = new String[]{
            "/resources/**","/api/member/duplicate","/api/test","/board"
    };

    private static final String[] ADMIN = new String[]{
            "/admin/**"
    };

    private static final String[] ANONYMOUS = new String[]{
            "/api/member/new","/api/1member"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()                      //cors 필터 활성화
                .and()
                .csrf()
                .disable()                   //csrf 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()                       //REST API 구현을 위해 session을 disable
                .authorizeRequests()
                .antMatchers(ANONYMOUS).anonymous()              //해당 요청 user와 admin을 제외한 anonymous에게만 허용
                .antMatchers(PUBLIC).permitAll()                 //해당 요청 권한 전체 해제
                .antMatchers(ADMIN).hasRole("ADMIN")        //해당 요청 admin에게만 권한 부여
                .anyRequest().authenticated()                  //이외의 모든 요청 spring security context 내 인증 필수
                .and()
                .httpBasic();

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
