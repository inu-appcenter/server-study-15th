# 10주차 제네릭(Generics)

| 📝 작성일 | 👨🏻‍🏫 소속 | 🙋🏻‍♂️ 이름 |
| --- | --- | --- |
| 2023년 7월 20일 | 인천대학교 컴퓨터공학 | 김원정 |

# 제네릭

제네릭은 다양한 타입의 객체들을 다루는 메서드나 컬렉션 클래스에 컴파일 시의 타입 체크를 해주는 기능이다.
객체의 타입을 컴파일 시에 체크하기 때문에 객체의 타입 안정성을 높이고 형변환의 번거로움이 줄어든다

예를 들어, ArrayList와 같은 컬렉션 클래스는 다양한 종류의 객체를 담을 수 있지만 보통 한 종류의 객체를 담는 경우가 더 많다. 그런데 꺼내 쓸 때마다 타입체크를 하고 형 변환을 하는 것은 비효율적일 수 밖에 없다.
게다가 원하지 않는 종류의 객체가 포함되는 것을 막을 방법이 없다는 것도 문제다.

### 제네릭의 장점

타입 안정성을 제공한다.

타입 체크와 형 변환을 생략할 수 있어 코드가 간결해진다.

# 제네릭 클래스

## 클래스 생성

```java
class Box<T> { // 제네릭 타입 T를 선언 (타입 변수라고 한다)
	T item;

	public void setItem(T item) {
		this.item = item;
	}

	public T getItem() {
		return this.item;
	}
}
```

## 객체(인스턴스) 생성

```java
Box<Integer> b = new Box<>(); // Java 8 부터 사용가능한 타입 추정
Box<Integer> b = new Box<Integer>(); // 리턴 타입을 명시

b.setItem("안녕"); // -> 오류 발생!
b.setItem(1);
Int item = b.getItem(); -> item = 1
```

## 제네릭의 호환

제네릭이 도입되기 이전의 코드와 호환을 위해, 제네릭 클래스인데도 예전 방식으로 객체를 생성하는 것이 허용

```java
Box b = new Box(); // -> 원시타입을 사용한다는 경고.. T는 Object로 간주된다.
b.setItem("안녕");
```

# 제네릭의 용어

```java
class Box<T> {}
```

- Box<T> : 제네릭 클래스 ‘T의 Box’나 ‘T Box’로 읽는다.
- T : 타입 변수 (타입 매개변수)
- Box : 원시 타입

# 제네릭에서 할 수 없는 것

- 모든 객체에 대해 동일하게 작동해야하는 static 멤버(필드)에는 타입 변수 T를 사용할 수 없다.
  → 타입 변수 T는 인스턴스 변수로 간주되기 때문이다.
- 제네릭 타입의 배열의 생성이 허용되지 않는다. (참조변수로는 가능)
  → new 연산자 때문에 생성이 안된다. 컴파일 시에는 컴파일러가 T가 어떤 타입인지 알아야 하기 때문이다.

# 제네릭 클래스의 제한된 타입 파라미터

제네릭 타입에 ‘extends’를 사용하면 특정 타입의 자손들만 대입할 수 있게 제한할 수 있다.

```java
class FruitBox<T extends Fruit> {...} // Fruit의 하위 클래스만 대입 가능
class NumberBox<T extends Number> {...} // Number의 하위 클래스 Integer, Double...
```

# 와일드 카드

```java
Class Juicer {
	static Juice makeJuice (FruitBox<Fruit> box) {
		String temp = "";
		for (Fruit f : box.getList()) temp += f + "";
		return new Juice(temp)
	}
}

...
FruitBox<Fruit> fruitBox = new FruitBox<>();
FruitBox<Apple> appleBox = new FruitBox<>();

Juicer.makeJuice(fruitBox) // 문제 1
Juicer.makeJuice(appleBox) // 문제 2
```

- **정답**

    <aside>
    💡 1. 가능
    2. 불가

  makeJuice의 제네릭 타입을 <Fruit>으로 고정해놨기 때문에 오류가 발생한다.

    </aside>

  이 문제를 해결하기 위해 메소드를 재정의 하는 것으로 문제가 해결될까?

  안타깝게도, 제네릭 타입이 다른 것 만으로 오버로딩이 성립하지 않기 때문에
  makeJuice 메소드를 <Apple> 제네릭 타입으로 재정의 할 수 없다.

  이런 문제를 해결하기 위해 와일드카드가 고안되었다.


### 와일드 카드의 쉬운 이해

와일드 카드는 상한과 하한으로 제한할 수있다.
다음 예를 참고해보자

Person의 하위 클래스로 Worker와 Student가 있고, Student의 하위 클래스로 HighStudent가 있다.

1. Course<?>
   수강생은 모든 타입(Person, Worker, HighStudent)이 될 수 있다.
2. Course<? extends Student>
   수강생은 Student와 HighStudent만 될 수 있다.
3. Course<? super Worker>
   수강생은 Worker와 Person만 될 수 있다.

### 문제가 해결된 makeJuice 메소드

```java
Class Juicer {
	static Juice makeJuice (FruitBox<? extends Fruit> box) {
		String temp = "";
		// FruitBox의 제네릭 타입이 extends Fruit이기 때문에 사용가능
		for (Fruit f : box.getList()) temp += f + ""; 
		// 만약 extends Fruit으로 제한되어 있지 않았으면 타입 추정이 불가능하므로
		// 참조변수로 Fruit을 지정하는 것은 안됨
		return new Juice(temp)
	}
}
```

# 제네릭 메소드

### 제네릭 메서드의 선언

```java
public <타입 변수> 리턴타입 메소드명(매개변수) {}
```

### 제네릭 메서드의 사용 예

```java
// 제네릭 메소드 적용 안됨
Class Juicer {
	static Juice makeJuice (FruitBox<? extends Fruit> box) {
		String temp = "";
		for (Fruit f : box.getList()) temp += f + ""; 
		return new Juice(temp)
	}
}
```

```java
// 제네릭 메소드 적용
Class Juicer {
	static <T extends Fruit> Juice makeJuice (FruitBox<T> box) {
		String temp = "";
		for (Fruit f : box.getList()) temp += f + ""; 
		return new Juice(temp)
	}
}
```

### 제네릭 메소드의 호출

```java
FruitBox<Fruit> fruitBox = new FruitBox<>();
FruitBox<Apple> appleBox = new FruitBox<>();

...
Juicer.<Fruit>makeJuice(fruitbox);
Juicer.<Apple>appleBox(appleBox);
// fruitBox의 선언부와 appleBox 선언부에 타입이 명시되어 있기 때문에
// 컴파일러가 타입을 추정할 수 있어 대입된 타입이 생략 가능하다.
Juicer.appleBox(appleBox);
// 컴파일러가 타입을 추정할 수 없는 경우는 생략이 불가능하다.
```

### 복잡하게 선언된 제네릭 메서드의 이해

제네릭에 익숙하지 않다면, 아래 메소드를 한눈에 이해하기 어려울 것이다.

```java
public static <T extends Comparable<? super T>> void sort(List<T> list);
```

와일드 카드를 걷어보자

```java
public static <T extends Comparable<T>> void sort(List<T> list);

// 1. List T의 요소가 Comparable 인터페이스를 구현하였다. 
// (제네릭에서는 인터페이스라고 implement를 쓰지 않는다)
```

조금 이해되었다면, 코드를 다시 한번 보자

```java
public static <T extends Comparable<? super T>> void sort(List<T> list);

// 1. 타입 T를 요소로 하는 List를 매개변수로 허용한다.
// 2. T는 comparable을 구현한 클래스며 (T extends Comparable)
// T 또는 그 조상의 타입을 비교하는 Comparable이어야 한다는 것을 의미한다. <? super T>
// ex. T가 Student고 Person의 자손이면 <? super T>는 Stduent, Person, Object가 모두 가능하다.
```

# 컴파일러에서의 제네릭

컴파일러는 제네릭 타입을 이용해서 소스파일을 체크하고 필요한 곳에 형변환을 넣어준다. 그리고 제네릭 타입을 제거한다. 컴파일된 파일에는 제네릭 타입에 대한 정보가 없다. 이렇게 하는 이유는 이전 코드와의 호환성 유지를 위해서이다.