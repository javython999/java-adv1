package thread.cas.spinlock;

import static util.MyLogger.log;

public class SpinLockMain {

    public static void main(String[] args) {
        //SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();
        Runnable task = () -> {
            spinLock.lock();
            try {
                log("비즈니스 로직 실행");
            } finally {
                spinLock.unlock();
            }
        };

        Thread thread1 = new Thread(task, "thread-1");
        Thread thread2 = new Thread(task, "thread-2");

        thread1.start();
        thread2.start();
    }
}
