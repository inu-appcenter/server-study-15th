package com.example.todolist.config;

import com.example.todolist.config.jwt.JwtAuthenticationFilter;
import com.example.todolist.config.jwt.JwtAuthorizationFilter;
import com.example.todolist.domain.user.UserEnum;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final CorsConfig corsConfig;
        private final UserRepository userRepository;

        @Bean
        public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .apply(new MyCustomDsl())
                    .and()
                    .authorizeRequests()
                    .antMatchers("/users/login", "/users/join").permitAll()
                    .antMatchers("/users/all").hasRole(UserEnum.ADMIN.name())
                    .antMatchers("/todos/**", "/users/**").hasAnyRole(UserEnum.USER.name(), UserEnum.ADMIN.name())
                    .anyRequest().permitAll()
                    .and().build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }
}