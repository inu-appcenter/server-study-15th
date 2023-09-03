package com.appcenter.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    // application.yml 에서 값을 가져 옴
    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";

    private final long tokenValidMilliSecond = 1000L * 60 * 60;

    // JwtTokenProvider는 @Compoent 어노테이션이 지정되어 있어서
    // 애플리케이션이 가동되면, 빈으로 자동주입됨.
    // 그때 @PostConstructr가 지정되어 있는 init() 메서드가 자동으로 실행됨

    @PostConstruct
    protected void init() {
        // secretKey를 인코딩
        // JwtTokenProvider 내 secretKey 초기화
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성 메서드
    public String createToken(String userUid, List<String> roles) {

        System.out.println("토큰 생성");
        // JWT 토큰에 값을 넣기 위해 Claims 객체를 생성하고
        // Claims 객체에는 토큰에 포함된 모든 정보에 대한 속성이 포함되어 있다.
        // Claims 의 sub 속성에 값을 추가하기 위해 User의 uid 값을 사용한다.
        /*
        * Clamis 의 속성
        *   iss: 토큰 발급자, sub: 토큰 제목, aud: 토큰 대상자
        *   exp: 토큰 만료 시간, nbf: Not before 토큰의 활성 날짜 (이 날짜가 지나기 전까진 토큰이 처리되지 않음)
        *   iat: 토큰이 발급된 시간 (토큰의 수명이 얼마나 되었는지 판단 가능)
        *   jti: JWT의 고유 식별자
        */
        // Claims의 메서드를 이용하여 sub에 userID 값 추가
        Claims claims = Jwts.claims().setSubject(userUid);
        // 사용자의 권한을 확인하기 위해 claims의 role 속성을 추가
        claims.put("roles", roles);
        Date now = new Date();

        // Jwts.builder를 이용해 토큰 인스턴스를 생성
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilliSecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    // 필터에서 인증이 성공했을 때, SecurityContextHolder에 저장할 Authentication을 생성하는 역할의 메서드
    public Authentication getAuthentication(String token) {
        // Authentication을 구현하는 가장 간단한 방법은
        // UsernamePasswordAuthenticationToken을 이용하는 것

        // getUsername 메서드를 이용하여
        // username를 리턴 받아서 userDetailsService를 이용하여 userDetails 인스턴스를 구성함

        // UserDetails 인터페이스는 사용자의 정보를 불러오기 위해서 구현해야하는 인터페이스이다
        // 다음과 같은 메서드가 존재:
        //   계정 권한 목록을 리턴
        //   계정의 고유한 값을 리턴
        //   계정의 만료 여부 확인
        //   계정의 잠김 여부 리턴
        //   비밀번호 만료 여부 리턴
        // 계정의 활성화 여부 리턴


        /* UserDetailsSerivce.
        *  Spring Security에서 유저의 정보를 가져오는 인터페이스
        *   loadUserByUsername 메서드를 구현해야 함*/
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));


        // UsernamePasswordAuthenticationToken은 AbstractAuthenticationToken을 상속 받고 있음
        // AbstractAuthenticationToken은 Authentication 인터페이스의 구현체
        // 이 토큰 클래스를 사용하려면 초기화를 위한 UserDetails가 필요하다
        // SecurityContextHolder.getContext()에 등록될 인스턴스를 생성
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

    private String getUsername(String token) {
        // Claims의 Body에서 sub를 참조하여 아이디 가져오기
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return info;
    }


    // HttpServletRequest를 파라미터로 받아 헤더 값으로 전달된 "X-AUTH-TOKEN" 값을 가져와 리턴하는 메서드
    // 클라이언트가 헤더를 통해 애플리케이션 서버로 JWT 토큰 값을 전달해야 정상적인 추출이 가능
    public String resolveToken(HttpServletRequest request) {
        return  request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰을 전달 받아 클레임의 유효기간을 체크하고 Boolean 타입의 값을 리턴하는 역할을 한다.
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


}
