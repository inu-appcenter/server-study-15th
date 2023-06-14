package com.example.todolist.service;

import com.example.todolist.domain.user.User;
import com.example.todolist.dto.userdto.UserResponseDto.UserJoinRespDto;
import com.example.todolist.exception.user.UserNotFoundException;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.todolist.dto.userdto.UserRequestDto.*;
import static com.example.todolist.dto.userdto.UserResponseDto.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserJoinRespDto join(UserJoinReqDto userJoinReqDto) {
        User user = userRepository.save(userJoinReqDto.changeEntity(bCryptPasswordEncoder));
        return new UserJoinRespDto(user);
    }

    @Transactional
    public UserEditRespDto editUser(Long id, UserEditReqDto userEditReqDto, UserId userId) {
        checkIdCorrect(id, userId.getId());
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.editByDto(userEditReqDto);
            return new UserEditRespDto(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public UserDeleteRespDto deleteUser(Long id, UserId userId) {
        checkIdCorrect(id, userId.getId());

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            return new UserDeleteRespDto(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public UserFindRespDto findUser(Long id, UserId userId) {
        checkIdCorrect(id, userId.getId());

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserFindRespDto(user);
        } else {
            throw new UserNotFoundException();
        }
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