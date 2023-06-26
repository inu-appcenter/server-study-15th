package appCenter.guCoding.todoList.dto.user;

import appCenter.guCoding.todoList.domain.user.User;
import appCenter.guCoding.todoList.domain.user.UserEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {

    @Getter
    @Setter
    public static class LoginReqDto {
        @Schema(example = "ssar")
        private String username;
        @Schema(example = "1234")
        private String password;
    }

    @Getter
    @Setter
    public static class JoinReqDto {

        @Schema(example = "ssar")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요.")
        @NotEmpty
        private String username;

        @Schema(example = "1234")
        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;

        @Schema(example = "ssar@nate.com")
        @Email
        @NotEmpty
        private String email;


        public User toEntity(BCryptPasswordEncoder bcryptPasswordEncoder) {
            return User.builder()
                    .username(username)
                    .password(bcryptPasswordEncoder.encode(password))
                    .email(email)
                    .role(UserEnum.USER)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UserEditReqDto {
        @Schema(example = "ssar2")
        private String username;
        @Schema(example = "12345")
        private String password;
        @Schema(example = "ssar2@nate.com")
        private String email;

    }
}
