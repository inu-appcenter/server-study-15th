package appCenter.guCoding.todoList.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
public class AuthenticationMangerConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 등록");
        return new BCryptPasswordEncoder();
    }

    // PasswordEncoder 의존하고 있음
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.debug("디버그 : authenticationManager 등록");
        return authenticationConfiguration.getAuthenticationManager();
    }
}
