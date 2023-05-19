package com.example.todolist.service;

import com.example.todolist.domain.Member;
import com.example.todolist.dto.MemberReqDto;
import com.example.todolist.dto.MemberPageRespDto;
import com.example.todolist.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원 저장 테스트")
     void 회원_저장() {
        //given
        MemberReqDto memberReqDto = MemberReqDto.builder()
                .name("spring")
                .nickName("springBoot")
                .email("spring@naver.com")
                .build();

        //when
        Long save = memberService.save(memberReqDto);

        //then
        Member member = memberRepository.findById(save).orElse(null);
        Assertions.assertThat(memberReqDto.getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("회원 닉네임 중복 저장 에러 테스트")
    void 회원_닉네임_중복_저장() {
        //given
        MemberReqDto memberReqDto = MemberReqDto.builder()
                .name("member1")
                .nickName("springBoot")
                .email("spring@naver.com")
                .build();

        //when
        Long save = memberService.save(memberReqDto);

        //then
        Member member = memberRepository.findById(save).orElse(null);
        Assertions.assertThat(memberReqDto.getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("회원정보 수정 테스트")
    void 회원정보_수정() {
        //given
        MemberReqDto memberReqDto = MemberReqDto.builder()
                .name("member2")
                .nickName("member2")
                .email("member2@naver.com")
                .build();

        //when
        Long update = memberService.update(1L, memberReqDto);

        //then
        Member member = memberRepository.findById(update).orElse(null);
        Assertions.assertThat(memberReqDto.getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("회원정보 id로 조회하기")
    public void 회원정보_id로_조회() throws Exception {
        // given
        MemberReqDto memberReqDto = MemberReqDto.builder()
                .name("member3")
                .nickName("member3")
                .email("spring@naver.com")
                .build();

        Long save = memberService.save(memberReqDto);

        // when
        MemberReqDto result = memberService.findOne(save);

        // then
        Assertions.assertThat(result.getName()).isEqualTo(memberReqDto.getNickName());

    }

    @Test
    @DisplayName("회원정보 전체 조회하기")
    public void 회원정보_전체_조회() throws Exception {
        // given


        // when
        List<MemberPageRespDto> members = memberService.findAll();

        // then
        for (MemberPageRespDto member : members) {
            System.out.println("member.getNickName() = " + member.getNickName());
        }
    }

    @Test
    public void 회원정보_삭제() throws Exception {
        // given
        MemberReqDto memberReqDto = MemberReqDto.builder()
                .name("member4")
                .nickName("member4")
                .email("member4@naver.com")
                .build();

        Long save = memberService.save(memberReqDto);

        // when
        memberService.deleteMember(save);

        // then


    }
}