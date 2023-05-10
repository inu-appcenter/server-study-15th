package com.example.todolist.repository;

import com.example.todolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<User, Long> {

}
