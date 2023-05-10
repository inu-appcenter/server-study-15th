package com.example.todolist.repository;

import com.example.todolist.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Task, Long> {
}