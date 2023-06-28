package com.example.todolist.repository;

import com.example.todolist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.name = :username")
    Optional<User> findByUserName(@Param("username") String username);
}
