package appCenter.guCoding.todoList.config.jwt;

import appCenter.guCoding.todoList.config.auth.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter { // 모든 요청에서 동작

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${jwt.header:null}")
    private String HEADER;

    @Value("${jwt.token_prefix:null}")
    private String TOKEN_PREFIX;

    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

//        log.debug("디버그 : doFilterInternal 실행" );
        if (isHeaderVerify(request, response)) {
            log.debug("디버그 : 토큰이 존재함");

            String token = request.getHeader(HEADER).replace(TOKEN_PREFIX, "");

            LoginUser loginUser = jwtService.verify(token);
            log.debug("디버그 : 토큰이 검증이 완료됨");

            // 임시 세션 (UserDetails 타입 or username) id,role 만 존재
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("디버그 : 임시 세션이 생성됨");
        }
        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(HEADER);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}
