package com.appcenter.service.impl;

import com.appcenter.data.dto.request.TodolistRequestDTO;
import com.appcenter.data.dto.response.TodolistResponseDTO;
import com.appcenter.data.entity.Member;
import com.appcenter.data.entity.Todolist;
import com.appcenter.data.repository.MemberRepository;
import com.appcenter.data.repository.TodolistRepository;
import com.appcenter.service.TodolistService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodolistServiceImpl implements TodolistService {

    // Repository 상수 선언
    private final TodolistRepository todolistRepository;
    // Repository 상수 선언
    private final MemberRepository memberRepository;

    // 오류 메세지 상수 선언
    private final String NOT_FOUND_CONTENT = "유효하지 않은 게시글 번호입니다.";
    private final String NOT_FOUND_MEMBER = "유효하지 않은 멤버 번호 입니다.";

    @Autowired
    public TodolistServiceImpl(TodolistRepository todolistRepository, MemberRepository memberRepository) {
        this.todolistRepository = todolistRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    // 트랜젝션이 있는데 왜 Exception이 있을까.. 내가 왜 그랬을까..
    // 예외처리 수정할 때 같이 수정하기..
    public TodolistResponseDTO getContent(Long id) throws Exception {
        Todolist todolist = todolistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONTENT));

       return new TodolistResponseDTO().updateTodolistResponse(todolist);
    }

    @Override
    public List<TodolistResponseDTO> getContentsList(Long member_id) throws Exception {
        memberRepository.findById(member_id).orElseThrow(() -> new NotFoundException("존재하지 않는 사용자 입니다."));
        List<Todolist> contentsList = todolistRepository.findByMember_id(member_id);
        return contentsList.stream().map(contents -> contents.toTodolistResponseDTO(contents)).collect(Collectors.toList());
    }

    @Override
    public TodolistResponseDTO savedContent(Long id, TodolistRequestDTO todolistRequestDTO) throws Exception {
        Todolist todolist = new Todolist();

        // 지금 요청한 멤버가 존재하는 멤버인지 확인
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));

        //
        Todolist savedContent = todolistRepository.save(todolist.createContent(findMember, todolistRequestDTO));

        return new TodolistResponseDTO().createTodolistResponse(savedContent);
    }

    @Override
    public TodolistResponseDTO updateContent(Long id, TodolistRequestDTO todolistRequestDTO) throws Exception {
        // id로 존재하는 콘텐츠인지 확인
        Todolist foundContent = todolistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONTENT));

        foundContent.updateContent(todolistRequestDTO);

        Todolist updateContent = todolistRepository.save(foundContent);
        return new TodolistResponseDTO().updateTodolistResponse(updateContent);
    }

    @Override
    public void deleteContent(Long id) throws Exception {
        try {
            todolistRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException(NOT_FOUND_CONTENT);
        }
    }
}
