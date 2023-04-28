# 2주차 Repository Layer의 구현 (without JPA)

| 📝 작성일 | 👨🏻‍🏫 소속 | 🙋🏻‍♂️ 이름 |
| --- | --- | --- |
| 2023년 4월 28일 | 인천대학교 컴퓨터공학부 | 김원정 |

# Repository Layer가 뭐였지?

---

- 데이터베이스에 접근해야 하는 작업을 수행
- ORM(Mybatis, Hibernate)를 주로 사용하는 계층
- 데이터베이스에 접근해야 하는 작업을 수행
- DAO 인터페이스와 @Repositiory 어노테이션을 사용하여 작성된 DAO 구현 클래스가 이 계층에 속함

## DAO와 Repository

- DAO와 Repository 모두 데이터베이스에 대한 접근을 추상화 해주는 역할 (Data Aceess Layer)
- DAO는 데이터저장소 중심의 개념
- Repository는 객체 중심 개념

## DAO로 구현된 Project 살펴보기

# JDBC (Java Database Connectivity)

---

자바에서 DB와 연결되어 데이터를 주고 받을 수 있도록 하기 위해 제공하는 API이다.
자바의 모든 데이터 접근은 내부적으로 JDBC API를 이용하게 된다.

## CP (Connection Pool)

일반적으로 데이터의 연동과정은 웹 애플리케이션이 필요할 때마다 데이터베이스에 연결하여 작업하는 방식이다.
하지만 이런 식으로 작업할 경우 데이터베이스 연결에 시간이 많이 걸리는 문제가 발생한다.
이 문제를 해결하기 위해 웹 애플리케이션이 실행됨과 동시에 연동할 데이터베이스와의 연결을 미리 설정해 둔다.
이렇게 미리 데이터베이스와 연결시킨 상태를 유지하는 기술을 Connection Pool이라고 한다.

## HikariCP

Springboot 2.0 이전에는 tomcat-jdbc를 사용했지만, 2.0 이후부터는 HikariCP를 기본 옵션으로 채택하고 있다.

# 패러다임의 불일치

---

## **객체지향 언어**

필드와 메서드등을 클래스로 캡슐화 해 객체로써 사용한다.

## **관계형 데이터베이스**

데이터 항목 간에 사전 정의를 통해, 데이터를 나누어 저장

객체지향 언어와 관계형 데이터 베이스가 지향하는 바가 다르니 문제가 생기게 된다.
DB에는 추상화, 상속등의 장치가 존재하지 않아 데이터를 나누어 저장해야 하는 일이 발생한다.

# ORM(Object Relational Mapping)

---

객체와 RDB의 데이터를 자동으로 매핑하여, 객체의 관계를 바탕으로 데이터베이스를 조작해주는 것.
객체와 RDB, 각자의 패러다임대로 설계할 수 있게 도와줌.

- **장점**
    - ORM을 사용하면서 데이터베이스 쿼리를 객체지향적으로 조작할 수 있다.
        - 객체지향적으로 데이터베이스에 접근할 수 있어 코드의 가독성을 높인다.
        - 쿼리문을 작성하는 양이 현저히 줄어 개발 비용이 줄어들게 된다.
    - 재사용 및 유지보수가 편리하다
        - ORM을 통해 매핑된 객체는 모두 독립적으로 작성되어있어 재사용이 용이
        - 객체들은 클래스와 나뉘어 있어 유지보수가 수월함
    - 데이터베이스에 대한 종속성이 줄어들게 된다.
- **단점**
    - 복잡한 서비스의 경우 직접 쿼리를 구현하지 않고 코드로 구현하기 어렵다.
    - 복잡한 쿼리를 정확한 설계 없이 ORM만으로 구성하게 되면 속도 저하 등의 성능 문제가 발생된다.
    - 애플리케이션의 객체 관점과 데이터베이스의 관계 관점의 불일치가 발생한다.

## JPA(Java Persistence API)

JPA는 **자바 진영의 ORM 기술 표준**으로 채택된 **인터페이스의 모음**이다.
JPA는 내부적으로 JDBC를 사용한다. 개발자가 직접 JBDC를 구현하면 SQL에 의존하게 되는 문제가 있어 개발됨 효율성이 떨어지지만, JPA는 개발자 대신 데이터베이스를 조작하여 객체를 자동 매핑하는 역할을 수행한다.

<aside>
⚠️ JPA는 라이브러리나 구현체가 아니다. 인터페이스란걸 기억하자!

</aside>

### Hibernate

하이버네이트는 자바의 ORM 프레임워크로 JPA가 정의하는 인터페이스를 구현하고 있는 JPA 구현체중 하나

### Spring Data JPA

Spring Data JPA는 JPA를 편리하게 사용할 수 있도록 지원하는 스프링 하위 프로젝트 중 하나.
CRUD 처리를 위한 공통 인터페이스를 제공한다. Repository 개발시 인터페이스만 작성하면 Spring Data JPA가 구현 객체를 동적으로 생성해서 주입.

<aside>
➕ Spring Data JPA는 JPA를 한 단계 추상화 시켰다.
Spring Data JPA의 Repository 구현에서 JPA를 사용하고 있다.

</aside>

## Mybatis

Object Mapping 기술을 통해서 구현됨. SQL문을 작성하고 매핑하는 형태

### Mybatis를 사용한 Spring Project 살펴보기

**참고자료**

[은미님: 1주차 Spring Web Layer - 레포지토리 계층](https://github.com/inu-appcenter/server-study-15th/blob/main/contents/concepts/week1/eunmi/1week.md)

[DAO와 Repository의 차이점 | Dev.Isaac (isaac56.github.io)](https://isaac56.github.io/etc/2021/08/29/difference_DAO_Repository/)

[JDBC, JPA, ORM에 대한 이해 (velog.io)](https://velog.io/@u-nij/JDBC-JPA-ORM%EC%97%90-%EB%8C%80%ED%95%9C-%EC%9D%B4%ED%95%B4)

[JPA, Hibernate, 그리고 Spring Data JPA의 차이점 (suhwan.dev)](https://suhwan.dev/2019/02/24/jpa-vs-hibernate-vs-spring-data-jpa/)

[[Spring] DB커넥션풀과 Hikari CP 알아보기 (velog.io)](https://velog.io/@miot2j/Spring-DB%EC%BB%A4%EB%84%A5%EC%85%98%ED%92%80%EA%B3%BC-Hikari-CP-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)

[[MyBatis] MyBatis란? 개념 및 데이터구조 :: 히진쓰의 서버사이드 기술 블로그 (tistory.com)](https://khj93.tistory.com/entry/MyBatis-MyBatis%EB%9E%80-%EA%B0%9C%EB%85%90-%EB%B0%8F-%ED%95%B5%EC%8B%AC-%EC%A0%95%EB%A6%AC)

스프링부트 핵심가이드 - ORM