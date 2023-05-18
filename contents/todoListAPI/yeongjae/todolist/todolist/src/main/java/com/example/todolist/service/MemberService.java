package com.example.todolist.service;

import com.example.todolist.domain.User;
import com.example.todolist.dto.userdto.UserRequestDto;
import com.example.todolist.dto.userdto.UserResponseDto;
import com.example.todolist.dto.userdto.UserResponseDto.UserJoinRespDto;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.todolist.dto.userdto.UserRequestDto.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public UserJoinRespDto join(UserJoinReqDto userJoinReqDto) {
        User user = memberRepository.save(userJoinReqDto.changeEntity(userJoinReqDto));
        return new UserJoinRespDto(user);
    }
}
