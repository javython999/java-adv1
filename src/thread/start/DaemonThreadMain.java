package thread.start;

public class DaemonThreadMain {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + ": main start");

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);   // 데몬 스레드 설정
        daemonThread.start();

        System.out.println(Thread.currentThread().getName() + ": main end");
    }

    static class DaemonThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": run()");

            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + ": run end");
        }
    }
}
