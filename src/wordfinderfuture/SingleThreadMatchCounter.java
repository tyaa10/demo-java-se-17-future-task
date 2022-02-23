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

/**
 *
 * @author yurii
 */

/**
 * Задача подсчитывает файлы в каталоге и всех его подкаталогах, которые содержат
 * указанное ключевое слово.
 */
class SingleThreadMatchCounter {

    /**
     * Конструктор MatchCounter
     *
     * @param directory Каталог, с которого начинается поиск
     * @param keyword Искомое ключевое слово
     */
    public SingleThreadMatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    public Integer call() {
        count = 0;
        File[] files = (directory.listFiles() != null) ? directory.listFiles() : new File[count];
        for (File file : files) {
            if (file.isDirectory()) {
                SingleThreadMatchCounter counter = new SingleThreadMatchCounter(file, keyword);
                count += counter.call();
            } else {
                if (search(file)) {
                    count++;
                }
            }
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
