package ru.sbp.gazii.podelki.threads.synchonize.tasks;

/**
 * Напишите программу, в которой два потока поочередно выводят числа от 1 до 10.
 * Один поток должен выводить четные числа, а другой — нечетные.
 */
public class Zadacha_1 {
    private static final int MAX = 10;
    private static final Object LOCK = new Object();
    private static int num = 1;

    public static void main(String[] args) {
        Thread firstThread = new Thread(() -> printFirst(), "1st Thread");
        Thread secondThread = new Thread(() -> printSecond(), "2d Thread");
        firstThread.start();
        secondThread.start();
    }

    private static void printSecond() {
        synchronized (LOCK) {
            while (num <= MAX) {
                if (num % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + num);
                    num++;
                    LOCK.notify(); // Будим другой поток
                } else {
                    try {
                        LOCK.wait(); // Ждем, пока другой поток не выведет число
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Thread interrupted");
                    }
                }
            }
            LOCK.notify(); // Будим другой поток, чтобы завершить его работу
        }
    }

    private static void printFirst() {
        synchronized (LOCK) {
            while (num <= MAX) {
                if (num % 2 != 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + num);
                    num++;
                    LOCK.notify(); // Будим другой поток
                } else {
                    try {
                        LOCK.wait(); // Ждем, пока другой поток не выведет число
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Thread interrupted");
                    }
                }
            }
            LOCK.notify(); // Будим другой поток, чтобы завершить его работу
        }
    }

}
