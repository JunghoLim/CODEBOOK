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
            "/resources/**","/api/member/duplicate","/logout","/api/member/new","/api/auth/member","/api/board","/api/board/list","/api/board/comment",
            "/api/member/profile/img/**","/api/member/profile/**","/api/board/main/list","/api/file/img/**","/api/board/view","/","/css/**","/js/**","/favicon.ico","/img/**"
    };
    private static final String[] ADMIN = new String[]{
            "/api/admin/**"
    };
    private static final String[] USER = new String[]{
            "/api/member/**","/api/test","/api/comment/**","/api/member/follow","/api/board/img","/api/board/recommend"
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
         * [?????? ??????]
         * csrf ?????? ???????????? (?????? ???????????? ??????).
         * session ????????? ????????? ???????????? ??????.
         * httpBasic ???????????? ??????.
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
         * [????????? ?????? ?????? ??????]
         * */
        http
                .authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(USER).access("hasRole('USER') or hasRole('ADMIN')")
                .antMatchers(ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();

        /*
         * [???????????? ??????]
         * ?????? ?????? ??????, ?????? ?????????.
         * */
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/member", HttpMethod.DELETE.name()))
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .invalidateHttpSession(true);

        /*
         * [??????]
         * UsernamePasswordAuthenticationFilter ??? ????????????????????? UsernamePasswordAuthenticationFilter ??????,
         * ??????????????? WebSecurityConfigurerAdapter ??? ?????? authenticationManager ????????? ??????.
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
