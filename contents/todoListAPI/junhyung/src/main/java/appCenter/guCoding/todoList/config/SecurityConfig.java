package appCenter.guCoding.todoList.config;

import appCenter.guCoding.todoList.config.jwt.JwtAuthenticationFilter;
import appCenter.guCoding.todoList.config.jwt.JwtAuthorizationFilter;
import appCenter.guCoding.todoList.domain.user.UserEnum;
import appCenter.guCoding.todoList.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 등록");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.cors().configurationSource(configurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();

        // 필터 적용
        http.apply(new CustomSecurityFilterManager());

        // 인증실패 예외 가로채기
        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            CustomResponseUtil.fail(response, "로그인을 진행 해주세요 .", HttpStatus.UNAUTHORIZED);
        });

        // 권한실패 예외 가로채기
        http.exceptionHandling().accessDeniedHandler((request, response, e) -> {
            CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
        });

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN)
                .anyRequest().permitAll();
        return http.build();
    }

    // 필터 등록
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception { // 스프링 시큐리티에서 사용되는 구성을 위한 빌더 클래스
            //            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            //            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(jwtAuthenticationFilter);
            builder.addFilter(jwtAuthorizationFilter);
            super.configure(builder);
        }
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*"); // 모든 ip 주소 허용 (프론트 엔드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트의 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 브라우저에 Authorization 헤더 노출, 클라이언트가 저장하기 위해서
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 주소요청에 위 설정을 넣어주겠다.
        return source;
    }
}
