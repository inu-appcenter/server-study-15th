# [8주차] 생성자(Constructor)

복습: No
유형: java
작성일시: 2023년 7월 4일 오전 10:21

# 01 생성자란?

- 생성자는 인스턴스가 생성될 때 호출되는 **`‘인스턴스 초기화 메서드’`**
- 주로 **인스턴스 변수의 초기화 작업**에 사용
- **인스턴스 생성 시에 실행되어야 할 작업을 위해서 사용**하기도 함

## 생성자의 조건

- 생성자의 이름은 클래스의 이름과 같아야 함.
- 생성자는 리턴값이 없음
    - 키워드 void는 모든 생성자가 리턴값이 없으므로 생략

## 특징

- 생성자는 오버로딩이 가능하므로 하나의 클래스에 여러개의 생성자가 존재할 수 있음
    
    ```java
    class Card {
    	Card() {} // 매개변수가 없는 생성자
    	Card(String k) {...} // 매개변수가 있는 생성자
    	Card(String k, int num) {...} // 매개변수가 있는 생성자
    }
    ```
    
- 🚨(주의) **연산자 new가 인스턴스를 생성하는 것! → 생성자가 인스턴스를 생성하는 것은 아님!**
    - 생성자는 인스턴스 변수들을 초기화하는데 사용하는 조금 더 특별한 메서드 정도로 생각하는게 좋다
    - 실제로도 메서드와 별반 차이가 없다고 함

## 인스턴스 생성하기

```java
Card c = new Card();
```

1. 연산자 new에 의해서 메모리(heap)에 Card 클래스의 인스턴스가 생성
2. 생성자 Card()가 호출되어 수행 → 인스턴스 변수 초기화 및 초기 실행작업 수행
3. 연산자 new의 결과로, 생성된 Card 인스턴스의 주소가 반환되어 참조변수 c에 저장

# 02 기본 생성자(default constructor)

- 모든 클래스는 반드시 하나 이상의 생성자를 정의 해야함

```java
class Card {
	Card() {} // 기본 생성자
}
```

- 그런데 나는 그동안 생성자를 정의하지 않고도 인스턴스가 잘 생성 되었는데??
- 그 이유는 컴파일러가 제공하는 기본생성자 덕분!!
    - 컴파일 할 때, 소스파일(*.java)의 클래스에 생성자가 하나도 정의되지 않은 경우 컴파일러는 자동적으로 기본 생성자를 추가하여 컴파일
    - 특별히 인스턴스를 초기화 할 필요가 없다면 생성자를 정의하지 않고 컴파일러가 제공하는 기본 생성자를 사용하는 것도 좋은 방법
    - 클래스의 접근제어자가 public인 경우 기본 생성자로 public 클래스명(){}이 추가됨

## 컴파일 에러가 발생하는 경우

```java
class Data1 {
    int value;

}

class Data2 {
    int value;
		Data2() {}
    Data2(int x) {
        value = x;
    }
}

class ConstructorTest {
    public static void main(String[] args) {
        Data1 d1 = new Data1();
        Data2 d2 = new Data2();  // compile error
    }
}
```

**Q. 위의 코드는 왜 컴파일 에러가 발생한 걸까?**

- Data2의 생성자 Data2()가 생성되지 않았기 때문이다

**Q. 하지만 Data1에는 에러코드가 없는데 Data2의 인스턴스를 생성하는 코드에서 에러가 발생하는 이유는?**

- Data1에 정의되어 있는 생성자가 하나도 없기 때문에 컴파일러가 기본생성자를 추가해 주었지만 Data2에는 이미 매개변수를 가진 생성자가 정의되어 있기 때문에 기본 생성자가 추가되지 않는다

**이에 따라 코드를 수정하면 아래와 같다.**

```java
class ConstructorTest {
    public static void main(String[] args) {
        Data1 d1 = new Data1();
				Data2 d2 = new Data2(10);  // OK!
    }
}
```

<aside>
💡 컴파일러가 자동적으로 기본생성자를 추가해주는 경우는 **`‘클래스 내에 생성자가 하나도 없을 때’`**뿐이라는 점을 알고 있자!

</aside>

# 03 매개변수가 있는 생성자

- 생성자도 메서드와 같이 매개변수를 선언하여 호출 시 값을 넘겨받아 인스턴스의 초기화 작업에 사용
    - 인스턴스마다 각기 다른 값으로 초기화되어야하는 경우가 많기 때문에 매우 유용

```java
class Car {
	String color;
	String gearType;
	int door;

	Car() {} // 기본생성자
	Car(String c) {...} // 매개변수가 있는 생성자
	Car(String c, String g, int d) {...} // 매개변수가 있는 생성자
}

class CarTest {
	public static void main(String[] args) {
		Car c = new Car("white", "auto", 4);
	}
}
```

- 이렇게 매개변수를 갖는 생성자를 사용한다면 인스턴스를 생성하는 동시에 원하는 값으로 초기화가 가능
- 뿐만 아니라 코드를 보다 간결하고 직관적으로 만듦

# 04 생성자에서 다른 생성자 호출하기 - this(), this

## this()

- 두가지의 조건을 만족시킨다면 생성자 간에도 서로 호출이 가능
    - 생성자의 이름으로 **클래스이름 대신 this를 사용**
    - 한 생성자에서 다른 생성자를 호출할 때는 **반드시 첫 줄에서만 호출**이 가능

**Q. 그런데 왜 생성자에서 다른 생성자를 첫줄에서만 호출이 가능한걸까?**

- 다른 생성자를 호출하기 이전에 초기화 작업을 미리 해두게 되면 그 이후에 호출된 다른 생성자에 의해 맴버변수들의 값이 다시 초기화 될 것이다. 이렇게 되면 처음에 미리 초기화를 해둔 작업이 무의미해질 가능성이 있기 때문이다.
- 잘못된 코드 예시

```java
class Car {

    String color;
    String gearType;
    int door;

    Car(String color) {
        door = 5;
        this(color, "auto", 4);  // error, 결론적으로 door의 값은 4로 초기화 됨
    }

    Car(String color, String gearType, int door) {
        this.color = color;
        this.gearType = gearType;
        this.door = door;
    }
}
```

→ 위의 코드와 같이 첫줄에서 호출을 하지 않게 되면 처음에 door=5로 초기화 했던 값이 다른 생성자를 호출하면서 처음에 미리 초기화를 해둔 작업이 무의미 해진 것을 확인할 수 있다.

## this

- 같은 클래스 내의 생성자들은 일반적으로 서로 관계가 깊은 경우가 많아 서로 호출하도록 유기적으로 연결가능
- 수정이 필요한 경우에도 보다 적은 코드만을 변경하기 때문에 유지보수가 쉬움

```java
Car(String color, String gearType, int door) {
        this.color = color;
        this.gearType = gearType;
        this.door = door;
    }
```

- this를 사용하여 매개변수의 이름을 구별되도록 하는 것이 의미가 더 명확하고 이해하기 쉬운코드
- this를 사용하지 않고 color = color로 작성 시 지역변수로 간주

## 특징

- this는 참조변수로 인스턴스 자신을 가리킴 → 즉, 인스턴스 변수에 this로 접근가능
- 하지만 this를 사용할 수 있는 것은 인스턴스 멤버뿐
    - static 메서드에서는 인스턴스 멤버들을 사용할 수 없는 것처럼, this 또한 사용 불가
    - 그 이유는? static메서드는 인스턴스를 생성하지 않고도 호출이 가능하기 때문

## 정리

- **this**
    - 인스턴스 자신을 가리키는 참조변수, 인스턴스의 주소가 저장되어 있음
    - 모든 **인스턴스 메서드**에 지역변수로 숨겨진 채로 존재
- **this(), this(매개변수)**
    - 생성자, 같은 클래스의 다른 생성자를 호출할 때 사용

> **this와 this() 는 완전히 다른 개념! this는 참조변수, this()는 생성자임을 기억하자!**
> 

# 05 생성자를 이용한 인스턴스의 복사(깊은복사!!!!!!!)

- 현재 사용하고 있는 인스턴스와 같은 상태를 갖는 인스턴스를 하나 더 만들 때 사용
    - 두 인스턴스의 모든 인스턴스 변수(상태)가 동일한 값을 갖고 있음을 뜻함

```java
Car(Car c) {
    this.color = c.color;
    this.gearType = c.gearType;
    this.door = c.door;
}
```

- 이와같이 Car 클래스의 참조변수를 매개변수로 선언한 생성자이다.
- 이외에도 Java API에서 인스턴스 복사를 위한 생성자를 정의해 있으므로 사용가능
    - Object 클래스에 정의된 clone메서드를 이용하여 인스턴스 복사 가능

## 특징

- 복사된 인스턴스는 서로 같은 상태를 갖지만, 서로 독립적으로 메모리 공간에 존재
    - c1과 c2(복사한 인스턴스)가 존재할 때, c1의 값을 변경하더라도 c2의 값에는 영향을 받지 않음
- 이와같이 생성자를 잘 활용하면 보다 간결하고 직관적인, 객체지향적인 코드를 작성할 수 있음

인스턴스를 생성할 땐 다음의 두가지를 생각해야한다.

1. **어떤 클래스의 인스턴스를 생성할 것인가?**
2. **선택한 클래스의 어떤 생성자로 인스턴스를 생성할 것인가?**

---

# 06 빌더패턴(builder pattern)

인스턴스를 생성하고 그 인스턴스를 초기화하려고 할 때, 생성자를 통해 생성하는 것이 일반적

하지만 이렇게 생성자를 통해 객체를 생성하게 된다면 단점이 없을까?

> 개인적으로 프로젝트로 하면서 인스턴스 변수들의 값을 초기화하고 싶을 때, 모든 매개변수에 대해 생성자를 만들어줘야하는 불편함을 느꼈다.
> 

실제로 생성자 파라미터가 많아질수록 가독성이 좋지 않다는 단점이 있다.

뿐만 아니라 생성자의 경우는 정해진 파라미터 순서대로 꼭 값을 넣어주어야 한다는 점도 있다.

이러한 단점을 보완하기 위해 사용하는 것이 바로 빌더패턴이다!

## 빌더패턴이란?

- 객체를 생성할 수  있는 빌더를 builder() 함수를 통해 얻고 거기에 초기화하고자 하는 값을 입력하고 마지막에 build()를 통해 빌더를 작동시켜 객체를 생성

> 즉, 빌더는 객체를 생성하는 것! → 값을 수정(set)하는 것 아님..!
> 

- 생성자를 이용한 인스턴스 초기화

```java
Bag bag = new Bag("name", 1000, "memo", "abc", "what", "is", "it", "?");
```

- 빌더패턴을 이용한 인스턴스 초기화

```java
Bag bag = Bag.builder()
		.name("name")
        	.money(1000)
        	.memo("memo")
            .letter("This is the letter")
            	.box("This is the box")
        	.build();
```

> 생성자 파라미터가 많더라도 가독성 있게 표현할 수 있으며, 값을 설정하는 순서에 대해 제약이 없기 때문에 순서에 종속적이지 않음
> 

**자, 그렇다면 이 빌더 패턴은 어떻게 사용하는걸까?**

## **@Builder**

빌더 어노테이션을 사용하여 쉽게 적용할 수 있다.

적용할 객체에 @Builder 어노테이션을 달기만 하면 된다!

- **빌더 어노테이션 적용**

```java
@Builder
public class Bag {
	private String name;
  private int money;
}
```

**이렇게 어노테이션만 달면 빌더가 생기고 이를 통해 객체를 생성할 수 있다.**

```java
Bag bag = Bag.builder()
		.name("name")
        	.money(1000)
        	.memo("memo")
        	.build();
```

## 빌더 어노테이션 옵션

### builderMethodName

- 빌더 어노테이션 사용시, 빌더를 생성하는 메서드의 이름은 기본값인 builder()
- 이를 새롭게 네이밍 할 수 있는 어노테이션 값
- 일반적으로는 builder() 그대로 사용

```java
Bag bag = Bag.builder()
```

### buildMethodName

- builder()로 얻은 빌더에 필드값들을 입력하고 마지막에 객체를 생성하는 동작인 빌드를 네이밍 할 수 있는 어노테이션 값
- 기본은 build()로 create() 등 필요한대로 네이밍
- 이에 맞추어 빌더 메서드도 같이 리네이밍 해주기도 함

```java
Bag bag = Bag.builder()
        	.build();
```

### builderClassName

- 빌더 어노테이션을 사용할 때, 각 필드들의 값을 초기화하는 메서드의 반환값의 빌더 클래스 이름이 000Builder로 자동 설정 됨
- 예를 들어 아래와 같은 Bag 클래스에 @Builder를 적용하면 각 필드를 설정하는 메서드의 반환값은 BagBuilder가 됨
- 이에 맞추어 원하는 대로 리네이밍 가능

```java
Bag bag = Bag.builder()
					.name("name") // return BagBuilder
        	.money(1000) // return BagBuilder
        	.memo("memo") // return BagBuilder
        	.build();
```

### toBuilder

- boolean 값으로 설정할 수 있는 어노테이션 값으로 기본 값은 false
- true로 설정시 빌더로 만든 인스턴스에서 toBuilder() 메서드를 호출해 그 인스턴스 값을 베이스로 빌더 패턴으로 새로운 인스턴스를 생성

```java
Bag bag1 = Bag.builder()
		.name("name") 
        	.money(1000)
        	.memo("memo")
        	.build();
            
Bag bag2 = bag1.toBuilder().money(8000).build();
```

### access()

- builder 클래스의 접근제어자를 어떻게 할 것인지에 대한 설정 값, 기본은 public

```java
AccessLevel access() default lombok.AccessLevel.PUBLIC;
```