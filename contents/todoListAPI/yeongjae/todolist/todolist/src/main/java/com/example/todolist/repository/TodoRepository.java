package com.example.todolist.repository;

import com.example.todolist.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query(value = "select t from Todo t where t.user.id = :id")
    List<Todo> findByUser_id(@Param("id") Long id);

    @Query(value = "select t from Todo t where t.title = :title and t.user.id = :id")
    List<Todo> findByTitle(@Param("title") String title, @Param("id") Long id);
}