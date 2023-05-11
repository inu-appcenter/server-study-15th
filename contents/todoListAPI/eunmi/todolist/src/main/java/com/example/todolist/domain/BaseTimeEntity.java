package com.example.todolist.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass  //JPA Entity 클래스들이 BaseTimeEntity를 상속 할 경우 createdDate, modifiedDate 두 필드도 컬럼으로 인식하도록 설정
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate  // 생성시 날짜 자동 생성
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate  // 수정시 날짜 자동 갱신
    private LocalDateTime modifiedDate;
}
