package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static util.MyLogger.log;

public class RejectMainV1 {

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.AbortPolicy());

        es.submit(new RunnableTask("task1"));

        try {
            es.submit(new RunnableTask("task2"));
        } catch (RejectedExecutionException e) {
            log("요청 초과");
            log(e);
        }

        es.close();
    }
}
