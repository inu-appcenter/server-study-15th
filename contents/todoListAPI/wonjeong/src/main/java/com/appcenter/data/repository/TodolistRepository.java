package com.appcenter.data.repository;

import com.appcenter.data.entity.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodolistRepository extends JpaRepository<Todolist, Long> {

}
