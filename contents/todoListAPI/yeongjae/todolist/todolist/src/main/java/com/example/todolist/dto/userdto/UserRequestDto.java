package com.example.todolist.dto.userdto;

import com.example.todolist.domain.user.User;
import com.example.todolist.domain.user.UserEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
public class UserRequestDto {
    public static final int MIN_NAME_VALUE = 5;
    public static final int MAX_NAME_VALUE = 15;
    public static final int MIN_PASS_VALUE = 8;
    public static final int MAX_PASS_VALUE = 20;


    @Getter
    @Setter
    public static class UserJoinReqDto {

        @NotEmpty
        @Size(min = MIN_NAME_VALUE, max = MAX_NAME_VALUE)
        private String name;
        @NotEmpty
        @Size(min = MIN_PASS_VALUE, max = MAX_PASS_VALUE)
        private String password;

        @Email
        @NotEmpty
        private String email;

        private UserEnum roles;

        public User changeEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
            return User.builder()
                    .name(name)
                    .password(bCryptPasswordEncoder.encode(password))
                    .email(email)
                    .role(roles)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UserLoginReqDto {
        @NotEmpty
        @Size(min = MIN_NAME_VALUE, max = MAX_NAME_VALUE)
        private String name;

        @NotEmpty
        @Size(min = MIN_PASS_VALUE, max = MAX_PASS_VALUE)
        private String password;


    }

    @Getter
    @Setter
    public static class UserEditReqDto {
        @NotEmpty
        @Size(min = MIN_NAME_VALUE, max = MAX_NAME_VALUE)
        private String name;
        @NotEmpty
        @Size(min = MIN_PASS_VALUE, max = MAX_PASS_VALUE)
        private String password;
        @Email
        @NotEmpty
        private String email;
    }

    @Getter
    @AllArgsConstructor
    public static class UserId {
        private final Long id;
    }
}