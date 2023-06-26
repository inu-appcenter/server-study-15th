# Appcenter server study 15기
> Since 2023.04.14

## 👨‍💻 운영진

<p>
    <a href="https://github.com/Juser0">
      <img src="https://avatars.githubusercontent.com/u/108407945?v=4" width="100">
    </a>
</p>

## 👨‍💻  스터디원

<p>
	<a href="https://github.com/rnwnsgud">
      <img src="https://avatars.githubusercontent.com/u/78197563?v=4" width="100">
    </a>
    <a href="https://github.com/wellbeing-dough">
      <img src="https://avatars.githubusercontent.com/u/102784323?v=4" width="100">
    </a>
    <a href="https://github.com/NARUBROWN">
      <img src="https://avatars.githubusercontent.com/u/38902021?v=4" width="100">
    </a>
    <a href="https://github.com/elyudwo">
      <img src="https://avatars.githubusercontent.com/u/97587573?v=4" width="100">
    </a>
	<a href="https://github.com/jum0624">
      <img src="https://avatars.githubusercontent.com/u/76554385?v=4" width="100">
    </a>
</p>

---

## 📘 스터디 진행 내용
- [개념 정리](contents/concepts/index.md)
- TodoList API
	- [구준형](contents/todoListAPI/junhyung/README.md)
	- [김동우](contents/todoListAPI/dongwoo/README.md)
	- [김원정](contents/todoListAPI/wonjeong/README.md)
	- [이영재](contents/todoListAPI/yeongjae/README.md)
	- [지은미](contents/todoListAPI/eunmi/README.md)

---

## 📝 규칙

- `커밋 컨벤션`

    - Feat: 새로운 기능 추가
    - Fix: 버그 수정
    - Docs: 문서 수정
    - Style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - Refactor: 코드 리팩토링
    - Test: 테스트 코드, 리팩토링 테스트 코드 추가
    - Chore: 빌드 업무 수정, 패키지 매니저 수정
<br><br>
      
- `issue 규칙`
    - 참고: [https://velog.io/@junh0328/협업을-위한-깃허브-이슈-작성하기](https://velog.io/@junh0328/%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B9%83%ED%97%88%EB%B8%8C-%EC%9D%B4%EC%8A%88-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0)
    - 레이블 참고:
      [https://github.com/modolee/github-initial-settings](https://github.com/modolee/github-initial-settings)
    - 제목 참고: [https://doublesprogramming.tistory.com/256](https://doublesprogramming.tistory.com/256)
      <br><br>
    - 템플릿
        - issue 제목
            - 예시: **[Feat] 이슈 정리**
        - issue 템플릿

            ```markdown
            ## 📋 이슈 내용
            
            ## ✅ 체크리스트
            
            ## 📚 레퍼런스
            
            ```
        - 제목 예시
            - [Add] UI button 구현
    <br><br>
- `branch 규칙`
    - 각자의 영어 이름을 딴 branch 명을 사용한다.
    - 예시: 
    ```
  git checkout -b <브랜치명>      
  git checkout -b juwon
    ```
    
- `commit message 규칙`
    - 참고: [https://doublesprogramming.tistory.com/256](https://doublesprogramming.tistory.com/256)
    - [종류] 메시지 - #이슈번호
    - 예시
        - [Feat] todo-list 회원 API 엔티티 구현 - #2
        - [Fix] todo-list 회원 단건 조회 서비스 에러 수정 - #2
    <br><br>
- `PR 규칙`
    - PR 템플릿

        ```markdown
        ## 📋 이슈 번호
        
        ## 🛠 구현 사항
        
        ## 📚 기타
        
        ```
---

## 📚 스터디 주제

### Server Concept
- ##### 1주차 ~ 2023-04-13 (목)

- 발표 내용
1. 서버의 개념과 동작원리
    1. 서버란 무엇인가?
    2. 서버의 동작원리는 무엇인가?
    3. Spring에서의 동작원리는 어떻게 되는가?
        1. Spring mvc
        2. Spring webflux (선택사항)
        
2. Spring과 Spring boot의 차이
    1. Spring framework란?
        1. IoC (제어 역전)
        2. DI (의존성 주입)
        3. AOP (관점 지향 프로그래밍)
    2. Spring boot란?
    3. Spring vs Spring boot
    
3. REST API (+ GraphQL (optional))
    1. API란
    2. REST API란? RESTful?
    3. REST API의 규칙
    4. GraphQL (선택사항)
        1. REST API와 GraphQL의 차이점
        
4. Spring Web Layer
    1. web layer
    2. service layer
    3. repository layer
    4. etc (dtos, domain model)

5. Spring Singleton Container
    1. 싱글톤 패턴이란?
    2. 싱글톤 패턴을 구현하는 여러가지 방법
    3. 스프링에서의 사례

### todo-list ERD & API Docs
- ##### 2주차 2023-04-14 (금) - 2023-04-27 (목)

- 발표 내용
1. About ERD
    1. ERD란?
    2. ERD의 요소들 설명
        1. 개체
        2. 관계
            1. 식별관계
            2. 비식별관계
        3. 매핑
    3. ERD의 형태 (선택사항)
    4. 본인의 ERD 설명하기
    
2. Domain Layer 구현
    1. Domain Layer란?
    2. ERD와 비교하여 구현된 형태 보여주기
    3. Domain Layer에서 사용된 annotation 설명하기
    
3. API 명세서 구현
    1. API 명세서 설명
    2. 본인의 API가 RESTful 한지 직접 체크해보기
    3. PATCH와 PUT 중 어떤 메소드를 명세서에 작성하였는 지 (이유 필수)
        1. 멱등성

4. Repository Layer 구현
    1. Repository Layer란?
    2. JPA repository 구현 설명
    3. JPA repository 추가하는 방법

5. Repository Layer 구현 - 2
    1. JPA 없이 repository 구현 - 검색만 해서 추가해주세요 (직접 구현할 필요 X)
    2. ORM이란?
        1. JDBC
        2. JPA
        3. Hibernate
        4. Mybatis (선택사항)

- 진행 내용
	- ERD 작성
	- API 명세서 작성
	- Domain Layer 구현
	- Repository Layer 구현		
	- DB 연동

---

### todo-list Service & Controller Layer
- ##### 3주차 2023-04-28 (금) - 2023-05-04 (목)

- 발표 내용
1. Service Layer
    1. Service Layer?
    2. Service Layer 구현한 내용 설명
    
2. Controller Layer
    1. Controller Layer?
    2. Controller Layer에서 사용되는 annotation 설명
    3. Controller Layer 구현한 내용 설명

- 진행 내용
	- Service Layer 구현
	- Controller Layer 구현
	- Swagger (or Postman) 실제 화면 시연

---

### todo-list 1차 배포
- ##### 4주차 2023-05-05 (금) - 2023-05-11 (목)

- 진행 내용
	- 구현되지 못했거나 오류가 있던 부분 개선
	- todo-list에 대한 CRUD API 구현
	- 원하는 방식으로 배포 진행 (EC2, 앱센터 서버 등)

---

### Spring Security + JWT Login 구현
- ##### 5, 6주차 2023-05-12 (금) - 2023-06-01 (목)

- 발표 내용
	- 세션과 쿠키의 차이
	- JWT란?
	- Spring Security란?
	- Spring Security & JWT 구현 과정

- 진행 내용
	- Spring Security + JWT로 로그인 구현하기!

---

### Validation & Exception Handling
- ##### 7주차

- 진행 내용
	- 유효성 검사 로직 추가
	- 예외처리 구현
	- 각자 구현한 내용에 대한 설명 및 테스트 진행
