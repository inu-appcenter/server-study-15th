package com.example.todolist.controller;

import com.example.todolist.repository.MemberRepository;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;



}