package com.example.todo.common.jwt.filter;

import com.example.todo.common.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


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

        String baseUrl = "http://localhost:8080/api";
        String requestURL = request.getRequestURL().toString();
        log.info("requestURL : " + requestURL);
        log.info("request.getMethod() : " + request.getMethod());
        if(requestURL.equals(baseUrl + "/oauth/login")
                || requestURL.equals(baseUrl + "/user/sign-in")
                || requestURL.equals(baseUrl + "/user/addInterestCategory")
                || requestURL.contains(baseUrl + "/user/mypage")
                || ( requestURL.contains(baseUrl + "/votes")
                    && (
                        request.getMethod().equals("POST")
                        || request.getMethod().equals("DELETE")
                        || request.getMethod().equals("PATCH")
                        )
                    )
        ) {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                try {
                    HashMap<String, Object> parseJwtTokenMap = jwtProvider.parseJwtToken(authorizationHeader);
                    Claims claims = (Claims)parseJwtTokenMap.get("claims");
                    String token = (String) parseJwtTokenMap.get("token");
                    request.setAttribute("claims", claims); // jwt 정보 컨트롤러에서 사용할 수 있게 request에 담기
                    request.setAttribute("token",token);
                } catch (ExpiredJwtException jwtException) {
                    throw jwtException;
                }
        }
        if( requestURL.contains(baseUrl + "/votes")
                && (
                request.getMethod().equals("GET")
        )
        ){
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null) {
                Long userId = 0L;
                request.setAttribute("userId",userId);
            }else {
                try {
                    HashMap<String, Object> parseJwtTokenMap = jwtProvider.parseJwtToken(authorizationHeader);
                    Claims claims = (Claims)parseJwtTokenMap.get("claims");
                    String token = (String) parseJwtTokenMap.get("token");
                    Integer Id = (int) claims.get("userId");
                    Long userId = Long.valueOf(Id);
                    request.setAttribute("userId", userId); // jwt 정보 컨트롤러에서 사용할 수 있게 request에 담기
                    request.setAttribute("token",token);
                } catch (ExpiredJwtException jwtException) {
                    throw jwtException;
                }
            }
        }
            filterChain.doFilter(request, response);
    }

}
