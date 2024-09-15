# 자바 고급1, 멀티스레드와 동시성
***
## 프로레스와 스레드
### 멀티태스킹과 멀티프로세싱
> 멀티태스킹

프로그램의 실행 시간을 분할해서 마치 동시에 실행되는 것 처럼 하는 기법을 시분할(Time Sharing, 시간 공유)기법이라 한다.
이런 방식을 사용하면 CPU 코어가 하나만 있어도 여러 프로그램이 동시에 실행되는 것 처럼 느낄 수 있다.
이렇게 하나의 컴퓨터 시스템이 동시에 여러 작업을 수행하는 능력을 멀티태스킹(Multitasking)이라 한다.

> 멀티프로세싱

컴퓨터 시스템에서 둘 이상의 프로세스(CPU 코어)를 사용하여 여러 작업을 동시에 처리하는 기술을 의미한다.
멀티프로세싱 시스템은 하나의 CPU 코어만을 사용하는 시스템보다 동시에 더 많은 작업을 처리할 수 있다.

> 멀티태스킹 VS 멀티프로세싱

멀티태스킹은 운영체제 소프트웨어의 관점이고 멀티프로세싱은 하드웨어 장비의 관점이다.
* 멀티태스킹
  * 단일 CPU(단일 CPU 코어)가 여러 작업을 동시에 수행하는 것 처럼 보이게 하는 것을 의미한다.
  * 소프트웨어 기반의 CPU 시간을 분할하여 각 작업에 할당한다.
  * 예: 현대 운영 체제에서 여러 애플리케이션이 동시에 실행되는 환경
* 멀티프로세싱
  * 여러 CPU(여러 CPU 코어)를 사용하여 동시에 여러 작업을 수행하는 것을 의미한다.
  * 하드웨어 기반으로 성능을 향상시킨다.
  * 예: 다중 코어 프로세서를 사용하는 현대 컴퓨터 시스템

### 프로세스와 스레드
> 프로세스
* 프로그램은 실제 실행하기 전까지는 단순한 파일에 불과하다.
* 프로그램을 실행하면 프로세스가 만들어지고 프로그램이 실행된다.
* 이렇게 운영체제 안에서 실행중인 프로그램을 프로세스라 한다.
* 프로세스는 실행중인 프로그램의 인스턴스이다.
* 자바 언어로 비유를 하면 프로그램은 클래스이고, 프로세스는 인스턴스이다.

프로세스는 실행중인 프로그램의 인스턴스이다. 각 프로세스는 `독립적인 메모리 공간`을 가지고 있으며, 
운영체제에서 별도의 작업 단위로 분리해서 관리된다.
각 프로세스는 별도의 메모리 공간을 갖고 있기 때문에 서로 간섭하지 않는다.
그리고 프로세스가 서로의 메모리에 직접 접근할 수 없다. 프로세스는 이렇듯 서로 격리되어 관리되기 때문에,
하나의 프로세스가 충돌해도 다른 프로세스에는 영향을 미치지 않는다.

> 프로세스의 메모리 구성
* 코드 섹션: 실행할 프로그램의 코드가 저장되는 부분
* 데이터 섹션: 전역 변수 및 정적 변수가 저장되는 부분
* 힙(Heap): 동적으로 할당되는 메모리 영역
* 스택(Stack): 메서드(함수) 호출 시 생성되는 지역 변수와 반환 주소가 저장되는 영역(스레드에 포함)

> 스레드

프로세스는 하나 이상의 스레드를 반드시 포함한다.
스레드는 프로세스 내에서 실행되는 작업의 단위이다. 한 프로세스 내에서 여러 스레드가 존재할 수 있으며,
이들은 프로세스가 제공하는 동일한 메모리 공간을 공유한다. 스레드는 프로세스보다 단순하므로 생성 및 관리가 단순하고 가볍다.

> 스레드의 메모리 구성
* 공유 메모리: 같은 프로세스의 코드 섹션, 데이터 섹션, 힙은 프로세스 안의 모든 스레드가 공유한다.
* 개별 스택: 각 스레드는 자신의 스택을 갖고 있다.

> 멀티스레드가 필요한 이유

하나의 프로그램도 그 안에서 동시에 여러 작업이 필요하다.
* 워드 프로그램은 문서를 편집하면서, 문서가 자동으로 저장되고, 맞춥법 검사도 함께 수행된다.
* 유튜브는 영상을 보는 동안, 댓글도 달 수 있다.

운영체제 관점에서 보면 다음과 같이 구분할 수 있다.
* 워드 프로그램 - 프로세스A
  * 스레드1: 문서 편집
  * 스레드2: 자동 저장
  * 스레드3: 맞춤법 검사
* 유튜브 - 프로세스B
  * 스레드1: 영상 재생
  * 스레드2: 댓글

### 스레드와 스케줄링
프로세스는 실행 환경과 자원을 제공하는 컨테이너 역할을 하고, 실제 CPU를 사용해서 코드를 하나하나 실행하는 것은 스레드이다.

> 단일 코어 스케줄링
* 운영체제는 내부에서 스케줄링 큐를 가지고 있고, 각각의 스레드는 스케줄링 큐에서 대기한다.
* 스레드A1, 스레드B1, 스레드B2가 스케줄링 큐에서 대기한다.
* 운영체제는 스레드A1을 큐에서 꺼내고 CPU를 통해 실행한다.
* 이때 스레드A1이 프로그램의 코드를 수행하고, CPU를 통해 연산도 일어난다.
* 운영체제는 스레드A1을 잠시 멈추고, 스케줄링 큐에 다시 넣는다.
* 운영체제는 스레드B1을 큐에서 꺼내고 CPU를 통해 실행한다.
* 이런 과정을 반복해서 수행한다.

> 멀티 코어 스케줄링
* 운영체제는 내부에서 스케줄링 큐를 가지고 있고, 각각의 스레드는 스케줄링 큐에서 대기한다.
* 스레드A1, 스레드B1, 스레드B2가 스케줄링 큐에서 대기한다.
* 스레드A1, 스레드B1을 CPU코어1, CPU코어2에서 병렬로 실행한다. 스레드B2는 스케줄링 큐에 대기한다.
* 스레드A1, 스레드B1의 수행을 잠시 멈추고, 스레드A1, 스레드B1을 스케줄링 큐에 다시 넣는다.
* 스케줄링 큐에서 대기중인 스레드B2를 CPU코어1에서 실행한다.
* 스케줄링 큐에서 대기중인 스레드A1을 CPU코어2에서 실행한다.
* 이런 과정을 반복해서 수행한다.

### 컨텍스트 스위칭
멀티태스킹이 반드시 효율적인 것 만은 아니다.
스레드A를 멈추는 시점에 CPU에서 사용하던 값들을 메모리에 저장해두어야 한다.
그리고 이후에 스레드A를 다시 실행할 때 이 값들을 CPU에 다시 불러와야 한다.
이러 과정을 `컨텍스트 스위칭(Context switching)`이라 한다.

멀티스레드는 대두분 효율적이디만, 컨텍스트 스위칭 과정이 필요하므로 항상 효율적인것은 아니다.
예를 들어 1~10,000까지 더해야 한다고 가정해보자. 이 문제는 둘로 나눌 수 있다.
* 스레드1: 1~5,000까지 더함
* 스레드2: 5001~10,000까지 더함
* 마지막에 스레드1의 결과와 스레드2의 결과를 더함

CPU 코어가 2개
CPU 코어가 2개 있다면 스레드1, 스레드2로 나누어 멀티스레드로 병렬 처리하는게 효율적이다. 모든 CPU를 사용하므로 연산을 2배 빠르게 처리할 수 있다.

CPU 코어가 1개
CPU 코어가 1개 있는데, 스레드를 2개로 만들어서 연산을 하면 중간 중간 컨텍스트 스위칭 비용이 발생한다.
운영체제 스케줄링 방식에 따라 다르겠지만, 스레드1을 1~1000 정도까지 연산한 상태에서 잠시 멈추고 스레드2를 5001~6001까지 연산하는 식으로 반복할 수 있다.
이때 CPU는 스레드1을 멈추고 다시 실행할 때 어디까지 연산했는지 알아야 하고, 그 값을 CPU에 다시 불러와야 한다. 결과적으로 이렇게 반복 할 때마다 컨텍스트 스위칭 비용이 든다.
이런 경우 단일 스레드로 컨텍스트 스위칭 비용 없이 연산하는게 더 효율적일 수 있다.

CPU 4개, 스레드 2개
스레드의 숫자가 너무 적으면 모든 CPU를 100% 다 활용할 수 없지만, 스레드가 몇개 없으므로 컨텍스트 스위칭 비용이 줄어든다.

CPU 4개, 스레드 100개
스레드의 숫자가 너무 많으면 CPU를 100% 다 활용할 수 있지만 컨텍스트 스위칭 비용이 늘어난다.

CPU 4개, 스레드 4개
스레드의 숫자를 CPU의 숫자에 맞춘다면 CPU를 100% 활용할 수있고, 컨텍스트 스위칭 비용도 자주 발생하지 않기 때문에 최적의 상태가 된다.
이상적으로 CPU 코어 수 + 1 개 정도로 스레드를 맞추면 특정 스레드가 잠시 대기할 때 남은 스레드를 활용할 수 있다.

CPU 바운드 작업 vs I/O 바운드 작업
* CPU 바운드 작업
  * CPU 연산 능력을 많이 요구하는 작업을 의미한다.
  * 이러한 작업은 주로 계산, 데이터 처리, 알고리즘 실행 등 CPU 처리 속도가 작업 완료 시간을 결정하는 경우이다.
  * 예시: 복잡한 수학 연산, 데이터 분석, 비디오 인코딩, 과학적 시뮬레이션 등
* I/O 바운드 작업
  * 디스크, 네트워크, 파일 시스템 등과 같은 입출력(I/O) 작업을 많이 요구하는 작업을 의미한다.
  * 이러한 작업은 I/O 작업이 완료될 때까지 대기 시간이 많이 발생하며, CPU는 상대적으로 유휴(대기) 상태에 있는 경우가 많다.
  * 예시: 데이터베이스 쿼리 처리, 파일 읽기/쓰기, 네트워크 통신, 사용자 입력 처리 등.

스레드의 숫자는 CPU 바운드 작업이 많은가, I/O 바운드 작업이 많은가에 따라 다르게 설정해야 한다.
* CPU 바운드 작업: CPU 코어 수 + 1개
* I/O 바운드 작업: CPU 코어 수 보다 많은 스레드를 생성, CPU를 최대한 사용할 수 있는 숫자까지 스레드 생성
  * CPU를 많이 사용하지 않으므로 성능테스트를 통해 CPU를 최대한 활용하는 숫자까지 스레드 생성
  * 단 너무 많은 스레드를 생성하면 컨텍스트 스위칭 비용도 함께 증가 - 적절한 성능 테스트 필요

***

## 스레드 생성과 실행
### 자바 메모리 구조
* 메서드 영역: 메서드 영역은 프로그램을 실행하는데 필요한 공통 데이터를 관리한다. 이 영역은 프로그램의 모든 영역에서 공유 한다.
  * 클래스 정보: 클래스의 실행코드(바이트 코드), 필드, 메서드와 생성자 코드등 모든 실행 코드가 존재한다.
  * static 영역: static 변수들을 보관한다.
  * 런타임 상수 풀: 프로그램을 실행하는데 필요한 공통 리터럴 상수를 보관한다.
* 스택 영역: 자바 실행 시, 하나의 실행 스택이 생성된다. 각 스택 프레임은 지역 변수, 중간 연산 결과, 메서드 호출 정보 등을 포함한다.
  * 스택 프레임: 스택 영역에 쌓이는 네모 박스 하나의 스택 프레임이다. 메서드를 호출할 때마다 하나의 스택 프레임이 쌓이고, 메서드가 종료되면 해당 스택 프레임이 제거 된다.
* 힙 영역: 객체(인스턴스)와 배열이 생성되는 영역이다. 가비지 컬렉션(GC)이 이루어지는 주요 영역이며, 더 이상 참조되지 않은 객체는 GC에 의해 제거 된다.

> 참고: 스택 영역은 더 정확히는 각 스레드별로 하나의 실행 스택이 생성된다. 따라서 스레드 수 만큼 스택이 생성된다.

> 스레드 생성

스레드를 만들 때는 `Thread`클래스를 상송 받는 방법과 `Runable`인터페이스를 구현하는 방법이 있다.

> 스레드 생성 - Thread 상속

자바는 많은 것을 객체로 다룬다. 자바가 예외를 객체로 다루듯이, 스레드도 객체로 다룬다.
스레다가 필요하면, 스레드 객체를 생성해서 사용하면 된다.

1. main 스레드 생성 및 실행 (프로세스가 동작 하려면 최소한 하나의 스레드가 있어야 한다.)
2. helloThread 생성(helloThread가 사용할 전용 스택 공간이 마련된다.)
3. helloThread 실행(`run()` 메서드의 스택 프레임을 스택에 올리면서 `run()` 메서드를 시작한다.)

> start() vs run()

스레드의 `start()` 대신에 재정의한 `run()` 메서드를 직접 호출하면 어떻게 될까?

1. main 스레드 생성 및 실행 (프로세스가 동작 하려면 최소한 하나의 스레드가 있어야 한다.)
2. main 스레드는 HelloThread 인스턴스에 있는 `run()`이라는 메서드를 호출한다.
3. main 스레드가 `run()` 메서드를 실행했기 때문에 main 스레드가 사용하는 스택위에 `run()` 스택 프레임이 올라간다.
4. 결과적으로 main 스레드가 모든 것을 처리하게 된다.

> 데몬 스레드

스레드는 사용자(User) 스레드와 데몬(daemon) 스레드 2가지 종류로 구분할 수 있다.

#### 사용자 스레드
* 프로그램의 주요 작업을 수행한다.
* 작업이 완료될 때까지 실행된다.
* 모든 사용자 스레드가 종료되면 JVM도 종료된다.

#### 데몬 스레드
* 백그라운드에서 보조적인 작업을 수행한다.
* 모든 사용자 스레드가 종료되면 데몬스레드는 자동으로 종료된다.

JVM은 데몬 스래드의 실행 완료를 기다려주지 않고 종료된다.
데몬 스레드가 아닌 모든 스레드가 종료되면, 자바 프로그램도 종료된다.

* `setDaemonT(true)`: 데몬 스레드로 설정(기본값: false)
* 데몬 스레드 여부는 `start()` 실행 전에 결정해야 한다. 이후에는 변경되지 않는다.

> 스레드 생성 - Runnable 구현

`Runnable` 인터페이스를 상속하고 `run()` 메서드를 오버라이드 한다.
스레드 객체를 생성할 때, 실행할 작업을 생성자로 전달하면 된다.

#### Thread 상속 VS Runnable 구현
스레드를 사용할 때는 Thread를 상속 받는 방법보다 `Runnable` 인터페이스를 구현하는 방식을 사용하자.

#### Thread 상속 방식
* 장점
  * 간단한 구현: `Thread` 클래스를 상속 받아 `run()` 메서드만 재정의하면 된다.
* 단점
  * 상속의 제한: 자바는 단일 상속만을 허용하므로 이미 다른 클래스를 상속 받고 있는 경우 `Thread` 클래스를 상속 받을 수 없다.
  * 유연성 부족: 인터페이스를 사용하는 방법에 비해 유연성이 떨어진다.

#### Runnable 인터페이스 구현 방식
* 장점
  * 상속의 자유로움:`Runnable` 인터페이스 방식은 다른 클래스를 상속받아도 문제없이 구현할 수 있다.
  * 코드의 분리: 스레드와 실행할 작업을 분리하여 코드의 가독성을 높일 수 있다.
* 단점
  * 코드가 약간 복잡해질 수 있다. `Runnable` 객체를 생성하고 이를 `Thread`에 전달하는 과정이 추가 된다.

***

## 스레드 제어와 생명주기
### 스레드 기본 정보
> 1. 스레드 생성

스레드를 생성할 때는 실행할 `Runnable` 인터페이스의 구현체와, 스레드의 이름을 전달할 수 있다.

```java
Thread myThread = new Thread(new HelloRunnable(), "myThread");
```
* Runnable 인터페이스: 실행할 작업을 포함하는 인터페이스다.
* 스레드 이름: "myThead"라는 이름으로 스레드를 생성한다. 이 이름은 디버깅이나 로깅 목적으로 유용하다. 이름을 생략하면 `Thread-0`, `Thread-1`과 같은 임의의 이름이 생성된다.

> 2. 스레드 객체 정보
* `Thread` 클래스의 `toString()` 메서드는 스레드 ID, 스레드 이름, 우선순위, 스레드 그룹을 포함하는 문자열을 반환한다.
* 예) Thread[#2, mythread, 5, main]

> 3. 스레드 ID
* `threadId()`: 스레드의 고유 식별자를 반환하는 메서드이다. 이 ID는 JVM 내에서 각 스레드에 대해 유일하다. ID는 스레드가 생성될 때 할당되며, 직접 지정할 수 없다.

> 4. 스레드 이름
* `getName()`: 스레드의 이름을 반환하는 메서드이다. 생성자에 `"myThread"`라는 이름을 지정했기 때문에, 이 값이 반환된다. 참고로 스레드 ID는 중복되지 않지만, 스레드 이름은 중복될 수 있다.

> 5. 스레드 우선순위
* `getPriority()`: 스레드의 우선순위를 반환하는 메서드이다. 우선순위는 1(가장 낮음)에서 10(가장 높음)까지의 값을 설정할 수있으며, 기본값은 5이다. `setPriority()`메서드를 사용해서 우선순위를 변경할 수 있다.
* 우선순위는 스레드 스케줄러가 어떤 스레드를 우선 실행할지 결정하는 데 사용된다. 하지만 실제 실행 순서는 JVM 구현과 운영 체제에 따라 달라질 수 있다.

>6. 스레드 그룹
* `getThreadGroup()`: 스레드가 속한 스레드 그룹을 반환하는 메서드이다. 스레드 구릅은 스레드를 그룹화하여 관리할 수 있는 기능을 제공한다. 기본적으로 모든 스레드는 부모 스레드와 동일한 스레드 그룹에 속하게 된다.
* 스레드 그룹은 여러 스레드를 하나의 그룹으로 묶어서 특정 작업(예: 일괄 종료, 우선순위 설정 등)을 수행할 수 있다.
* 부모 스레드(Parent Thread): 새로운 스레드를 생성하는 스레드를 의미한다. 스레드는 기본적으로 다른 스레드에 의해 생성된다. 이러한 생성 관계에서 새로운 생성된 스레드는 생성한 스레드를 부모로 간주한다. 
예를 들어 myThread는 main 스레드에 의해 생성되었으므로 main 스레드가 부모 스레드이다.
* main 스레드는 기본적으로 제공되는 main 스레드 그룹에 소속되어있다. 따라서 myThread도 부모 스레드인 main 스레드의 그룹인 main 스레드 그룹에 소속된다.

7. 스레드 상태
* `getState()`: 스레드의 현재 상태를 반환하는 메서드이다. 반환되는 값은 `Thread.State` 열거형에 정의된 상수 중 하나이다.
  * NEW: 스레드가 아직 시작되지 않은 상태이다.
  * RUNNABLE: 스레드가 실행중이거나 실행될 준비가 된 상태이다.
  * BLOCKED: 스레드가 동기화 락을 기다리는 상태이다.
  * WAITING: 스레드가 다른 스레드의 특정 작업이 완료되기를 기다리는 상태이다.
  * TIMED_WAITING: 일정 시간 동안 기다리는 상태이다.
  * TERMINATED: 스레드가 실행을 마친 상태이다.

### 스레드의 상태
* NEW(새로운 상태): 스레드가 생성되었으나 아직 시작되지 않은 상태.
* RUNNABLE(실행가능 상태): 스레드가 실행중이거나 실행 준비가 된 상태.
* 일시 중지 상태들(Suspended States)
  * BLOCKED(차단 상태): 스레드가 종기화 락을 기다리는 상태.
  * WAITING(대기상태): 스레드가 무기한으로 다른 스레드의 작업을 기다리는 상태.
  * TIME WAITING(시간 제한 대기 상태): 스레드가 일정 시간 동안 다른 스레드의 작업을 기다리는 상태.
* TERMINATED(종료 상태): 스레드의 실행이 완료된 상태.

1. NEW
 * 스레드가 아직 시작되지 않은 상태이다.
 * 이 상태에서는 Thread 객체가 생성되었지만, `start()`메서드가 호출되지 않은 상태이다.

2. RUNNALBLE
   * 스레드가 실행 준비가 된 상태이다. 이 상태에서는 스레드는 실제로 CPU에서 실행될 수 있다.
   * 예: `thread.start();`
   * 이 상태는 스레드가 실행 될 준비가 되었음을 나타내며, 실제로 CPU에서 실행될 수 있는 상태이다. 그러나 RUNNALBLE 상태에 있는 모든 스레드가 동시에 실행되는 것은 아니다.
   운영체제의 스케줄러가 각 스레드에 CPU 시간을 할당하여 실행하기 때문에, RUNNALBE 상태에 있는 스레드는 스케줄러의 실행 대기열에 포함되어 있다가 차례로 CPU에서 실행된다.
   * 운영체제 스케줄러의 실행 대기열에 있든, CPU에서 실제 실행되고 있든 모두 RUNNABLE 상태이다. 자바에서 둘을 구분해서 확인할 수는 없다.
   * 보통 실행 상태라고 부른다.

3. BLOCKED
   * 스레드가 다른 스레드에 의해 동기화 락을 얻기 위해 기다리는 상태이다.
   * 예를 들어 synchronized 블록에 진입하기 위해 락을 얻어야 하는 경우 이 상태에 들어간다.
   * 예: `synchronized (lock) {...}` 코드 블록에 진입하려고 할 때, 다른 스레드가 이미 `lock`을 가지고 있는 경우

4. WAITING
   * 스레드가 다른 스레드의 특정 작업이 완료되기를 무기한 기다리는 상태이다.
   * `wait()`, `join()` 메서드가 호출될 때 이 상태가 된다.
   * 스레드는 다른 스레드가 `notify()` 또는 `notifyAll()` 메서드를 호출하거나, `join()`이 완료될 때까지 기다린다.
   * 예: `object.wait();`

5. TIMED WAITING
   * 스레드가 특정 시간 동안 다른 스레드의 작업이 완료되기를 기다리는 상태이다.
   * `sleep(long mills)`, `wait(long timeout)`, `join(long mills)` 메서드가 호출될 때 이 상태가 된다.
   * 주어진 시간이 경과하거나 다른 스레드가 해당 스레드를 깨우면 이 상태에서 벗어난다.
   * 예: `Thread.sleep(1000);`

6. TERMINATED
  * 스레드의 실행이 완료된 상태이다.
  * 스레드가 정상적으로 종료되거나, 예외가 발생하여 종료된 경우 이 상태로 들어간다.
  * 스레드는 한 번 종료되면 다시 시작할 수 없다.

> 자바 스레드의 상태 전이 과정
1. NEW -> RUNNABLE: `start()`메서드를 호출하면 스레드가 `RUNNABLE` 상태로 전이된다.
2. RUNNABLE -> BLOCKED/WAITING/TIMED WAITING: 스레드가 락을 얻지 못하거나, `wait()` 또는 `sleep()` 메서드를 호출할 때 해당 상태로 전이된다.'
3. BLOCKED/WAITING/TIMED WAITING -> RUNNABLE: 스레드가 락을 얻거나, 기다림이 완료되면 다시 RUNNABLE 상태로 돌아간다.
4. RUNNABLE -> TERMINATED: 스레드의 `run()`메서드가 완료되면 스레드는 `TERMINATED` 상태가 된다.

> 체크 예외 재정의

자바에서 메서드를 재정의 할 때, 재정의 메서드가 지켜야할 예외와 관련된 규칙이 있다.
* 체크 예외
  * 부모 메서드가 체크 예외를 던지지 않는 경우, 재정의된 자식 메서드도 체크 예외를 던질 수 없다.
  * 자식 메서드는 부모 메서드가 던질 수 있는 체크 예외의 하위 타입만 던질 수 있다.
* 언체크(런타임) 예외
  * 예외 처리를 강제하지 않으므로 상관없이 던질 수 있다.

`Runnable`인터페이스의 `run()` 메서드는 아무런 체크 예외를 던지지 않는다. 따라서 `Runnable`인터페이스의 `run()` 메서드를 재정의 하는 곳에서는 체크 예외를 밖으로 던질 수 없다.

자바는 왜 이런 제약을 두는 것일까? 
부모 클래스의 메서드를 호출하는 클라이언트 코드는 부모 메서드가 던지는 특정 예외만을 처리하도록 작성된다. 
자식 클래스가 더 넓은 범위의 예외를 던지면 해당 코드는 모든 예외를 제대로 처리하지 못할 수도 있다.
예상하지 못한 런타임 오류를 초래할 수 있다.

체크 예외 재정의 규칙
* 자식 클래스에 재정의된 메서드는 부모 메서드가 던질 수 있는 체크 예외의 하위 타입만을 던질 수 있다.
* 원래 메서드가 체크 예외를 던지지 않는 경우, 재정의된 메서드도 체크 예외를 던질 수 없다.

안전한 예외 처리
체크 예외를 `run()` 메서드에서 던질 수 없도록 강제함으로써, 개발자는 반드시 체크 예외를 `try-catch` 블록 내에서 처리하게 된다.
이는 예외 발생시 예외가 적절히 처리되지 않아서 프로그램이 비정상 종료되는 상황을 방지할 수 있다.
특히 멀티스레딩 환경에서는 예외 처리를 강제함으로써 스레드의 안정성과 일관성을 유지할 수 있다.

> join

join이 필요한 상황
`main` 스레드가 1~100까지 더하는 작업을 `thread-1`, `thread-2`에 각각 작업을 나누어 지시하면 CPU 코어를 더 효율적으로 활용할 수 있다.
* `thread-1`: 1~50까지 더하기
* `thread-2`: 51~100까지 더하기
* `main`: 두 스레드의 결과를 받아서 더하기

`main` 스레드는 두 스레드를 시작후 실행이 끝날 때까지 기다리지 않는다. 다른 두 스레드를 실행만 하고 자신의 다음 코드를 실행한다.
`main` 스레드가 두 스레드의 작업이 끝날 때까지 기다리게 하기 위해서는 `join()` 메서드를 사용해야 한다.

> join - 특정 시간 만큼만 대기
* `join()`: 호출 스레드는 대상 스레드가 완료 될 때까지 무한정 대기
* `join(ms)`: 호출 스레드는 특정 시간 만큼만 대기한다. 호출 스레드는 지정한 시간이 지나면 다시 `RUNNABLE` 상태가 되면서 다음 코드를 수행한다.

### 인터럽트
인터럽트를 사용하면, `WAITING`, `TIMED_WATING` 같은 대기 상태의 스레드를 직접 깨워서, 동작하는 `RUNNABLE` 상태로 만들 수 있다.

* 특정 스레드의 인스턴스에 `interrupt()` 메서드를 호출하면, 해당 스레드에 인터럽트가 발생한다.
  * 이때 인터럽트를 받은 스레드는 대기 상태에서 깨어나 `RUNNABLE` 상태가 되고, 코드를 정상 수행한다.
  * 이때 `InterruptException`을 `catch`로 받아서 정상 흐름으로 변경하면 된다.
* 참고로 `interrupt()` 호출 했다고 즉각 `InterruptException`이 발생하는 것은 아니다. 오직 `sleep()`처럼 `InterruptException`을 던지는 메서드를 호출 하거나 또는 호출 중일 때 예외가 발생한다.

인터럽트가 발생 한 후 스레드의 인터럽트 상태를 정상(`false`)으로 돌리지 않으면 `InterruptException`을 던지는 메서드를 만나게 되면 계속 인터럽트가 발생하게 된다.
자바에서 인터럽트가 한번 발생하면, 스레드의 인터럽트 상태를 다시 정상(`false`)으로 돌리는 것은 이런 이유 때문이다.

> Thread.interrupted()

스레드의 인터럽트 상태를 단순히 확인만 하는 용도라면 `isInterrupted()`를 사용하면 된다.
하지만 직접 체크해서 사용 할 때는 `Thread.interrupted()`를 사용해야 한다.
* 스레드가 인터럽트 상태라면 `true`를 반환하고 해당 스레드의 인터럽트 상태를 `false`로 변경한다.
* 스레드가 인터럽트 상태가 아니라면 `false`를 반환하고, 해당 스레드의 인터럽트 상태를 변경하지 않는다.

> yield

어떤 스레드를 얼마나 실행할지는 운영체제가 스케줄링을 통해 결정한다. 그런데 특정 스레드가 크게 바쁘지 않은 상황이어서 다른 스레드에 CPU 실행 기회를 양보하고 싶을 수 있다.
이렇게 양보하면 스케줄링 큐에 대기중인 다른 스레드가 CPU 실행 기회를 더 빨리 얻을 수 있다.

* 자바의 스레드가 `RUNNABLE` 상태일 때, 운영체제의 스케줄링은 다음과 같은 상태들을 가질 수 있다.
  * 실행 상태(RUNNING): 스레드가 CPU에서 실제 실행 중이다.
  * 실행 대기(READY): 스레드가 실행될 준비가 되었지만, CPU가 바빠서 스케줄링 큐에서 대기 중이다.

sleep() vs yield()
* sleep()은 `RUNNABLE` -> `TIMED_WAITING` -> `RUNNABLE`로 변경되는 복잡한 과정을 거치고, 또 특정 시간만큼 스레드가 실행되지 않은 단점이 있다.
  * 양보할 스레드가 없다면 차라리 스레드를 더 실행하는 것이 나은 선택일 수 있다. 나머지 스레드가 모두 대기 상태로 쉬고 있어도 내 스레드까지 잠깐 실행되지 않는 것이다.

* yield()는 현재 실행 중인 스레드가 자발적으로  CPU를 양보하여 다른 스레드가 실행될 수 있도록 한다.
  * yield()를 호출한 스레드는 `RUNNALBE` 상태를 유지하면서 CPU를 양보한다. 즉, 이 스레드는 다시 스케줄링 큐에 들어가면서 다른 스레드에게 CPU 사용 기회를 넘긴다.
  * yield()는 운영체제의 스케줄러에게 단지 힌트를 제공할 뿐, 강제적인 실행 순서를 지정하지 않는다. 그리고 반드시 다른 스레드가 실행되는 것도 아니다.
  * yield()는 `RUNNABLE` 상태를 유지하기 때문에 양보할 사람이 없다면 본인 스레드가 계속 실행될 수 있다.

***

## 메모리 가시성
멀티스레드 환경에서 한 스레드가 변경한 값이 다른 스레드에서 언제 보이는지에 대한 문제를 메모리 가시성이라 한다.
이름 그대로 메모리에 변경한 값이 보이는가, 보이지 않는가의 문제이다.

해결방안은 아주 단순하다. 성능을 약간 포기하는 대신에, 값을 읽을 때, 값을 쓸 때 캐시 메모리가 아닌 메인 메모리에 직접 접근하면 된다.
자바에서는 `volatile`이라는 키워드로 이런 기능을 제공한다.

여러 스레드에서 같은 값을 읽고 써야 한다면 `volatile` 키워드를 사용하면 된다. 단 캐시 메모리를 사용할 때 보다 성능이 느려지는 단점이 있기 때문에
꼭 필요한 곳에만 사용하는 것이 좋다.

캐시 메모리를 메인 메모리에 반영하거나, 메인 메모리의 변경 내역을 캐시 메모리에 다시 불러오는 것은 언제 발생할까?
이 부분은 CPU 설계 방식과 실행 환경에 따라 다를 수 있다.
주로 컨텍스트 스위칭이 될 때, 캐시 메모리도 함께 갱신되는데, 이 부분도 환경에 따라 달라질 수 있다.
    * `Thread.sleep()`, 콘솔에 출력등을 할 때 스레드가 잠시 쉬는데, 이럴 때 컨텍스트 스위칭이 되면서 주로 갱신된다. 하지만 이것이 갱신을 보장하는 것은 아니다.\

> Java Memory Model

Java Memory Model(JMM)은 자바 프로그램이 어떻게 메모리에 접근하고 수정할 수 있는지를 규정하며, 특히 멀티스레드 프로그래밍에서 스레드 간의 상호작용을 정의한다.
JMM에 여러가지 내용이 있지만, 핵심은 여러 스레드들의 작업 순서를 보장하는 happens-before 관계에 대한 정의다.

> happens-before

happens-before 관계는 자바 메모리 모델에서 스레드 간의 작업 순서를 정의하는 개념이다. 만약 A작업이 B작업보다 happens-before 관계에 있다면,
A작업에서의 모든 메모리 변경 사항은 B작업에서 볼 수 있다. 즉 A작업에서 변경된 내용은 B작업이 시작 되기 전에 모두 메모리에 반영된다.

* happens-before 관계는 이름 그대로, 한 동작이 다른 동작보다 먼저 발생함을 보장한다.
* happens-before 관계는 스레드 간의 메모리 가시성을 보장하는 규칙이다.
* happens-before 관계가 성립하면, 한 스레드의 작업을 다른 스레드에서 볼 수 있게 된다.
* 즉, 한스레드에서 수행한 작업을 다른 스레드가 참조할 때 최신 상태가 보장되는 것이다.

***

## 동기화 synchronized

멀티스레드를 사용할 때 가장 중의해야 할 점은, 같은 자원에 여러 스레드가 동시에 접근할 때 발생하는 동시성 문제이다.
여러 스레드가 접근하는 자원을 `공유 자원`이라 한다. 대표적인 공유 자원은 인스턴스의 필드(멤버 변수)이다.
멀티스레드를 사용할 때는 이런 공유 자원에 대한 접근을 적절하게 동기화(synchronization)해서 동시성 문제가 발행하지 않게 방지하는 것이 중요하다.

### 임계 영역(critical section)
* 여러 스레드가 동시에 접근하면 데이터 불일치나 예상치 못한 동작이 발생할 수 있는 위험하고 또 중요한 코드 부분을 뜻한다.
* 여러 스레드가 동시에 접근해서는 안 되는 공유 자원을 접근하거나 수정하는 부분을 의미한다.
  * 예) 공유 변수나 공유 객체를 수정

이런 임계 영역은 한 번에 하나의 스레드만접근할 수 있도록 안전하게 보호해야 한다.
그럼 어떻게 한 번에 하나의 스레드만 접근할 수 있도록 임계 영역을 안전하게 보호할 수 있을까?
여러가지 방법이 있지만 자바는 `synchronized` 키워드를 통해 아주 갇난하게 임계 영역을 보호할 수 있다.

메서드에 `synchronized` 키워드를 넣으면 1개의 스레드만 해당 메서드를 실행할 수 있게 된다. 먼저 해당 메서드를 실행하는 스레드는 lock을 걸고
다른 스레드들은 lock이 풀릴 때까지 기다리게 된다. `synchronized`키워드를 쓰면 메모리 가시성 문제도 해결이 된다.

`synchronized`의 가장 큰 장점이다 단점은 한 번에 하나의 스레드만 실행할 수 있다는 점이다. 여러 스레드가 동시에 실행하지 못하기 때문에, 전체적으로 보면
성능이 떨어질 수 있다. 따라서 `synchoronized`를 통해 동시에 실행할 수 없는 코드 구간은 꼭 필요한 곳으로 한정해서 설정해야 한다.

> synchronized 코드 블럭

* 메서드 동기화: 메스드를 `synchronized`로 선언해서, 메서드에 접근하는 스레드가 하나뿐이도록 보장한다.
```java
public synchronized void synchronizedMethod() {
    ...
}
```

* 블록 동기화: 코드 블록을 `synchronized`로 감싸서, 동기화를 구현할 수 있다.
```java
public void synchronizedMethod() {
    synchronized (this) { ... }
}
```

이런 동기화를 사용하면 다음 문제들을 해결할 수 있다.
* 경합 조건(Race condition): 두 개 이상의 스레드가 경쟁적으로 동일한 자원을 수정할 때 발생하는 문제.
* 데이터 일관성: 여러 스레드가 동시에 읽고 쓰는 데이터의 일관성을 유지.

***

## 고급 동기화 - concurrent.Lock
`synchronized`는 자바 1.0부터 제공되는 매우 편리한 기능이지만, 다음과 같은 한계가 있다.
* 무한 대기: `BLOCKED` 상태의 스레드는 락이 풀릴 때 까지 무한 대기 한다.
  * 특정 시간까지만 대기하는 타임아웃X
  * 중간에 인터럽트 X
* 공정성: 락이 돌아왔을 때 `BLOCKED` 상태의 여러 스레드 중에 어떤 스레드가 락을 획득할 지 알 수 없다. 최악의 경우 ㅅ특정 스레드가 너무 오랜 기간 락을 획득하지 못할 수 도 있다.

이런 문제를 해결하기 위해 자바 1.5부터 `java.util.concurrent`라는 동시성 문제 해결을 위한 라이브러리 패키지가 추가 된다.

### LockSupport 기능
`LockSupport`는 스레드 `WAITING` 상태로 변경한다.
`WAITING` 상태는 누가 깨워주기 전까지는 계속 대기한다. 그리고 CPU 실행 스케줄링에 들어가지 않는다.

`LockSupport`의 대표적인 기능은 다음과 같다.
* `park()`: 스레드를 WAITING 상태로 변경한다.
  * 스레드를 대기 상태로 둔다.
* `parkNanos(nanos)`: 스레드를 나노초 동안만 TIMED_WAITING 상태로 변경한다.
  * 지정한 나노초가 지나면 TIMED_WAITING 상태에서 빠져나오고 RUNNABLE 상태로 변경된다.
* `unpark(thread)`: WAITING 상태의 대상 스레드를 RUNNABLE 상태로 변경한다. 

### interrupt 사용
WAITING 상태의 스래드에 인터럽트가 발생하면 RUNNABLE 상태로 변경하면서 깨어난다.

> LockSupport 정리

`LockSupport`를 사용하면 스레드를 `WAITING`, `TIMED_WAITING` 상태로 변경할 수 있고, 또 인터럽트를 받아서 스레드를 깨울 수도 있다.
이런 기능들을 잘 활용하면 `synchronized`의 단점인 무한 대기 문제를 해결 할 수 있다.

`synchronized`의 단점
* 무한 대기: `BLOCKED` 상태의 스레드는 락이 풀릴 때 까지 무한 대기한다.
  * 특정 시간까지만 대기하는 타임아웃 X -> `parkNanos()`를 사용하면 특정 시간까지만 대기할 수 있음
  * 중간에 인터럽트 x -> `park()`, `parkNanos()`는 인터럽트를 걸 수 있음

### ReentrantLock
자바는 1.0부터 존재한 `synchronized`와 `BLOCKED` 상태를 통한 임계 영역 관리의 한계를 극복하기 위해 자바 1.5부터 `Lock`인터페이스와 `ReentrantLock`구현체를 제공한다.

> Lock 인터페이스

`Lock` 인터페이스는 동시성 프로그래밍에서 쓰이는 안전한 임계 영역을 위한 락을 구현하는데 사용된다.
구체적인 구현체로는 `RentrantLock`이 있다. 다음과 같은 메서드를 제공한다.

`void lock()`
* 락을 획득한다. 만약 다른 스레드가 이미 락을 획득 했다면, 락이 풀릴 때까지 현재 스레드가 대기(`WAITING`)한다. 이메서드는 인터럽트에 응답하지 않는다.
* 예) 맛집에 한 번 줄을 서면 끝까지 기다린다. 친구가 다른 맛집을 찾았다고 중간에 연락해도 포기하지 않고 기다린다.

`void lockInterruptibly()`
* 락 획득을 시도하되, 다른 스레드가 인터럽트할 수 있도록 한다. 만약 다른 스레드가 이미 락을 획득했다면, 현재 스레드는 락을 획득할 때까지 대기한다. 대기 중에 인터럽트가 발생하면 `InterruptedException`이 발생하며 락 획득을 포기한다.
* 예) 맛집에 한 번 줄을 서서 기다린다. 다만 친구가 다른 맛집을 찾았다고 중간에 연락하면 포기한다.

`boolean tryLock()`
* 락 획득을 시도하고, 즉시 성공 여부를 반환한다. 만약 다른 스레드가 이미 락을 획득했다면 `false`를 반환하고 그렇지 않으면 락을 획득하고 `true`를 반환한다.
* 예) 맛집에 대기 줄이 없으면 바로 들어가고, 대기 줄이 있으면 즉시 포기한다.

`boolean tryLock(long time, TimeUnit unit)`
* 주어진 시간 동안 락 획득을 시도한다. 주어진 시간 안에 락을 획득하면 `true`를 반환한다. 주어진 시간이 지나도록 락을 획득하지 못한 경우 `false`를 반환한다. 이 메서드는 대기중 인터럽트가 발생하면 `InterruptException`이 발생하며 락 획득을 포기한다.
* 예) 맛집에 줄을 서지만 특정 시간 만큼만 기다린다. 특정 시간이 지나도록 줄을 서야 한다면 포기한다. 친구가 다른 맛집을 찾았다고 중간에 연락해도 포기한다.

`void unLock()`
* 락을 해제한다. 락을 해제하면 락 획득을 대기 중인 스레드중 하나가 락을 획들할 수 있게 된다.
* 락을 획득한 스레드가 호출해야하며, 그렇지 않으면 `IllegalMonitorStateException`이 발생할 수 있다.
* 예) 식당안에 있는 손님이 밥을 먹고 나간다. 식당에 자리가 하나 난다. 기다리는 손님에게 이런 사실을 알려주어야 한다. 기다리던 손님중 한명이 식당에 들어간다.

`condition newCondition()`
* `Condition` 객체를 생성하여 반환한다. `Condition` 객체는 락과 결합되어 사용되며, 스레드가 특정 조건을 기다리거나 신호를 받을 수 있도록 한다. 이는 `Object` 클래스의 `wait`, `notify`, `notifyAll` 메서드와 유사한 역할을 한다.

> 공정성

`Lock` 인터페이스가 제공하는 다양한 기능 덕분에 `synchronized`의 단점인 무한 대기 문제가 해결되었다.
그런데 공정성에 대한 문제가 남아있다. `Lock` 인터페이스의 대표적인 구현체로 `ReentrantLock`이 있는데, 이 클래스는 스레드가 공정하게 락을 얻을 수 있는 모드를 제공한다.

`ReentrantLock`은 공정성(fairness) 모드와 비공정(non-fair) 모드로 설정할 수 있으며, 이 두 모드는 락을 획득하는 방식에 차이가 있다.

> 비공정 모드

비공종 모드는 `ReentrantLock`의 기본 모드이다. 이 모드에서는 락을 먼저 요청한 스레드가 락을 먼저 획득한다는 보장이 없다.
락을 풀었을 때, 대기 중인 스레드중 아무나 락을 획득할 수 있다. 이는 락을 빨리 획득할 수 있지만, 특정 스레드가 장기간 락을 획들하지 못할 가능성도 있다.

비공정 모드 특징
* 성능 우선: 락을 획득하는 속도가 빠르다.
* 선점 가능: 새로운 스레드가 기존 대기 스레드보다 먼저 락을 획득할 수 있다.
* 기아 현상 가능성: 특정 스레드가 계속 락을 획득하지 못할 수 있다.

> 공정 모드

생성자에서 `true`를 전달하면 된다. 예) `new ReentrantLock(true)`
공정 모드는 락을 요청한 순서대로 스레드가 락을 획득할 수 있게 한다. 이는 먼저 대기한 스레드가 먼저 락을 획득하게 되어 스레드간의 공정성을 보장한다. 그러나 이로 인해 성능이 저하될 수 있따.

***

## 생산자 소비자 문제
생상자 소비자 문제는 멀티스레드 프로그래밍에서 자주 등장하는 동시성 문제 중 하나로, 여러 스레드가 동시에 데이터를 생산하고 소비하는 상황을 다룬다.

* 생산자(Producer): 데이터를 생성하는 역할을 한다. 예를 들어, 파일에서 데이터를 읽어오거나 네트워크에서 데이터를 받아오는 스레드가 생산자 역할을 할 수 있다.
* 소비자(Consumer): 생성된 데이터를 사용하는 역할을 한다. 예를 들어, 데이터를 처리하거나 저장하는 스레드가 소비자 역할을 할 수있다.
* 버퍼(Buffer): 생산자가 생성한 데이터를 일시적으로 저장하는 공간이다. 이 버퍼는 한정된 크기를 가지며, 생산자와 소비자가 이 버퍼를 통해 데이터를 주고 받는다.

### 문제 상황
* 생산자가 너무 빠를 때: 버퍼가 가득 차서 더 이상 데이터를 넣을 수 없을 때까지 생산자가 데이터를 생성한다. 버퍼가 가득 찬 경우 생산자는 버퍼에 빈 공간이 생길 때까지 기다려야 한다.
* 소비자가 너무 빠를 때: 버퍼가 비어서 더 이상 소비할 데이터가 없을 때까지 소비자가 데이터를 처리한다. 버퍼가 비어있을 때 소비자는 버퍼에 새로운 데잍처가 들어올 때까지 기다려야 한다.

### Object - wait, notify
`Object` 클래스는 이런 문제를 해결할 수 있는 `wait()`, `notify()` 메서드를 제공한다.
* `Object.wait()`
  * 현재 스레드가 가진 락을 반납하고 대기 (WAITING)한다.
  * 현재 스레드를 대기 (WAITING) 상태로 전환한다. 이 메서드는 현재 스레드가 `synchronized` 블록이나 메서드에서 락을 소유하고 있을 때만 호출할 수 있다. 
    호출한 스레드는 락을 반납하고, 다른 스레드가 해당 락을 획득할 수 있도록 한다. 이렇게 대기 상태로 전환된 스레드는 다른 스레드가 `notify()` 또는 `notifyAll()`을 호출할 때까지 대기 상태를 유지한다.
* `Object.notify()`
  * 대기 중인 스레드 중 하나를 깨운다.
  * 이 메서드는 `synchorinized` 블록이나 메서드에서 호출되어야 한다. 꺠운 스레드는 락을 다시 획득할 기회를 얻게 된다.
  * 만약 대기 중인 스레드가 여러 개라면, 그 중 하나만이 깨워지게 된다.
* `Object.notifyAll()`
  * 대기 중인 모든 스레드를 깨운다.
  * 이 메서드 역시 `synchronized` 블록이나 메서드에서 호출되어야 하며, 모든 대기 중인 스레드가 락을 획득할 수 있는 기회를 얻게 된다. 이 방법은 모든 스레드를 꺠워야할 필요가 있는 경우에 유용하다.

***

## Lock Condition

### Condition
`Condition`은 `ReentrantLock`을 사용하는 스레드가 대기하는 스레드 대기 공간이다.
`lock.newCondition()` 메서드를 호출하면 스레드 대기 공간이 만들어진다.

### condition.await()
`Object.wait()`와 유사한 기능이다. 지정한 condition에 현재 스레드 대기(WAITING) 상태로 보관한다.
이때 `ReentrantLock`에서 획득한 락을 반납하고 대기 상태로 `condition`에 보관된다.

### condition.signal()
`Object.notify()`와 유사한 기능이다. 지정한 condition에서 대기중인 스레드를하나 깨운다. 깨어난 스레드는 condition에서 빠져나온다.

자바의 모든 객체 인스턴스는 멀티스레드와 임계 영역을 다루기 위해 내부에 3가지 기본 요소를 가진다.
* 모니터락
* 락 대기 집합(모니터 락 대기집합)
* 스레드 대기 집합

락 대기 집합이 1차 대기소이고, 스레드 대기 집합이 2차 대기소라 생각하면된다.
2차 대기소에 들어간 스레드는 2차, 1차 대기소를 모두 빠져나와야 임계 영역을 수행할 수 있다.
이 3가지 요소는 서로 맞물려 돌아간다.

* `synchronized`를 사용한 임계 영역에 들어가려면 모니터 락이 필요하다.
* 모니터 락이 없으면 락 대기 집합에 들어가서 `BLOCKED` 상태로 락을 기다린다.
* 모니터 락을 반납하면 락 대기 집합에 있는 스레드 중 하나가 락을 획득하고 `BLOCKED -> RUNNABLE` 상태가 된다.
* `wait()`를 호출해서 스레드 대기 집합에 들어가기 위해서는 모니터 락이 필요하다.
* 스레드 대기 집합에 들어가려면 모니터 락을 반납한다.
* 스레드가 `notify()`를 호출하면 스레드 대기 집합에 있는 스레드 중 하나가 스레드 대기 집합을 빠져나온다. 그리고 모니터 락 획득을 시도한다.
  * 모니터 락을 획득하면 임계 영역을 수행한다.
  * 모니터 락을 획득하지 못하면 락 대기 집합에 들어가서 `BLOCKED` 상태로 락을 기다린다.

* 대기1: ReentrantLock 락 획득 대기
  * `ReentrantLock`의 대기 큐에서 관리
  * `WAITING` 상태로 락 획득 대기
  * `lock.lock()`을 호출 했을 때 락이 없으면 대기
  * 다른 스레드가 `lock.unlock()`을 호출 했을 때 대기가 풀리면서 락 획득 시도, 락을 획득하면 대기 큐를 빠져나감
* 대기2: await() 대기
  * `condition.await()`를 호출 했을 때, `condition` 객체의 스레드 대기 공간에서 관리
  * `WAITING` 상태로 대기
  * 다른 스레드가 `condition.signal()`을 호출 했을 때 `condition` 객체의 스레드 대기 공간에서 빠져나감

## BlockingQueue
자바는 생산자 소비자 문제를 해결하기 위해 `java.util.concurrent.BlockingQueue`라는 특별한 멀티스레드 자료구조를 제공한다.
* 데이터 추가 차단: 큐가 가득 차면 데이터 추가 작업(`put()`)을 시도하는 스레드는 공간이 생길 때까지 차단된다.
* 데이터 획득 차단: 큐가 비어 있으면 획득 작업(`take()`)을 시도하는 스레드는 큐에 데이터가 들어올 때까지 차단된다.

`BlockingQueue` 인터페이스의 대표적인 구현체
* `ArrayBlockingQueue`: 배열 기반으로 구현되어 있고, 버퍼의 크기가 고정되어 있다.
* `LinkedBlockingQueue`: 링크 기반으로 구현되어있고, 버퍼의 크기를 고정할 수도, 또는 무한하게 사용할 수도 있다.

큐가 가득 찼을 때 생각할 수 있는 선택지는 4가지가 있다.
* 예외를 던진다. 예외를 받아서 처리한다.
* 대기하지 않는다. 즉시 `false`를 반환한다.
* 대기한다.
* 특정 시간 만큼만 대기한다.

이런 문제를 해결하기 위해 `BlockingQueue`는 각 상황에 맞는 다양한 메서드를 제공한다.
### Throws Exception - 대기시 예외
* add(e): 지정된 요소를 큐에 추가하며, 큐가 가득 차면 `IllegalStateException` 예외를 던진다.
* remove(): 큐에서 요소를 제거하면 반환한다. 큐가 비어있으면 `NoSuchElementException` 예외를 던진다.
* element(): 큐의 머리 요소를 반환하지만, 요소를 큐에서 제거하지 않는다. 큐가 비어있으면 `NoSuchElementException` 예외를 던진다.

### Special Value - 대기시 즉시 반환
* offer(e): 지정된 요소를 큐에 추가하려고 시도하며, 큐가 가득차면 즉시 `false`를 반환한다.
* poll(): 큐에서 요소를 제거하고 반환한다. 큐가 비어있으면 `null`을 반환한다.
* peek(): 큐의 머리 요소를 반환하지만, 요소를 큐에서 제거하지 않는다. 큐가 비어있으면 `null`을 반환한다.

### Blocks - 대기
* put(e): 지정된 요소를 큐에 추가할 때까지 대기한다. 큐가 가득차면 공간이 생길 때까지 대기한다.
* take(): 큐에서 요소를 제거하고 반환한다. 큐가 비어 있으면 요소가 준비될 때까지 대기한다.
* Example(관찰): 해당사항 없음.

### Time Out - 시간 대기
* offer(e, time, unit): 지정된 요소를 큐에 추가하려고 시도하며, 지정된 시간 동안 큐가 비워지기를 기다리다가 시간이 초과되면 `false`를 반환한다.
* poll(time, unit): 큐에서 요소를 제거하고 반환한다. 큐에 요소가 없다면 지정된 시간 동안 요소가 준비되기를 기다리다가 시간이 초과되면 `null`을 반환한다.
* Examine(관찰): 해당 사항 없음.

***

## 동기화와 원자적 연산

### 원자적 연산
컴퓨터 과학에서 사용하는 원자적 연산(atomic operation)의 의미는 해당 연산이 더 이상 나눌 수 없는 단위로 수행된다는 것을 의미한다.
즉, 원자적 연산은 중단되지 않고, 다른 연산과 간섭없이 완전히 실행되거나 전혀 실행되지 않는 성질을 가지고 있다.
쉽게 이야기해서 멀티스레드 상황에 다른 스레드의 간섭 없이 안전하게 처리되는 연산이라는 뜻이다.

### CAS 연산
> 락 기반 방식의 문제점

락은 특정 자원을 보호하기 위해 스레드가 해당 자원에 접근하는 것을 제한한다.
락이 걸려 있는 동안 다른 스레드들은 해당 자원에 접근할 수 없고, 락이 해제 될 때까지 대기해야 한다.
또한 락 기반 접근에서는 락을 획득하고 해제하는 데 시간이 소요 된다.

이런 문제를 해결하기 위해 락을 걸지 않고 원자적인 연산을 수행할 수 있는 방법이 있는데,
이것을 CAS(Compare-And-Swap, Compare-And-Set)연산이라 한다.
이 방법은 락을 사용하지 않기 떄문에 락 프리(lock free)기법이라 한다.
CAS 연산은 락을 완전히 대체하는 것은 아니고, 작은 단위의 일부 영역에 적용할 수 있다.
기본은 락을 사용하고, 특별한 경우에 CAS를 적용할 수 있다고 생각하면 된다.

> CPU 하드웨어의 지원

CAS 연산은 원자적이지 않은 두 개의 연산을 CPU 하드웨어 차원에서 특별하게 하나의 원자적인 연산으로 묶어서 제공하는 기능이다.
이것은 소프트웨어가 제공하는 기능이 아니라 하드웨어가 제공하는 기능이다. 대부분 현대 CPU들은 CAS 연산을 위한 명령어를 제공한다.

## CAS와 락 방식의 비교
> 락 방식
* 비관적(pessimistic) 접근법
* 데이터에 접근하기 전에 항상 락을 획득
* 다른 스레드의 접근을 막음
* 다른 스레드가 방해할 것이다 라고 가정

> CAS 방식
* 낙관적(optimistic) 접근법
* 락을 사용하지 않고 데이터에 바로 접근
* 충돌이 발생해도 그때 재시도
* 대부분 경우 충돌이 없을 것이다 라고 가정

간단한 연산은 충돌이 잘 일어나지 않는다. 이 경우 락 보다는 CAS 연산이 효과적이다.

락을 획득 할 때 중요한 부분이 있다. 바로 두 연산을 하나로 만들어야 한다는 점이다.
1. 락 사용 여부 확인
2. 락의 값 변경

***