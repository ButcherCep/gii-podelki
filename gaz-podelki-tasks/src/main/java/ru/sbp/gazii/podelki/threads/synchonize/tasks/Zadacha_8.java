package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Напишите программу,
 * которая использует ConcurrentHashMap для потокобезопасного доступа к данным
 */
public class Zadacha_8 {
    public static void main(String[] args) {
        // Создаем ConcurrentHashMap
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Создаем и запускаем потоки для записи данных
        Thread writerThread1 = new Thread(new Writer(map, "Writer-1"));
        Thread writerThread2 = new Thread(new Writer(map, "Writer-2"));

        // Создаем и запускаем потоки для чтения данных
        Thread readerThread1 = new Thread(new Reader(map, "Reader-1"));
        Thread readerThread2 = new Thread(new Reader(map, "Reader-2"));

        writerThread1.start();
        writerThread2.start();
        readerThread1.start();
        readerThread2.start();

        // Ожидаем завершения всех потоков
        try {
            writerThread1.join();
            writerThread2.join();
            readerThread1.join();
            readerThread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        // Выводим итоговое состояние карты
        System.out.println("Final map: " + map);
    }

    // Поток для записи данных в ConcurrentHashMap
    static class Writer implements Runnable {
        private final ConcurrentHashMap<String, Integer> map;
        private final String threadName;

        public Writer(ConcurrentHashMap<String, Integer> map, String threadName) {
            this.map = map;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                String key = threadName + "-Key-" + i;
                int value = i;
                map.put(key, value); // Потокобезопасная операция
                System.out.println(threadName + " added: " + key + " -> " + value);
                try {
                    Thread.sleep(100); // Имитация задержки
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(threadName + " interrupted");
                }
            }
        }
    }

    // Поток для чтения данных из ConcurrentHashMap
    static class Reader implements Runnable {
        private final ConcurrentHashMap<String, Integer> map;
        private final String threadName;

        public Reader(ConcurrentHashMap<String, Integer> map, String threadName) {
            this.map = map;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                String key = "Writer-1-Key-" + i; // Читаем данные, добавленные Writer-1
                Integer value = map.get(key); // Потокобезопасная операция
                System.out.println(threadName + " read: " + key + " -> " + value);
                try {
                    Thread.sleep(100); // Имитация задержки
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(threadName + " interrupted");
                }
            }
        }
    }
}
