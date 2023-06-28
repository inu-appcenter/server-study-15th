package com.example.todolist.config.security;

import com.example.todolist.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${jwt.secret}")
    private String secretKey;
    private final long expiredMs = 1000 * 60 * 60L;         // 1h

    @PostConstruct
    protected void init() {
        log.info("[init] JwtProvider secretKey 초기화 실행");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] JwtProvider secretKey 초기화 완료");
    }

    public String createJwt(String userName, String role) {
        log.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims();  // claims : 원하는 정보를 담아 둘 수 있음
        claims.put("userName", userName);
        claims.put("roles", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 현재 시간을 담아야함
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)   // 해당 알고리즘과 시크릿 킷으로 sign 됨
                .compact();
    }

    public String getUserName(String token) {
        log.info("[getUserName] 유저정보 가져오기 : {}", token);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userName", String.class);  // 클레임의 값을 추출하여 회원정보 반환
    }

    public Authentication getAuthentication(String token) {  // 필터에서 인증 성공 시, securityContextHolder에 저장할 Authentication을 생성하는 역할
        log.info("[getAuthentication] 토큰 인증정보 조회");
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(this.getUserName(token));
        log.info("[getAuthentication] 토큰 인증정보 조회완료, UserDetails userName: {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getToken(HttpServletRequest request) {
        log.info("[getToken] Http header에서 Token값 추출");
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }


    public boolean isExpired(String token) {
        log.info("[isExpired] Token값 유효성 검증");
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
        // 시크릿 키를 이용하여 토큰 정보를 열어서 유효시간 여부 확인
    }



}
