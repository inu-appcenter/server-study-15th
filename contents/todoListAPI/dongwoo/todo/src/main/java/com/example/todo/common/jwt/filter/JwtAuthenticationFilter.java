package com.example.todo.common.jwt.filter;

import com.example.todo.common.jwt.JwtProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtProvider jwtProvider;

    @Builder
    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("jwt Filter...");
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("jwtToken = {}",jwtToken);
        if ((jwtToken != null) && (jwtProvider.validateToken(jwtToken))) {
            Authentication auth = jwtProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        log.info("next Filter");
        filterChain.doFilter(request, response);
    }
}
