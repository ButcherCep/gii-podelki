package ru.sbp.gazii.podelki.threads.synchonize.tasks;

/**
 * Напишите программу,
 * которая использует synchronized для потокобезопасного увеличения счетчика
 * */
public class Zadacha_6 {
    private static int counter = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new IncrementTask(), "Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
            }
        }

        System.out.println("Final counter value: " + counter);
    }

    static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
            System.out.println(Thread.currentThread().getName() + " finished. Current counter: " + counter);
        }
    }
}
