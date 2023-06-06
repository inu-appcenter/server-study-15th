package com.example.todolist.dto.userdto;

import com.example.todolist.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;

@Getter
public class UserRequestDto {

    @Getter
    @Setter
    public static class UserJoinReqDto {
        private String name;
        private String password;
        private String email;

        private String roles;

        public User changeEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
            return User.builder()
                    .name(name)
                    .password(bCryptPasswordEncoder.encode(password))
                    .email(email)
                    .roles(roles)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UserLoginReqDto {
        private String name;
        private String password;


    }

    @Getter
    @Setter
    public static class UserEditReqDto {
        private String name;
        private String password;
        private String email;
    }
}