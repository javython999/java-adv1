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