package ru.sbp.gazii.podelki.threads.synchonize.tasks;

/**
 * Напишите программу, в которой один поток ждет,
 * пока другой поток не выполнит определенное условие, используя методы wait и notify
 */
public class Zadacha_4 {
    private static final Object lock = new Object();
    private static boolean condition = false;

    public static void main(String[] args) {
        Thread waiterThread = new Thread(new Waiter(), "WaiterThread");
        Thread notifierThread = new Thread(new Notifier(), "NotifierThread");

        waiterThread.start();
        notifierThread.start();
    }

    // Поток, который ждет выполнения условия
    static class Waiter implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + ": Waiting for condition...");
                while (!condition) {
                    try {
                        lock.wait(); // Ожидание уведомления
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().getName() + ": Thread interrupted");
                    }
                }
                System.out.println(Thread.currentThread().getName() + ": Condition met, continuing execution.");
            }
        }
    }

    // Поток, который изменяет условие и уведомляет ожидающий поток
    static class Notifier implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + ": Performing some work...");
                    Thread.sleep(2000); // Имитация работы
                    condition = true; // Изменение условия
                    lock.notify(); // Уведомление ожидающего потока
                    System.out.println(Thread.currentThread().getName() + ": Notified waiting thread.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().getName() + ": Thread interrupted");
                }
            }
        }
    }
}
