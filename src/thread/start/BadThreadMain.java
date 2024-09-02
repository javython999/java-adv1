package thread.start;

public class BadThreadMain {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + ": main() start");

        HelloThread thread = new HelloThread();

        System.out.println(Thread.currentThread().getName() + ": start() 호출 전");

        thread.run(); // 직접 실행

        System.out.println(Thread.currentThread().getName() + ": start() 호출 후");

        System.out.println(Thread.currentThread().getName() + ": main() end");
    }
}
