# [9주차] 내부 클래스(inner class)

작성일시: 2023년 7월 13일 오후 8:51
유형: java
복습: No

# 01 내부 클래스 (Inner Class)

- 내부 클래스란 하나의 클래스 내부에 선언된 또 다른 클래스를 의미
- 내부에 클래스가 정의된다는 점을 제외하면 일반적인 클래스와 다르지 않음

**그렇다면 이 내부 클래스를 왜 사용하는걸까?**

- 일반적으로 두 클래스가 서로 밀접한 관계가 있거나 하나의 클래스 또는 메소드에서만 사용되는 클래스일 때 사용되는 기법

> 내부 클래스는 중첩 클래스로 분류되기도 함
> 

```java
// Creature 클래스는 내부 클래스들의 외부 클래스
class Creature {
	int life;
    
    // Animal 클래스는 Creature 클래스의 내부 클래스
    class Animal { 

    }
	
    // Insect 클래스는 Creature 클래스의 내부 클래스
    class Insect {

    }
    
    public void method() {
        Animal animal = new Animal();
	    	Insect insect = new Insect();
        // ...
    }
}
```

## 내부 클래스의 장점

- 내부 클래스에서 외부 클래스의 멤버들을 쉽게 접근할 수 있다
- 코드의 복잡성을 줄일 수 있다
    - 예시
    
    ```java
    class Creature {
        int life;
    
        public void method() {
            Animal animal = new Animal(); // Animal 객체는 오로지 Creature 클래스의 메소드 내에서만 사용된다고 가정 
            // ...
        }
    }
    
    // 외부에 선언된 클래스
    class Animal {
    
    }
    ```
    
    일반적으로 외부에 또는 다른 파일로 클래스를 선언해놓고 인스턴스화 하여 사용하는 경우가 많을 것이다.
    
    하지만 만약 Animal 클래스 자료형이 오로지 Creature 클래스의 메소드 내에서만 선언되어 사용한다면 Animal 클래스를 Creature 클래스 내부에 넣어줌으로써 보다 코드의 이해에 도움이 될 것이다.
    
    → 즉, 한눈에 상관관계를 이해하기 쉬움.
    
    ```java
    class Creature {
        int life;
    	
        // 클래스 멤버 같이 Creature 클래스 안에다 넣어 선언한다.
        class Animal {
    
        }
    
        public void method() {
            Animal animal = new Animal(); // Animal 객체는 오로지 Creature 클래스의 메소드 내에서만 사용된다고 가정 
            // ...
        }
    }
    ```
    

- 캡슐화의 적용

내부 클래스에 `private` 제어자를 적용해줌으로써, 캡슐화를 통해 클래스를 내부로 숨길 수 있다.

즉, 캡슐화를 통해 외부에서의 접근을 차단하면서도, 내부 클래스에서 외부 클래스의 멤버들을 제약 없이 쉽게 접근할 수 있어 구조적인 프로그래밍이 가능해 진다. 그리고 클래스 구조를 숨김으로써 코드의 복잡성도 줄일 수 있다.

```java
class Creature {
    private int life = 50;
	
    // private class 로, 오로지 Creature 외부 클래스에서만 접근 가능한 내부 클래스로 설정
    private class Animal {
        private String name = "호랑이";

        int getOuter() {
            return life; // 외부 클래스의 private 멤버를 제약 없이 접근 가능
        }
    }

    public void method() {
        Animal animal = new Animal(); 

        // Getter 없이 내부 클래스의 private 멤버에 접근이 가능
        System.out.println(animal.name); // 호랑이

        // 내부 클래스에서 외부 클래스이 private 멤버를 출력
        System.out.println(animal.getOuter()); // 50
    }
}
```

# 02  내부 클래스의 종류와 특징

- 클래스 멤버변수도 `**선언되는 위치나 접근제어자에 따라 역할과 이름이 달라지 듯**`, 내부 클래스도 선언된 위치에 따라 구분

<aside>
💡 내부 클래스에는 아래 표와같이 총 4가지가 있는데, 이중에서 중점적으로 봐야할 요소는 **`static 클래스와 익명 클래스`**이다. static 클래스는 디자인 패턴에서 싱글톤 패턴과 Holder 패턴에서 쓰이는 기법중 하나이고, 익명 클래스는 자바8의 람다 표현식의 기본 골자이기 때문이다.

</aside>

> 위 내용은 다시 정리해보도록 하겠다.
> 

| 내부 클래스 | 특징 |
| --- | --- |
| 인스턴스 클래스 (instance class) | 외부 클래스의 멤버변수 선언 위치에 선언하며, 외부 클래스의 인스턴스 멤버처럼 다뤄진다.
주로 외부 클래스의 인스턴스 멤버들과 관련된 작업에 사용될 목적으로 선언된다. |
| 스태틱 클래스
(static class) | 외부 클래스의 멤버변수 선언 위치에 선언하며, 외부 클래스의 static 멤버처럼 다뤄진다.
다만 주의할점은 static이라고 해서 new 생성자 초기화를 못하는 건 아니다.
즉, 일반적인 static 필드 변수나 static 메서드와 달리, static 내부 클래스는 같은 static이지만 메모리 구조나 기능이 전혀 다르다. |
| 지역 클래스
(local class) | 외부 클래스의 메서드나 초기화블럭 안에 선언하며, 선언된 메서드 블록 영역 내부에서만 사용될 수 있다. |
| 익명 클래스
(anonymous class) | 클래스의 선언과 객체의 생성을 동시에 하는 이름없는 클래스이다.
주로 클래스를 일회용으로 사용할때 자주 이용된다. |

# 03 내부 클래스의 선언

- 변수가 선언된 위치에 따라 인스턴스 변수, 클래스변수, 지역변수로 나뉘듯이 내부 클래스도 이와 마찬가지로 `**선언된 위치**`에 따라 나뉨
- 각 내부 클래스의 선언위치에 따라 같은 선언위치의 변수와 동일한 유효범위와 접근성을 갖음

```java
class Outer{
	class InstanceInner { ... } // 인스턴스 클래스
	static class StaticInner { ... } // 스태틱 클래스
    
    void method1(){
    	class LocalInner { ... } // 지역 클래스
    }
}
```

![클래스 멤버 접근 제어자 특성과 비슷하다고 보면 된다.](/[9주차] 내부 클래스(inner class)/Untitled.png)

클래스 멤버 접근 제어자 특성과 비슷하다고 보면 된다.

# 04 내부 클래스의 제어자와 접근성

## 인스턴스 클래스

- 클래스의 멤버 변수 선언부에 위치하고 static 키워드가 없는 내부 클래스
- 외부 클래스의 멤버로 취급되기 때문에 외부 클래스의 객체 먼저 생성한 후 내부 클래스의 객체를 생성이 가능하다
- 인스턴스 클래스 내부에는 instance 멤버만 선언할 수 있다. (static 멤버는 선언 불가)
    - 단, final을 사용할 경우 상수이므로 모든 내부 클래스에서 정의 가능
- 주로 외부 클래스의 인스턴스 멤버들과 관련된 작업에 사용될 목적으로 선언된다.

```java
class PocketBall {
    // 인스턴스 변수
    int size = 100;
    int price = 5000;

    // 인스턴스 내부 클래스
    class PocketMonster {
        String name = "이상해씨";
        int level = 10;
        
        // static int cost = 100; - 에러! 인스턴스 내부 클래스에는 static 변수를 선언할 수 없다.
        static final int cost = 100; // final static은 상수이므로 허용

        public void getPoketMember() {
            // 별다른 조치없이 외부 클래스 맴버 접근 가능
            System.out.println(size);
            System.out.println(price);

            // 내부 클래스 멤버
            System.out.println(name);
            System.out.println(level);
            System.out.println(cost);
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        
        PocketBall ball = new PocketBall(); // 먼저 외부 클래스를 인스턴스화 해주고
        PocketBall.PocketMonster poketmon = ball.new PocketMonster(); // 외부클래스.내부클래스 형식으로 내부클래스를 초기화 하여 사용할 수도 있다
        poketmon.getPoketMember();
        
        // 위의 단계를 한줄로 표현
        PocketBall.PocketMonster poketmon2 = new PocketBall().new PocketMonster();
    }
}
```

- 외부 클래스를 인스턴스화하면 외부 클래스의 코드가 메모리에 올라오게 되고, 이 때 내부 클래스의 코드도 메모리에 올라오게 된다. 이렇게 코드를 메모리에 올린 이후에야 내부 클래스의 인스턴스를 생성할 수 있게 된다.
- 하지만 내부 클래스는 다른 클래스에서 직접 사용하기 보단 해당 외부 클래스에서만 사용하는 것이 일반적이기 때문에 위의 메인 코드와 같이 내부 클래스의 인스턴스를 다른 클래스에서 만드는 경우는 드물다.

## 스태틱 클래스

- static 키워드가 붙은 내부 클래스
- 단, 일반적인 static 필드 변수나 static 메서드와 똑같이 생각하면 안된다.
- static 클래스 내부에는 instance 멤버와 static 멤버 모두 선언 할 수 있다.
- 그러나 일반적인 static 메서드와 동일하게 외부 클래스의 인스턴스 멤버에는 접근이 불가하고, 정적(static) 멤버에만 접근할 수 있다.

```java
class PocketBall {
    int size = 100;
    static int price = 5000;

    // static 내부 클래스
    static class PocketMonster {
        static String name = "이상해씨";
        int level = 10;

        public static void getPoketMember() {
            // 외부 클래스 인스턴스 맴버 접근 불가능
            // System.out.println(size);
            
            // 외부 클래스 스태틱 멤버 접근 가능
            System.out.println(price);
			
            // 내부 클래스 멤버도 스태틱 맴버만 접근 가능
            System.out.println(name);
            // System.out.println(level);
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        // 스태틱 내부 클래스의 인스턴스는 외부 클래스를 먼저 생성하지 않아도 된다.
        PocketBall.PocketMonster poketmon = new PocketBall.PocketMonster();
        System.out.println(poketmon.level);
        System.out.println(PocketBall.PocketMonster.name);

        // 클래스.정적내부클래스.정적메소드()
        PocketBall.PocketMonster.getPoketMember();
    }
}
```

### **static 클래스에 대한 오해**

가장 많은 사람들이 실수하는 오해가 static 클래스가 그 외의 static 필드 변수나 static 메소드와 같이, 'static 이니까 메모리에 하나만 올라가는 인스턴스'로 착각 한다는 점이다.

우선 결론부터 말하면 static 클래스는, 키워드가 static이 들어갔을 뿐이지, 우리가 알던 static 멤버와는 전혀 다른 녀석이다.

다음 코드를 보면 static 변수와 내부 인스턴스 클래스 그리고 내부 static 클래스가 있다.

이들을 각각 메인 메소드에서 변수로 받아 같은지 다른지 비교해보면, static 변수는 우리의 예상대로 메모리에서 한번만 생성 되니까 반환 되는 데이터는 같다.

하지만 내부 static 클래스는 일반 클래스처럼 초기화를 할때마다 다른 객체가 만들어짐을 볼 수 있다. (static 인데 말이다)

```java
public class Main {
    // 스태틱 필드 변수
    static Integer num = new Integer(0);

    // 내부 인스턴스 클래스
    class InnerClass{
    }

    // 내부 스태틱 클래스
    static class InnerStaticClass{
    }
    
    public static void main(String[] args) {

        // 스태틱 필드 변수는 유일해서 서로 같다
        Integer num1 = Main.num;
        Integer num2 = Main.num;
        System.out.println(num1 == num2); // true

        // 생성된 내부 클래스 인스턴스는 서로 다르다
        Main.InnerClass inner1 = new Main().new InnerClass();
        Main.InnerClass inner2 = new Main().new InnerClass();
        System.out.println(inner1 == inner2); // false

        // 생성된 내부 스태틱 클래스 인스턴스는 서로 다르다
        Main.InnerStaticClass static1 = new InnerStaticClass();
        Main.InnerStaticClass static2 = new InnerStaticClass();
        System.out.println(static1 == static2); // false
    }
}
```

복잡하게 생각할 필요없이 static 클래스는 static 이라고 해서 메모리에 한번만 로드되는 객체 개념이 아닌 것이다.

즉, 내부 인스턴스 클래스 처럼 외부 인스턴스를 먼저 선언하고 초기화해야 하는 선수 작업 필요 없이, **내부 클래스의 인스턴스를 바로 생성**할 수 있다는 차이점이 존재 할 뿐이다.

## 로컬 클래스

- 메소드 내부에 위치하는 클래스 (지역 변수와 같은 성질을 지님)
- **지역 변수처**럼 해당 메서드 내부에서만 한정적으로 사용된다. (해당 메소드 실행 외에는 클래스 접근 및 사용 불가)
- 접근제한자와 static을 붙일 수 없다. 메소드 내부에서만 사용되므로 접근을 제한할 필요가 없고, 원래 메소드 내에는 static 멤버를 선언할 수 없기 때문이다.

```java
class PocketBall {
    int size = 100;
    int price = 5000;

    public void pocketMethod() {
        int exp = 5000;
        
        // 메소드 내에서 클래스를 선언
        class PocketMonster {
            String name = "이상해씨";
            int level = 10;

            public void getPoketLevel() {
                System.out.println(level); // 인스턴스 변수 출력
                System.out.println(exp); // 메소드 지역 상수 출력
            }
        }
		
        // 메소드 내에서 클래스를 선언
        class PocketMonster2 {
            String name = "리자몽";
            int level = 50;
        }

        new PocketMonster().getPoketLevel();
        System.out.println("메소드 실행 완료");
    }
}
```

메소드 내에서 잠시 사용하는 지역 변수 개념과 같이 지역 클래스 자료형을 사용하고 버리는 것으로 이해하면 된다.

메소드 내의 모든 내용은 스택 프레임 안에 생성됐다가 메소드 종료 시 사라지게 되므로, 당연히 다른 곳에서는 접근 및 사용 자체가 불가능하다.

메소드 내에서 인스턴스를 생성한 후 사용하고 메소드 종료와 함께 레퍼런스가 사라지면서 힙 메모리 영역의 실제 데이터도 나중에 GC(가비지 컬렉터)에 의해 지워지게 된다.

# 05 익명 클래스

- 익명 클래스는 클래스 이름이 존재하지 않는 이너 클래스다. (자바스크립트의 익명 함수로 생각해도 된다)
- 단 하나의 객체만을 생성하는 일회용 클래스
- 클래스의 선언과 동시에 객체를 생성
- 익명 클래스는 생성자가 존재하지 않는다.
- 익명 클래스는 기존에 존재하는 클래스를 메서드 내에서 일회용으로 클래스 내부 구성을 선언하여 필요한 메서드를 재정의 하여 사용하는 기법이다.

```java
public class Main {
    public static void main(String[] args) {
    	// Object 클래스를 일회성으로 익명 클래스로 선언하여 변수 o 에 저장
        Object o = new Object() {
            @Override
            public String toString() {
                return "내 마음대로 toString 바꿔보리기 ~";
            }
        };
		
        // 익명 클래스의 객체의 오버라이딩한 메서드를 사용
        String txt = o.toString();
        System.out.println(txt); // 내 마음대로 toString 바꿔보리기 ~
    }
}
```