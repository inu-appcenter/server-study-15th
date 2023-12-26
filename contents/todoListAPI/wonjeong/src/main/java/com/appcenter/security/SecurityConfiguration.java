package com.appcenter.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // httpBasic().disable() UI를 사용하는 것을 기본 값으로 가진 시큐리티 설정을 비활성화
        httpSecurity.httpBasic().disable()
                // .csrf().disable() REST API에서는 CSRF 보안이 필요없기 때문에 비활성화
                // CSRF는 cross-site request forgery의 줄임말로 사이트간 요청 위조를 의미
                .csrf().disable()

                // REST API 기반 애플리케이션의 동작 방식을 설정
                // 지금 프로젝트에서는 JWT 토큰으로 인증을 처리하며. 세션은 사용하지 않기 때문에
                // STATELESS로 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                // 애플리케이션에 들어오는 요청에 대한 사용 권한을 체크한다.
                .authorizeRequests()
                // 권한을 설정하는 역할
                // 하단 요청에 대해서는 허용
                .antMatchers("/sign-in", "/sign-up",
                        "/exception").permitAll()
                // exception 단어가 들어가도 허용
                .antMatchers("**exception**").permitAll()

                .anyRequest().hasRole("ADMIN")

                .and()
                // 권한 확인 과정에서 통과하지 못할 경우 예외 처리
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                // 인증과정에서 통과를 못하면 예외처리
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                // 필터의 등록은 HttpSecurity 설정에서 진행
                // addFilterBefore 메서드를 통해 어떤 필터 앞에 추가할 것인지 설정할 수 있음
                // UsernamePasswordAuthenticationFilter 앞에 생성한 JwtAuthenticationFilter를 추가
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }

    // 인증 인가를 무시하는 경로 설정
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
    }
}
