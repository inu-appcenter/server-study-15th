package appCenter.guCoding.todoList.service;

import appCenter.guCoding.todoList.domain.user.User;
import appCenter.guCoding.todoList.domain.user.UserRepository;
import appCenter.guCoding.todoList.dto.user.UserReqDto.UserEditReqDto;
import appCenter.guCoding.todoList.dto.user.UserReqDto.JoinReqDto;
import appCenter.guCoding.todoList.dto.user.UserRespDto.UserEditRespDto;
import appCenter.guCoding.todoList.dto.user.UserRespDto.JoinRespDto;
import appCenter.guCoding.todoList.handler.ex.CustomApiException;
import appCenter.guCoding.todoList.handler.ex.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 사용자명이 존재합니다.");
        }
        User userPS = userRepository.save(joinReqDto.toEntity(bCryptPasswordEncoder));
        return new JoinRespDto(userPS);
    }

    public UserEditRespDto 사용자정보수정(UserEditReqDto userEditReqDto, String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        if (userOP.isEmpty()) {
            throw new CustomNotFoundException("해당 이름의 사용자가 없습니다.");
        }
        String encPassword = bCryptPasswordEncoder.encode(userEditReqDto.getPassword());
        User user = userOP.get();
        user.changeField(userEditReqDto, encPassword);
        return new UserEditRespDto(user);
    }


}
