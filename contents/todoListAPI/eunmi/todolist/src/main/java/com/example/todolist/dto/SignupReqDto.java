package com.example.todolist.dto;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignupReqDto {

    private String name;

    @Size(min = 5, max = 20)
    @NotBlank(message = "사용하실 아이디를 입력해주세요.")
    private String userName;


    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private String role;

    @Builder
    public SignupReqDto(String name, String userName, String password, String email, String role) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Member toMember(SignupReqDto signupReqDto, String encodingPassword) {
        return Member.builder()
                .name(signupReqDto.getName())
                .userName(signupReqDto.getUserName())
                .password(encodingPassword)
                .email(signupReqDto.getEmail())
                .role(Role.valueOf(signupReqDto.getRole()))
                .build();
    }

}
