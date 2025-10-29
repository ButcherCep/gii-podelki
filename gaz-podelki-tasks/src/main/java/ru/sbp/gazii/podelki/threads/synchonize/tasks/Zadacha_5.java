package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Напишите программу,
 * которая использует AtomicInteger для потокобезопасного увеличения счетчика
 */
public class Zadacha_5 {
    private static final AtomicInteger counter = new AtomicInteger(0); // Потокобезопасный счетчик

    public static void main(String[] args) {
        // Создаем и запускаем несколько потоков
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new IncrementTask(), "Thread-" + (i + 1));
            threads[i].start();
        }

        // Ожидаем завершения всех потоков
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
            }
        }

        // Выводим итоговое значение счетчика
        System.out.println("Final counter value: " + counter.get());
    }

    // Задача для потоков: увеличить счетчик на 1
    static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                counter.incrementAndGet(); // Атомарное увеличение счетчика на 1
            }
            System.out.println(Thread.currentThread().getName() + " finished. Current counter: " + counter.get());
        }
    }
}
