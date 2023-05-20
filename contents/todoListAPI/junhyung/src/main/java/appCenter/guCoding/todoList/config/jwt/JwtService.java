package appCenter.guCoding.todoList.config.jwt;

import appCenter.guCoding.todoList.config.auth.LoginUser;
import appCenter.guCoding.todoList.domain.user.User;
import appCenter.guCoding.todoList.domain.user.UserEnum;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.token_prefix:null}")
    private String TOKEN_PREFIX;

    @Value("${jwt.expiration_time:null}")
    private Integer EXPIRATION_TIME;

    // 토큰 생성
    public String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("todoList")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7  )) // 타입조심
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole().name())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwtToken;
    }

    // 토큰 검증 (return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정)
    public LoginUser verify(String token) {

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).role(UserEnum.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
}
