package com.example.todo.common.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    private final UserDetailsService userDetailsService;

    /**
     * JwtToken 생성 메서드
     * @param userId         : 유저  아이디
     * @param minutes        : jwt 유효시간
     * @return               : jwt 토큰
     */
    public String makeJwtToken(Long userId, int minutes) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(minutes).toMillis()))
                .claim("userId",userId)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    public HashMap<String, Object> parseJwtToken(String authorizationHeader) {
        validationAuthorizationHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);

        log.info("*******Accesstoken : {}",token);
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("token", token);
        //토큰 검증
        Claims claims = (Claims) getToken(token);

        hashMap.put("claims", claims);

        return hashMap;
    }


    /**
     * 토큰 헤더 검증 메서드 : 헤더가 없거나, Bearer 로 시작하지않으면 에러처리
     * @param header
     */
    private void validationAuthorizationHeader(String header) {
        log.info("*******header : {}", header);
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 토큰 추출 메서드 : 헤더에서 토큰값만 추출해줌
     * @param authorizationHeader   : 헤더
     * @return                      : 추출된 토큰 값
     */
    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    /**
     * 토큰 검증 메서드
     * @param token                 :
     * @return                      : 토큰안에 들어있는 유저 이메일 값(user Email)
     * @throws ExpiredJwtException
     */
    public Object getToken(String token) throws ExpiredJwtException {

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) {
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return info;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
