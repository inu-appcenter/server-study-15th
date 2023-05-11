package com.example.todolist.service;

import com.example.todolist.domain.Todo;
import com.example.todolist.dto.MemberDto;
import com.example.todolist.dto.TodoReqDto;
import com.example.todolist.dto.TodoRespDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
class TodoServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TodoService todoService;


    @Test
    public void 할일_등록() throws Exception {
        // given
        MemberDto memberDto = MemberDto.builder()
                .name("test1")
                .nickName("test1")
                .email("test@naver.com")
                .build();

        Long save = memberService.save(memberDto);

        TodoReqDto todoReqDto = TodoReqDto.builder()
                .content("할일 1")
                .creatAt(LocalDateTime.now())
                .checked(false)
                .build();

        // when
        Long result = todoService.save(save, todoReqDto);

        // then
        Assertions.assertThat(result).isEqualTo(1);


    }

    @Test
    public void 할일_수정() throws Exception {
        // given
        TodoReqDto todoReqDto = TodoReqDto.builder()
                .content("할일 2")
                .creatAt(LocalDateTime.now())
                .checked(false)
                .build();

        Long result = todoService.save(6L, todoReqDto);

        // when
        TodoReqDto todoReqDto2 = TodoReqDto.builder()
                .content("할일 3")
                .creatAt(LocalDateTime.now())
                .checked(true)
                .build();

        Long update = todoService.update(result, todoReqDto2);

        // then
        Assertions.assertThat(update).isEqualTo(result);

    }

    @Test
    public void 할일_조회() throws Exception {
        // given


        // when
        TodoRespDto todo = todoService.findOne(4L);

        // then
        Assertions.assertThat(todo.getContent()).isEqualTo("할일 3");

    }

    @Test
    public void 할일_전체_조회() throws Exception {
        // given

        // when
        List<Todo> todos = todoService.findAll(6L);

        // then
        for (Todo todo : todos
        ) {
            System.out.println("todo.getContent() = " + todo.getContent());
        }


    }

}