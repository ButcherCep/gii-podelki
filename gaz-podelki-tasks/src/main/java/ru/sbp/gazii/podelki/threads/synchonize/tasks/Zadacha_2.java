package ru.sbp.gazii.podelki.threads.synchonize.tasks;

/**
 * Напишите пример кода, который приводит к deadlock.
 * Объясните, как его можно избежать. (Поменять местами LOCKs)
 */
public class Zadacha_2 {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK1) {
                System.out.println("Thread 1: Holding lock 1...");
                try {
                    Thread.sleep(100); //Иммитация работы
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (LOCK2) {
                    System.out.println("Thread 1: Acquired lock 2!");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (LOCK2) {
                System.out.println("Thread 2: Holding lock 2...");
                try {
                    Thread.sleep(100); // Имитация работы
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (LOCK1) {
                    System.out.println("Thread 2: Acquired lock 1!");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
