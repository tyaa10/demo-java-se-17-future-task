/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordfinderfuture;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import javax.imageio.stream.FileImageInputStream;

/**
 *
 * @author yurii
 */
/**
 * Задача подсчитывает файлы в каталоге и всех его подкаталогах, которые содержат
 * указанное ключевое слово.
 */
class MatchCounter implements Callable {

    /**
     * Конструктор MatchCounter
     *
     * @param directory Каталог, с которого начинается поиск
     * @param keyword Искомое ключевое слово
     */
    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    @Override
    public Integer call() {
        count = 0;
        try {
            File[] files = (directory.listFiles() != null) ? directory.listFiles() : new File[count];
            ArrayList<Future> results = new ArrayList<>();
            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword);
                    FutureTask task = new FutureTask(counter);
                    results.add(task);
                    Thread t = new Thread(task);
                    t.start();
                } else {
                    if (search(file)) {
                        count++;
                    }
                }
            }
            for (Future result : results) {
                try {
                    count += (int)result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
        }
        return count;
    }

    /**
     * Ищем в файле заданное ключевое слово.
     *
     * @param file Файл, в котором идет поиск return true, если слово найдено в
     * файле
     */
    public boolean search(File file) {
        try {
            Scanner in =
                new Scanner(file);
            
            boolean found = false;
            while (!found && in.hasNextLine()
            
            ) {
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    found = true;
                }
            }
            in.close();
            return found;
        } catch (IOException e) {
            return false;
        }
    }
    private File directory;
    private String keyword;
    private int count;
}
