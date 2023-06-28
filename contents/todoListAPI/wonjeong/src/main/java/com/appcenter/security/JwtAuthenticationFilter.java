package com.appcenter.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 필터를 상속받아서 구현하는 것이 편리하다.
// 필터가 두번 호출되는 것을 막기 위해, OncePerFilterBean을 사용
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // JwtTokenProvider를 통해 ServletRqeust에서 토근을 추출한 뒤,
    // 토큰에 대한 유효성을 검사한다.
    // 토큰이 유효하다면 Authentication 객체를 생성해서 SecurityContextHolder에 추가하는 작업을 수행한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // 값 유효성 체크
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 서블릿 실행 메서드
        // 이 코드 전에 작성된 코드는 서블릿이 실행되기 전에 실행됨
        filterChain.doFilter(request, response);
        // 이후 코드는 서블릿이 실행된 이후 실행

    }
}
