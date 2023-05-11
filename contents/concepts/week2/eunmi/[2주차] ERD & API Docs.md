# [2주차] ERD & API Docs

복습: No
작성일시: 2023년 4월 27일 오후 10:57

# 01 todo-list 설계

## 1) 요구사항 명세서

- **회원기능**
    - 회원등록
        - 사용자이름, 아이디, 비밀번호 입력 받음
        - 회원은 해당 유저에 부여된 pk값으로 식별
    - 회원조회
        - 모든 회원 조회
        - 해당 회원 조회
    - 회원수정
        - 사용자 이름과 비밀번호만 변경가능
    - 회원삭제
        - 해당 유저에 부여된 pk값 이용하여 삭제
        - 유저가 삭제되면 해당 유저의 todo 목록을 전체 삭제
- **todo 기능**
    - 조회기능
        - 회원 pk에 해당하는 todo 리스트를 조회하므로 회원에서 todo를 참조할 수 있어야함.
        - 각 회원에 해당하는 todo-list 조회
    - 등록기능
        - content, 생성일, 수행여부
        - 수행 여부는 초기값 false
        - 한명의 회원은 여러 개의 todo를 등록할 수 있음 → 회원:todo = (1:N)
    - 수정기능
        - content와 수행 여부 수정가능
    - 삭제기능
        - 하나의 todo 삭제
        - 모든 todo 삭제

## 2) ERD 작성

![Untitled](./%5B2주차%5D%20ERD%20&%20API%20Docs/Untitled.png)

## 3) API 명세서

[Todo-List API 명세서](https://www.notion.so/e73f3ac2964f4d00866985d6dabab171)

# 02 Domain Layer 구현

## 1) Domain Layer란?

- DB의 테이블과 매칭될 클래스
- Entity 클래스라고도 불림
- 다만, 무조건 데이터베이스의 테이블과 관계가 있어야 하는 것은 아님
- 도메인 모델은 아키텍처 상의 도메인 계층을 객체 지향 기법으로 구현하는 패턴을 말함
    - **핵심 규칙을 구현한 코드는 도메인 모델에만 위치**하기 때문에 규칙이 바뀌거나 규칙을 확장할 때 다른 코드에 영향을 덜 주고 변경 내역을 모델에만 반영할 수 있게 된다.

> `**'도메인 모델'**`이란 용어는 도메인 자체를 표현하는 개념적인 모델을 의미하지만,
*도메인 계층을 구현할 때 사용하는 객체 모델을 언급할 때*에도 '도메인 모델'이란 용어를 사용한다.
> 

## 2) Annotation

우선, **@Getter 와 @Setter**는 각각 접근자와 설정자 메소드를 작성해주는 Lombok 어노테이션으로 유명하다. 또한 생성자를 자동 생성해주는 Lombok 어노테이션에는

- @NorgsConstructor : 파라미터가 없는 기본 생성자 생성
- @AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 만들어 준다.

### 자주 사용하는 ****JPA 어노테이션****

- 객체 - 테이블 맵핑 : @Entity, @Table
- 필드 - 컬럼의 팹핑 : @Column
- 기본키의 맵핑 : @Id
- 조인 맵핑 : @ManyToOne, @JoinColumn

### 1) @Entity

- @Entity 어노테이션을 클래스에 선언하면 그 클래스는 JPA가 관리한다. 그러므로 DB의 테이블과 Class(VO, DTO)와 맵핑한다면 반드시 @Entity를 붙여주어야 한다.
- @Entity가 붙은 클래스에는 다음 제약사항이 필요하다.
    - 필드에 final, enum, interface, class를 사용할 수 없다.
    - 생성자중 기본 생성자가 반드시 필요하다.
- @Entity의 속성
    - **name**
        - 엔티티 이름을 지정합니다. 기본값으로 클래스 이름을 그대로 사용한다.

### 2) @Table

- @Table 어노테이션은 맵핑할 테이블을 지정한다.
- @Table의 속성
    - **name**
        - 매핑할 테이블의 이름을 지정
    - **catalog**
        - DB의 catalog를 맵핑
    - **schema**
        - DB 스키마와 맵핑
    - **uniqueConstraint**
        - DDL 쿼리를 작성할 때 제약 조건을 생성

### @Column

- @Column 어노테이션은 객체 필드와 DB 테이블 컬럼을 맵핑한다.
- @Column의 속성
    - name
        - 맵핑할 테이블의 컬럼 이름을 지정
    - insertable
        - 엔티티 저장시 선언된 필드도 같이 저장
    - updateable
        - 엔티티 수정시 이 필드를 함께 수정
    - table
        - 지정한 필드를 다른 테이블에 맵핑
    - **nullable**
        - NULL을 허용할지, 허용하지 않을지 결정
    - unique
        - 제약조건을 걸 때 사용
    - columnDefinition
        - DB 컬럼 정보를 직접적으로 지정할 때 사용
    - length
        - varchar의 길이를 조정합니다. 기본값으로 255가 입력
    - precsion, scale
        - BigInteger, BigDecimal 타입에서 사용, 각각 소수점 포함 자리수, 소수의 자리수를 의미

### @Id

JPA가 객체를 관리할 때 식별할 기본키를 지정한다.

### @GeneratedValue

- 자동할당
- IDENTITY 전략
    - IDENTITY 전략은 기본키 생성을 데이터베이스에 위임한다.
    - jpa 입장에서는 엔티티를 영속 상태에 만들기 위해선, 기본키를 먼저 알아야한다.
    - **따라서 em.persist() 시점에 쓰기 지연을 하지않고 바로 쿼리를 DB에 날려 데이터를 등록하고 식별자를 리턴받는다.**
    - 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용 (예: MySQL의 AUTO_ INCREMENT)

| 속성값 | 설명	 | 대표DBMS
 |
| --- | --- | --- |
| strategy = GenerationType.IDENTITY | 기본키 생성을 데이터베이스에 위임. | MYSQL |
| strategy = GenerationType.SEQUENCE | 시퀀스 사용, @SequenceGenerator 필요(시퀀스 생성) | ORACLE |
| strategy = GenerationType.TABLE | 키 생성용 테이블 사용, @TableGenerator 필요 | 모든 DBMS |
| strategy = GenerationType.AUTO | 데이터베이스 방언에 따라 자동 지정(기본값) |  |

### @**JoinColumn**

- **외래 키**를 매핑할 때 사용
- name 속성에는 매핑할 **외래 키 컬럼명(이름)을 지정**

### **mappedBy**

- **연관관계의 주인을 지정**해주는 속성
- 다음 문장을 실행한다고 할 수 있습니다.

<aside>
📌 나는 내 연관관계의 주인의 [~~~] 필드에 해당해!

</aside>

- 위의 표현에서 ~~~에 들어올 값을 지정해주면 된다.

### **특징**

- 연관관계의 **주인**은 mappedBy 속성을 **사용하지 않습니다**.
- 주인이 아니라면 **mappedBy** 속성을 사용해서 **주인이 아님을 설정**합니다. 속성의 값으로는 **연관관계의 주인을 지정**해줍니다.
- mappedBy 속성에 들어올 이름은, **연관관계 주인의 해당 속성의 필드명과 일치**해야 합니다

<aside>
📌 데이터베이스 테이블의 다대일, 일대다 관계에서는 항상 다 쪽이 외래키를 가집니다.

따라서 항상 다(N)쪽이 연관관계의 주인이 되므로 @ManyToOne에는 mappedBy 속성이 없습니다.

</aside>

### Cascade

- cascade 옵션이란 @OneToMany 나 @ManyToOne에 옵션으로 줄 수 있는 값이다.
- Entity의 상태 변화를 전파시키는 옵션이다.
- 만약 Entity의 상태 변화가 있으면 연관되어 있는(ex. @OneToMany, @ManyToOne) Entity에도 상태 변화를 전이시키는 옵션이다.
- 기본적으로는 아무 것도 전이시키지 않는다.
