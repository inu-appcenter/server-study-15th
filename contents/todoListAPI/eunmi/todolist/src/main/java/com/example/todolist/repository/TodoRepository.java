package com.example.todolist.repository;

import com.example.todolist.domain.Member;
import com.example.todolist.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findPageByMember(Member member, Pageable pageable);
}
