package com.appcenter.data.repository;

// Spring Data JPA가 제공하는 인터페이스
// 엔티티가 생성한 데이터베이스에 접근하는 역할

import com.appcenter.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 접근하려는 테이블과 매핑되는 엔티티에 대한 인터페이스를 생성하고, JpaRepository를 상속받으면 된다.
// <대상 엔티티, 엔티티의 @Id 필드타입>
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
