package wordfinderfuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FibonacciCalc {

    public static Long fib(Long n) {
        if (n < 2) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static Long fibParallel(Long n) throws ExecutionException, InterruptedException {
        if (n < 2) {
            return n;
        }
        FutureTask<Long> task1 = new FutureTask<>(() -> fib(n - 1));
        FutureTask<Long> task2 = new FutureTask<>(() -> fib(n - 2));
        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();
        return task1.get() + task2.get();
    }
}
