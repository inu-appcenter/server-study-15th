# [11주차] thread

작성일시: 2023년 8월 4일 오전 8:19
유형: java
복습: No

# 01 프로세스와 스레드

## 1) 프로세스와 스레드

`프로세스(process)`는  ‘실행 중인 프로그램’으로 프로그램을 실행하면 OS로부터 메모리를 할당받아 프로세스가 된다.

프로세스는 프로그램 수행에 필요한 데이터와 메모리, 그리고 **스레드**로 구성되어 있다.

**프로세스 자원을 이용해서 실제로 작업을 수행하는 것**이 `스레드`이다!

`스레드`란 **모든 프로세서에서 최소한 하나 이상의 스레드가 존재하며, 둘 이상의 스레드를 가진 프로세스를 ‘멀티스레드 프로세스(multi-threaded process)** 라고 한다.

> 즉, 스레드는 프로세스라는 작업 공간에서 작업을 처리하는 일꾼으로 이해하면 좋다!
> 

## 2) 멀티테스킹과 멀티스레딩

우리가 사용하고 있는 윈도우나 유닉스를 포함한 대부분의 OS는 멀티테스킹(다중작업)을 지원하기 때문에 여러 개의 프로세스가 동시에 실행될 수 있다

멀티스레딩은 하나의 프로세스 내에서 여러 스레드가 동시에 작업을 수행하는 것으로, CPU의 코어가 한번에 하나의 작업만 할 수 있으므로 실제로 동시에 처리되는 작업의 수는 코어의 개수와 일치한다

그러나 처리해야하는 스레드의 수는 언제나 코어의 수보다 훨씬 많기 때문에 코어가 아주 짧은 시간동안 여러작업을 번갈아가며 수행함으로써 여러 작업들이 동시에 수행되는 것처럼 보이게 된다.

그렇다면 프로세스의 성능이 단순히 스레드의 개수에 비례할까?

아니다, 하나의 스레드를 가진 프로세스보다 두 개의 스레드를 가진 프로세스가 오히려 낮은 성능을 보일수도 있다.

### 멀티스레딩의 장단점

- CPU의 사용률 향상
- 자원의 효율적인 사용
- 사용자에 대한 응답성 향상
- 작업의 분리에 따른 코드의 가독성

예시를 들어보자!

우리는 채팅을 하면서 파일을 다운받거나 음성대화를 나눌 수 있는 것이 가능하다. 만약 멀티스레드를 사용하지 않는다면 이러한 일들은 할 수 없게된다. 뿐만아니라 동시에 사용자가 접속하는 프로그램의 경우에서도 다수의 사용자의 요청을 서비스해야하므로 멀티스레드로 작성하는 것이 필수적이다!

그렇다면 스레드의 단점은 무엇일까?

- 프로세스는 여러 스레드가 같은 프로세스 내에서 자원을 공유하면서 작업을 하기 때문에 동기화, 교착상태와 같은 문제들이 생길 수 있다.

<aside>
💡 `교착상태`란 시스템 자원에 대한 요구가 뒤엉킨 상태로, 둘 이상의 프로세스가 다른 프로세스가 점유하고 있는 자원을 서로 기다릴 때 무한 대기에 빠지는 상황을 말한다.

</aside>

# 02 스레드의 구현과 실행

자바에서 스레드를 생성하는 방법에는 다음과 같이 두 가지 방법이 있다.

1. Runnable 인터페이스를 구현하는 방법
2. Thread 클래스를 상속받는 방법

두 방법 모두 스레드를 통해 작업하고 싶은 내용을 run() 메소드에 작성하면 된다.

```java
class ThreadWithClass extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(getName()); // 현재 실행 중인 스레드의 이름을 반환함.
            try {
                Thread.sleep(10);          // 0.01초간 스레드를 멈춤.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
 

class ThreadWithRunnable implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()); // 현재 실행 중인 스레드의 이름을 반환함.

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Thread01 {
    public static void main(String[] args){
        ThreadWithClass thread1 = new ThreadWithClass();       // Thread 클래스를 상속받는 방법
        Thread thread2 = new Thread(new ThreadWithRunnable()); // Runnable 인터페이스를 구현하는 방법
        thread1.start(); // 스레드의 실행
        thread2.start(); // 스레드의 실행
    }
}
```

```java
Thread-0
Thread-1
Thread-1
Thread-0
Thread-1
Thread-0
Thread-1
Thread-0
Thread-1
Thread-0
```

Thread 클래스를 상속받은 경우에는 자손 클래스의 인스턴스를 생성하면 되지만, Runnable 인터페이스를 구현한 경우에는 Runnable을 구현한 클래스의 인스턴스를 생성하여 이 인스턴스를 다시 Thread 클래스의 생성자의 매개변수로 제공해야한다.

## 스레드의 실행 - start()

> t1.start(); *// 쓰레스 t1을 실행시킨다.*
> 

스레드를 생성했다고 자동으로 실행되는 것이 아닌 start()를 호출해야만 스레드가 실행된다.

여기서 알아야할 것은 한번 실행이 종료된 스레드는 다시 실행할 수 없다는 점이다.

즉, 하나의 스레드에 대해 start()가 한번만 호출될 수 있다. 이를 한번 더 수행해야한다면 새로운 스레드를 생성한 뒤 start()를 호출해야한다.

# 03 s**tart()와 run()**

run()을 호출하는 것은 단순히 클래스에 선언된 메서드를 호출하는 것일 뿐이다.

![Untitled](%5B11%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%5D%20thread%20a07bdc6436cd44438b09657717c4ae49/Untitled.png)

start()는 새로운 쓰레드가 작업을 실행하는데 필요한 호출스택(call stack)을 생성한 다음에 run()을 호출해서,

생성된 호출스택에 run()이 첫번째로 올라가게 한다.

모든 쓰레드는 독립적인 작업을 수행하기위해 자신만의 호출스택을 필요로 하기 때문에,

새로운 쓰레드를 생성하고 실행시킬 때마다 새로운 호출스택이 생성되고, 쓰레드가 종료되면 작업에 사용된 호출스택은 소멸한다.

![Untitled](%5B11%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%5D%20thread%20a07bdc6436cd44438b09657717c4ae49/Untitled%201.png)

1. main메서드에서 쓰레드의 start()를 호출한다.
2. start()는 새로운 쓰레드를 생성하고, 쓰레드가 작업하는 사용될 호출스택을 생성한다.
3. 새로 생성된 호출스택에 run()이 호출되어, 쓰레드가 독립된 공간에서 작업을 수행한다.
4. 호출스택이 2개이므로 스케줄러가 정한 순서에 의해 번갈아가며 수행한다.

## **main쓰레드**

main메서드의 작업을 수행하는 것도 쓰레드이며, 이를 main쓰레드라고 한다.

지금까지는 main 메서드가 수행을 마치면 프로그램이 종료되었으나, 다른 쓰레드가 아직 작업을 마치지 않은 상태라면 프로그램이 종료되지 않는다.

```java
class ThreadEx2 {
	public static void main(String args[]) throws Exception {
		ThreadEx2_1 t1 = new ThreadEx2_1();
		t1.start();
	}
}

class ThreadEx2_1 extends Thread {
	public void run() {
		throwException();
	}

	public void throwException() {
		try {
			throw new Exception();		
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
```

```java
java.lang.Exception
	at ThreadEx2_1.throwException(ThreadEx2.java:15)
	at ThreadEx2_1.run(ThreadEx2.java:10)
```

start()를 통해 새로 생성한 쓰레드에서 고의로 예외를 발생시켰다.

한 쓰레드가 예외를 발생해서 종료되어도 다른 쓰레드의 실행에는 영향을 미치지 않는다.

main쓰레드의 호출스택이 없는 이유는 main쓰레드가 이미 종료되었기 때문이다.

![Untitled](%5B11%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%5D%20thread%20a07bdc6436cd44438b09657717c4ae49/Untitled%202.png)

```java
class ThreadEx3 {
	public static void main(String args[]) throws Exception {
		ThreadEx3_1 t1 = new ThreadEx3_1();
		t1.run();
	}
}

class ThreadEx3_1 extends Thread {
	public void run() {
		throwException();
	}

	public void throwException() {
		try {
			throw new Exception();		
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
```

```java
java.lang.Exception
	at ThreadEx3_1.throwException(ThreadEx3.java:15)
	at ThreadEx3_1.run(ThreadEx3.java:10)
	at ThreadEx3.main(ThreadEx3.java:4)
```

쓰레드를 새로 생성하지 않고, run() 호출 후 예외 발생.

![Untitled](%5B11%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%5D%20thread%20a07bdc6436cd44438b09657717c4ae49/Untitled%203.png)

# 04 싱글스레드와 멀티스레드

![Untitled](%5B11%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%5D%20thread%20a07bdc6436cd44438b09657717c4ae49/Untitled%204.png)

## 싱글스레드

하나의 프로세스에서 오직 하나의 스레드로만 실행한다. 그렇기 때문에, 하나의 레지스터와 스택으로 표현이 가능하다.

![https://velog.velcdn.com/images%2Fgil0127%2Fpost%2Fe77bf094-c662-48ac-ad2e-530d9bd0f781%2Fsingle.gif](https://velog.velcdn.com/images%2Fgil0127%2Fpost%2Fe77bf094-c662-48ac-ad2e-530d9bd0f781%2Fsingle.gif)

## 멀티스레드

멀티 스레드는 CPU의 최대 활용을 위해 프로그램의 둘 이상을 동시에 실행하는 기술이다.

이러한 작업은 [컨텍스트 스위칭( Context Switching )](https://velog.io/@gil0127/%EC%8B%B1%EA%B8%80%EC%8A%A4%EB%A0%88%EB%93%9CSingle-thread-vs-%EB%A9%80%ED%8B%B0%EC%8A%A4%EB%A0%88%EB%93%9C-Multi-thread)을 통해서 이뤄진다. 위의 이미지에서 하나의 스레드에서 다음 스레드로 이동을 하면서, 컨텍스트 스위칭이 일어난다. 그리고, 스위칭이 일어나면서 부분적으로 조금씩 조금씩 각각의 스레드에 대한 작업을 끝내게 된다.

![https://velog.velcdn.com/images%2Fgil0127%2Fpost%2F813ea794-6eef-40b4-8042-09a8551082fd%2Fmulti.gif](https://velog.velcdn.com/images%2Fgil0127%2Fpost%2F813ea794-6eef-40b4-8042-09a8551082fd%2Fmulti.gif)

> 그래서 어떤걸 사용하는게 더 좋은가?
> 

![Untitled](%5B11%E1%84%8C%E1%85%AE%E1%84%8E%E1%85%A1%5D%20thread%20a07bdc6436cd44438b09657717c4ae49/Untitled%205.png)

위 그림은 하나의 스레드 작업을 수행했을때의 성능이 더 높은 것을 확인할 수 있다.

즉, 단순히 CPU만을 사용하는 계산작업이라면 싱글스레드로 프로그래밍 하는 것이 더 효율적이며, 사용자로부터 데이터를 입력받거나, 네트워크로 파일을 주고받는 작업, 프린터로 파일을 출력하는 작업과 같이 외부기기와 입출력을 필요로 하는 경우에는 멀티스레드로 프로그래밍 하는것이 더 효율적이다.

# 05 스레드의 우선순위

자바에서 각 스레드는 우선순위(priority)에 관한 자신만의 필드를 가지고 있다.

이러한 우선순위에 따라 특정 스레드가 더 많은 시간 동안 작업을 할 수 있도록 설정할 수 있다.

| 필드 | 설명 |
| --- | --- |
| static int MAX_PRIORITY | 스레드가 가질 수 있는 최대 우선순위를 명시함. |
| static int MIN_PRIORITY | 스레드가 가질 수 있는 최소 우선순위를 명시함. |
| static int NORM_PRIORITY | 스레드가 생성될 때 가지는 기본 우선순위를 명시함. |
- getPriority()와 setPriority() 메소드를 통해 스레드의 우선순위를 반환하거나 변경할 수 있다.
- 스레드의 우선순위가 가질 수 있는 범위는 1부터 10까지이며, 숫자가 높을수록 우선순위 또한 높아진다.

하지만 스레드의 우선순위는 비례적인 절댓값이 아닌 어디까지나 상대적인 값일 뿐이다.

우선순위가 10인 스레드가 우선순위가 1인 스레드보다 10배 더 빨리 수행되는 것이 아니고, 단지 우선순위가 10인 스레드는 우선순위가 1인 스레드보다 좀 더 많이 실행 큐에 포함되어, 좀 더 많은 작업 시간을 할당받을 뿐이다.

> main() 메소드를 실행하는 스레드의 우선순위는 언제나 5
> 

그리고 스레드의 우선순위는 해당 스레드를 생성한 스레드의 우선순위를 상속받게 된다.

```java
class ThreadEx8 {
    public static void main(String args[]) {
        ThreadEx8_1 th1 = new ThreadEx8_1();
        ThreadEx8_2 th2 = new ThreadEx8_2();

        th2.setPriority(7);

        System.out.println("Priority of th1(-) : " + th1.getPriority() );
        System.out.println("Priority of th2(|) : " + th2.getPriority() );
        th1.start();
        th2.start();
    }
}

class ThreadEx8_1 extends Thread {
    public void run() {
        for(int i=0; i < 300; i++) {
            System.out.print("-");
            for(int x=0; x < 10000000; x++);
        }
    }
}

class ThreadEx8_2 extends Thread {
    public void run() {
        for(int i=0; i < 300; i++) {
            System.out.print("|");
            for(int x=0; x < 10000000; x++);
        }
    }
}
```

```java
Priority of th1(-) : 5
Priority of th2(|) : 7
-|-|---------------------------------------------------------------------------
------------------------------------------------|------------|||||||||||||||||||
||||----------|||||||||||||||||||||||||||||||---|-|||||---------||||||||||||----
----|||-||||||-||---|--|||||----||||||||----------------------------------------
--------------------------------||||------------------|||||||||||||||||||||||||-
---|||-|||||-----||||---||||||------|||||||||||||||||||||||||||||||||||||||------
------|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
|||||||||||||||||||||||||||||||||||||||
```

싱글코어에선 우선순위에 따른 차이가 크지만(A가 더 빨리 작업됨),

멀티코어에선 위의 코드가 수행하는데 큰 효과가 없었다.

멀티코어라해도 OS마다 다른 방식으로 스케쥴링을 진행하기 때문에, 어떤 OS에서 실행하느냐에 따라 다른 결과를 얻을 수 있다. 특정 OS의 스케쥴링 정책과 JVM의 구현을 직접 확인해봐야 우선순위에 따른 구현을 확인해 볼 수 있을 것이다.

멀티코어환경에선 우선순위 부여 대신 작업에 우선순위를 두어 PriorityQueue에 저장해 놓고, 우선순위가 높은 작업이 먼저 처리되도록 하는 것이 나을 수도 있다.

# 06 스레드 그룹(thread group)

스레드 그룹(thread group)이란 서로 관련이 있는 스레드를 하나의 그룹으로 묶어 다루기 위한 장치

자바에서는 스레드 그룹을 다루기 위해 ThreadGroup이라는 클래스를 제공

이러한 스레드 그룹은 다른 스레드 그룹을 포함할 수도 있으며, 이렇게 포함된 스레드 그룹은 트리 형태로 연결된다.

이때 스레드는 자신이 포함된 스레드 그룹이나 그 하위 그룹에는 접근할 수 있지만, 다른 그룹에는 접근할 수 없다.

| 생성자 / 메서드 | 설 명 |
| --- | --- |
| ThreadGroup(String name) | 지정된 이름의 새로운 쓰레드 그룹을 생성 |
| ThreadGroup(ThreadGroup parent, String name) | 지정된 쓰레드 그룹에 포함되는 새로운 쓰레드 그룹을 생성 |
| int activeCount() | 쓰레드 그룹에 포함된 활성상태에 있는 쓰레드의 수 반환 |
| int activeGroupCount() | 쓰레드 그룹에 포함된 활성상태에 있는 쓰레드 그룹의 수 반환 |
| void checkAccess() | 현재 실행중인 쓰레드가 쓰레드 그룹을 변경할 권한이 있는지 체크.만일 권한이 없다면 SecurityException을 발생시킨다. |
| void destroy() | 쓰레드 그룹과 하위 쓰레드 그룹까지 모두 삭제한다.단, 쓰레드 그룹이나 하위 쓰레드 그룹이 비어있어야 한다. |
| int enumerate(Thread[] list)int enumerate(Thread[] list, boolean recurse)int enumerate(ThreadGroup[] list)int enumerate(ThreadGroup[] list, boolean recurse) | 쓰레드 그룹에 속한 쓰레드 또는 하위 쓰레드 그룹의 목록을 지정된 배열에 담고 그 개수를 반환.두 번째 매개변수인 recurse의 값을 true로 하면 쓰레드 그룹에 속한 하위 쓰레드 그룹에 쓰레드 또는 쓰레드 그룹까지 배열에 담는다. |
| int getMaxPriority() | 쓰레드 그룹의 최대우선순위를 반환 |
| String getName() | 쓰레드그룹의 이름을 반환 |
| ThreadGroup getParent() | 쓰레드 그룹의 상위 쓰레드그룹을 반환 |
| void interrupt() | 쓰레드 그룹에 속한 모든 쓰레드를 interrupt |
| boolean isDaemon() | 쓰레드 그룹이 데몬 쓰레드그룹인지 확인 |
| boolean isDestroyed() | 쓰레드 그룹이 삭제되었는지 확인 |
| void list() | 쓰레드 그룹에 속한 쓰레드와 하위 쓰레드그룹에 대한 정보를 출력 |
| boolean parentOf(ThreadGroup g) | 지정된 쓰레드 그룹의 상위 쓰레드 그룹인지 확인 |
| void setDaemon(boolean daemon) | 쓰레드 그룹을 데몬 쓰레드그룹으로 설정/해제 |
| void setMaxPriority(int pri) | 쓰레드 그룹의 최대우선순위를 지정 |
1. 쓰레드를 쓰레드 그룹에 포함시키려면 Thread생성자를 이용한다.

> Thread(ThreadGorup group, String name)
> 

1. **모든 쓰레드는 반드시 쓰레드 그룹에 포함**되어 있어야한다.
    1. **기본적으로 자신을 생성한 쓰레드와 같은 쓰레드 그룹에 속하게 된다.**
    2. JVM은 main과 system이라는 쓰레드 그룹을 만들고 JVM운영에 필요한 쓰레드들을 생성해서 이 쓰레드 그룹에 포함시킨다.

> 가비지컬렉션은 system, main메서드는 main 쓰레드 그룹에 속함.
> 

1. 우리가 생성하는 모든 쓰레드 그룹은 main쓰레드 그룹의 하위 쓰레드 그룹이 되고, 지정하지 않고 생성한 쓰레드는 자동적으로 main쓰레드 그룹에 속하게 된다.

Thread의 쓰레드 그룹과 관련된 메서드는 다음과 같다.

> ThreadGroup getThreadGroup() : 쓰레드 자신이 속한 쓰레드 그룹을 반환한다.
> 

```java
class ThreadEx9 {
	public static void main(String args[]) throws Exception {
		ThreadGroup main = Thread.currentThread().getThreadGroup();
		ThreadGroup grp1 = new ThreadGroup("Group1");
		ThreadGroup grp2 = new ThreadGroup("Group2");

		// ThreadGroup(ThreadGroup parent, String name) 
		ThreadGroup subGrp1 = new ThreadGroup(grp1,"SubGroup1"); 

		grp1.setMaxPriority(3);	// 쓰레드 그룹 grp1의 최대우선순위를 3으로 변경.
		
		Runnable r = new Runnable() {
			public void run() {
				try { 
					Thread.sleep(1000); // 쓰레드를 1초간 멈추게 한다.
				} catch(InterruptedException e) {}
			}	
		};

         // Thread(ThreadGroup tg, Runnable r, String name)
		Thread th1 = new Thread(grp1,     r, "th1"); 
		Thread th2 = new Thread(subGrp1,  r, "th2");
		Thread th3 = new Thread(grp2,     r, "th3");   

		th1.start();
		th2.start();
		th3.start();

		System.out.println(">>List of ThreadGroup : "+ main.getName() 
                           +", Active ThreadGroup: " + main.activeGroupCount()
                           +", Active Thread: "      + main.activeCount());
		main.list();
	}
}
```

```java
>>List of ThreadGroup : main, Active ThreadGroup: 3, Active Thread: 5
java.lang.ThreadGroup[name=main,maxpri=10]
    Thread[main,5,main]
    Thread[Monitor Ctrl-Break,5,main]
    java.lang.ThreadGroup[name=Group1,maxpri=3]
        Thread[th1,3,Group1]
        java.lang.ThreadGroup[name=SubGroup1,maxpri=3]
            Thread[th2,3,SubGroup1]
    java.lang.ThreadGroup[name=Group2,maxpri=10]
        Thread[th3,5,Group2]
```

정보 출력전에 쓰레드가 종료될 수 있으므로, sleep()을 호출해서 1초간 멈춤.

쓰레드 그룹에 포함된 하위 쓰레드 그룹이나 쓰레드는 들여쓰기를 이용해서 구별되어 있음.

새로 생성한 모든 쓰레드 그룹은 main쓰레드 그룹의 하위 쓰레드 그룹으로 포함.

쓰레드 그룹의 우선순위 설정(setMaxPriority)은 쓰레드가 그룹에 추가되기 이전에 설정되어야함.

후에 여기에 속한 쓰레드 그룹과 쓰레드가 영향 받음.

참조변수 없이 쓰레드를 생성해서 실행시켰지만, 가비지 컬렉션에 의해 제거되진 않음.

→ 쓰레드의 참조가 쓰레드그룹에 저장되어 있기 때문이다.