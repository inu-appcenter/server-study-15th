package com.example.todolist.service;

import com.example.todolist.domain.user.User;
import com.example.todolist.dto.userdto.UserResponseDto.UserJoinRespDto;
import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.user.UserNotFoundException;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.todolist.dto.userdto.UserRequestDto.*;
import static com.example.todolist.dto.userdto.UserResponseDto.*;
import static com.example.todolist.exception.ErrorCode.USER_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserJoinRespDto join(UserJoinReqDto userJoinReqDto) {
        User user = userRepository.save(userJoinReqDto.changeEntity(bCryptPasswordEncoder));
        @Valid final UserJoinRespDto userJoinRespDto = new UserJoinRespDto(user);

        return userJoinRespDto;
    }

    @Transactional
    public UserEditRespDto editUser(Long id, UserEditReqDto userEditReqDto, UserId userId) {
        checkIdCorrect(id, userId.getId());
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));
        user.editByDto(userEditReqDto);

        @Valid final UserEditRespDto userEditRespDto = new UserEditRespDto(user);

        return userEditRespDto;
    }

    @Transactional
    public UserDeleteRespDto deleteUser(Long id, UserId userId) {
        checkIdCorrect(id, userId.getId());
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));

        userRepository.delete(user);
        @Valid final UserDeleteRespDto userDeleteRespDto = new UserDeleteRespDto(user);

        return userDeleteRespDto;
    }

    @Transactional(readOnly = true)
    public UserFindRespDto findUser(Long id, UserId userId) {
        checkIdCorrect(id, userId.getId());
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND_EXCEPTION));

        @Valid final UserFindRespDto userFindRespDto = new UserFindRespDto(user);

        return userFindRespDto;
    }

    @Transactional(readOnly = true)
    public List<UserFindRespDto> findAll() {
        List<User> list = userRepository.findAll();
        List<UserFindRespDto> userFindRespDtoList = new ArrayList<>();

        for(User user : list) {
            UserFindRespDto userFindRespDto = new UserFindRespDto(user);
            userFindRespDtoList.add(userFindRespDto);
        }
        return userFindRespDtoList;
    }

    public void checkIdCorrect(Long parameterId, Long userId) {
        if(!parameterId.equals(userId)) {
            throw new UserNotFoundException();
        }
    }
}