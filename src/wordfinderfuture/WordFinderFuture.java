/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordfinderfuture;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;

/**
 *
 * @author yurii
 */
public class WordFinderFuture {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /* Scanner in = new Scanner(System.in);
        System.out.print("Укажите базовый каталог (например, /usr/local/jdk/lib или C:\\Users): ");
        String directory = in.nextLine();
        System.out.print("Введите ключевое слово (например, volatile): ");
        String keyword = in.nextLine(); */
        // timeDebugger(() -> doJob(directory, keyword));
        // timeDebugger(() -> doJobParallel(directory, keyword));
        timeDebugger(() -> System.out.printf("Fib = %s", FibonacciCalc.fib(42L)));
        System.out.println();
        timeDebugger(() -> {
            try {
                System.out.printf("Fib = %s", FibonacciCalc.fibParallel(42L));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // Вызов одно-потоковой версии счетчика файлов, содержащих искомое слово keyword
        // SingleThreadMatchCounter counter = new SingleThreadMatchCounter(new File(directory), keyword);
        // System.out.println(counter.call().toString() + " файлов найдено.");
        // Вызов много-потоковой версии счетчика файлов, содержащих искомое слово keyword
        /* MatchCounter counter = new MatchCounter(new File(directory), keyword);
        FutureTask task = new FutureTask(counter);
        Thread t = new Thread(task);
        t.start();
        try {
            System.out.println(task.get().toString() + " файлов найдено.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        } */
    }

    private static void doJob (String directory, String keyword) {
        SingleThreadMatchCounter counter = new SingleThreadMatchCounter(new File(directory), keyword);
        System.out.println(counter.call().toString() + " файлов найдено.");
    }

    private static void doJobParallel (String directory, String keyword) {
        MatchCounter counter = new MatchCounter(new File(directory), keyword);
        FutureTask task = new FutureTask(counter);
        Thread t = new Thread(task);
        t.start();
        try {
            System.out.println(task.get().toString() + " файлов найдено.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
        }
    }

    private static void timeDebugger (Runnable job) {
        Long start = System.currentTimeMillis();
        job.run();
        System.out.printf("\nDelta time: %s", System.currentTimeMillis() - start);
    }
}
