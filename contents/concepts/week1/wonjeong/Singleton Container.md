# 1주차 Singleton Container

| 📝 작성일 | 👨🏻‍🏫 소속 | 🙋🏻‍♂️ 이름 |
| --- | --- | --- |
| 2023년 4월 14일 | 인천대학교 컴퓨터공학부 | 김원정 |

## 싱글톤 패턴이란

싱글톤 패턴은 객체의 인스턴스가 오직 한개만 생성되는 패턴을 의미합니다.

## (예제) 싱글톤 패턴을 구현하는 방법들 (6가지)

### Eager Initialization (이른 초기화)

가장 간단한 형태의 방법이지만, 
항상 리소스를 차지하고, Exception Handling이 불가능한 것이 단점입니다.

```java
public class Singleton {
    
    private static final Singleton instance = new Singleton();
    
    // 클라이언트가 생성자를 사용하지 못하게 접근 제어자를 private로 설정해줍니다.
    private Singleton(){}
 
    public static Singleton getInstance(){
        return instance;
    }
}
```

### Static Block Initialization (정적 블록 초기화)

Eager Initialization에서 Exception Handling이 추가된 형태입니다. Exception Handling은 가능하지만, Class Load 단계에서 인스턴스를 생성하기 때문에, 여전히 리소스를 차지합니다.

```java
public class Singleton {
 
    private static Singleton instance;
    
    private Singleton(){}
    
    //예외처리를 위한 정적 블록 초기화
    static{
        try{
            instance = new Singleton();
        }catch(Exception e){
            throw new RuntimeException("싱글톤 인스턴스를 만들던 중 오류가 발생했습니다.");
        }
    }
    
    public static Singleton getInstance(){
        return instance;
    }
}
```

### Lazy Initialization (늦은 초기화)

나중에 초기화 하는 방식입니다. 메소드를 호출할 때 인스턴스가 없으면 생성하는 방식입니다. 리소스를 항상 차지한다는 문제를 해결할 수 있습니다.
그러나, 멀티 스레드 환경에서 동기화 문제가 생기기 때문에 싱글 스레드 환경이 보장될 때만 사용될 수 있습니다.

```java
public class Singleton {
 
    private static Singleton instance;
    
    private Singleton(){}
    
    public static Singleton getInstance(){
					// 인스턴스가 실행중이지 않을때
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}
```

### Thread Safe Singleton (안전한 스레드 초기화)

Lazy Initialization의 멀티 스레드 문제를 해결하기 위하여, 메소드에 Synchronized를 활용하는 방식입니다. 멀티스레드 환경에서 정상 동작을 보장하나 Synchronized 키워드가 너무 자주 호출될 경우 성능이 떨어지는 문제를 가지고 있습니다.

```java
public class Singleton {
 
    private static Singleton instance;
    
    private Singleton(){}
    
		// synchronized 키워드 추가
    public static synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
```

<aside>
💡 자바의 synchronized 키워드는 여러개의 스레드가 한개의 자원을 사용하고자 할 때, 현재 데이터를 사용하고 있는 해당 스레드를 제외하고 나머지 스레드들은 데이터에 접근 할 수 없도록 막아줍니다. 출처 - [코딩스타트 :: java - synchronized 란? 사용법? (tistory.com)](https://coding-start.tistory.com/68)

</aside>

### Double Checked Locking

현재 실행되는 인스턴스가 없을 경우 synchronized가 동작하게 하는 방식 입니다.

```java
public class Singleton {
 
    private static Singleton instance;
    
    private Singleton(){}

		public static Singleton getInstance(){
		// 인스턴스가 없을 때 synchronized가 동작하도록 함
	    if(instance == null){
	        synchronized (Singleton.class) {
	            if(instance == null){
	                instance = new Singleton();
	            }
	        }
	    }
	    return instance;
}
```

### Bill Push Singlethon Implementation

Bill Push가 고안한 방식입니다. inner static helper class를 이용하는 방식입니다. 가장 널리 이용되는 싱글톤 구현 방법
클래스 안에 클래스를 선언하여, 호출이 있을때만 싱글톤 인스턴스를 생성하게 끔하는 방법. synchronized를 호출하지 않기 때문에, 성능 문제가 해결됨

```java
public class Singleton {
 
    private Singleton(){}
    
    private static class SingletonHelper{
        private static final Singleton INSTANCE = new Singleton();
    }
    
    public static Singleton getInstance(){
        return SingletonHelper.INSTANCE;
    }
}
```

### Enum Singleton (Enum 초기화)

Java의 Reflection API를 이용해 싱글톤 인스턴스를 원하는 대로 조작하여 파괴할 수 있기 때문에 Enum으로 싱글톤을 구현하는 방식을 제안함…

<aside>
💡 Java의 Reflection API를 통해 런타임 중 클래스에 접근하여 원하는대로 조작할 수 있음 출처 - [[Java] Reflection 개념 및 사용 방법 (tistory.com)](https://steady-coding.tistory.com/609)

</aside>

## 싱글톤 패턴을 이용하는 이유

- 하나의 인스턴스를 공유하기 때문에 메모리를 효율적으로 사용할 수 있음
- 클래스간의 데이터 공유가 쉬움

## 싱글톤 패턴의 단점

- 코드가 많이 필요하다.
- 싱글톤 인스턴스는 자원을 공유하고 있기 때문에, 테스트하기 어렵다.
- 객체지향과 맞지 않다.

<aside>
💡 하나의 싱글톤 인스턴스가 너무 많은 책임을 가지거나 많은 데이터를 공유하게 되면, 클래스간 결합도가 너무 높아져 ‘개방-폐쇄 원칙’(OCP)와 ‘단일 책임 원칙’(SRP)를 위배하게 된다.

</aside>

## 스프링 컨테이너에서의 싱글톤 패턴

스프링 컨테이너는 싱글톤 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리합니다 (스프링의 기본 빈 등록은 싱글톤). 컨테이너 내에서 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 합니다. 스프링 컨테이너의 이런 기능 덕에 싱글톤 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수 있습니다. 

## Singleton Registry

스프링이 직접 싱글톤 객체를 만들고 관리하는 기능을 제공하는 것.
평범한 public 생성자를 가진 자바 클래스를 싱글톤으로 활용할 수 있게 해줍니다. 이렇게 될 수 있는 이유는 클래스의 제어권을 IoC 방식의 컨테이너에게 넘기면 해당 컨테이너가 객체 생성에 대한 모든 권한을 가질 수 있기 때문입니다.

<aside>
💡 IoC란, Inversion of Control (제어역전)입니다. 메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부(스프링)에서 결정되는 것을 의미합니다.

</aside>

참고자료 출처

[[생성 패턴] 싱글톤(Singleton) 패턴을 구현하는 6가지 방법 :: 준비된 개발자 (tistory.com)](https://readystory.tistory.com/116)

[자바 싱글톤 클래스(Singleton class) (tistory.com)](https://yeolco.tistory.com/122)

[[Spring Core] 싱글톤 컨테이너 (velog.io)](https://velog.io/@syleemk/Spring-Core-%EC%8B%B1%EA%B8%80%ED%86%A4-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88)

[깊이 있는 싱글톤](https://ssoco.tistory.com/65) 

[DI, IoC 정리 (velog.io)](https://velog.io/@gillog/Spring-DIDependency-Injection)