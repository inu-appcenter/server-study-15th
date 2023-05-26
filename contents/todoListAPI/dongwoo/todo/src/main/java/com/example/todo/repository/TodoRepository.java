package com.example.todo.repository;

import com.example.todo.domain.Todo;
import com.example.todo.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Slice<Todo> findSliceByUser(Pageable pageable, User findUser);

}
