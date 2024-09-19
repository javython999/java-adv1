package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;

public class RejectMainV4 {

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new MyRejectedExecptionHandler());

        es.submit(new RunnableTask("task1"));
        es.submit(new RunnableTask("task2"));
        es.submit(new RunnableTask("task3"));
        es.submit(new RunnableTask("task4"));

        es.close();
    }

    static class MyRejectedExecptionHandler implements RejectedExecutionHandler {

        static AtomicInteger count = new AtomicInteger(0);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int i = count.incrementAndGet();
            log("[경고] 거절된 누적 작업 수: " + i);
        }
    }
}
