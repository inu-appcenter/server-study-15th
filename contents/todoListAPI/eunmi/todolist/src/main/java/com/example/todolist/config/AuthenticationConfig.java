package com.example.todolist.config;

import com.example.todolist.config.security.CustomAccessDeniedHandler;
import com.example.todolist.config.security.CustomAuthenticationEntryPoint;
import com.example.todolist.config.security.JwtAuthenticationFilter;
import com.example.todolist.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final JwtProvider jwtProvider;

    private static final String[] PERMIT_URL_ARRAY = {
            "/api/v1/signup",
            "/api/v1/login",

            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()  // 인증을 ui로 하는 것이 아닌 토큰 인증 방식을 이용할 것이므로 해체. 즉, 로그인 페이지 없음
                .csrf().disable()

                .sessionManagement()  // REST API 기반 애플리케이션의 동작 방식 설정. JWT토큰으로 인증을 처리하기 때문에 세션은 사용하지 않으므르 STATELESS로 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt를 사용하는 경우 사용

                .and()
                .authorizeHttpRequests()  // 애플리케이션에 들어오는 요청에 대한 사용 권한을 체크
                .antMatchers(PERMIT_URL_ARRAY).permitAll() // swagger, join, login 은 언제나 접근 가능
                .anyRequest().authenticated()  // 나머지 모든 요청은 인가 필요

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())  // 권한을 확인하는 과정에서 통과하지 못하는 예외가 발생할 경우 예외를 전달
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())  // 인증과정에서 예외가 발생할 경우 예외를 전달

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
