package com.example.todolist.config.resolver;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todolist.config.jwt.JwtProperties;
import com.example.todolist.domain.user.User;
import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.ErrorCode;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


import static com.example.todolist.dto.userdto.UserRequestDto.*;
import static com.example.todolist.exception.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == UserId.class;
    }

    @Override
    public UserId resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            String jwtToken = webRequest.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
            User user = userRepository.findByUserName(username).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));

            UserId userId = new UserId(user.getId());

            return userId;
        } catch (Exception e) {
            log.info("UserIdArgumentResolver.resolveArgument() : {}", e.getMessage());
            return null;
        }
    }
}
