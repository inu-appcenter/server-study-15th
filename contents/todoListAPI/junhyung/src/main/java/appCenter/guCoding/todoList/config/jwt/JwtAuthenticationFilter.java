package appCenter.guCoding.todoList.config.jwt;

import appCenter.guCoding.todoList.config.auth.LoginUser;
import appCenter.guCoding.todoList.util.CustomResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static appCenter.guCoding.todoList.dto.user.UserReqDto.*;
import static appCenter.guCoding.todoList.dto.user.UserRespDto.*;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    // 사용자 인증 관리하는 역할
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${jwt.token_prefix:null}")
    private String TOKEN_PREFIX;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // post : setFilterProcessesUrl -> /api/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("디버그 : attemptAuthentication 호출됨");

        try {
            // req 의 json 데이터 꺼내기
            ObjectMapper om = new ObjectMapper();
            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

            // 사용자 인증 정보 담고있는 토큰
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(), loginReqDto.getPassword());

            // UserDetailsService loadByUsername 호출해서 사용자 조회 후 임시로 세션을 만듬 -> successfulAuthentication 에 임시세션 전달(리턴되면사라짐)
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        } catch (Exception e) {
            // unSuccessfulAuthentication 간접적으로 호출
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("디버그 : successfulAuthentication 호출됨");

        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = jwtService.create(loginUser);
        response.addHeader("Authorization", jwtToken);
        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser(), jwtToken);

        CustomResponseUtil.success(response, loginRespDto);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response, "로그인실패", HttpStatus.UNAUTHORIZED);
    }
}
