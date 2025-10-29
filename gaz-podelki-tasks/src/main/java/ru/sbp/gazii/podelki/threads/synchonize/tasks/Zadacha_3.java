package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Пример реализации паттерна Producer-Consumer с использованием BlockingQueue.
 */
public class Zadacha_3 {
    public static void main(String[] args) {
        // Создаем BlockingQueue с ограниченной емкостью
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

        // Создаем и запускаем Producer
        Thread producerThread = new Thread(new Producer(queue));
        producerThread.start();

        // Создаем и запускаем Consumer
        Thread consumerThread = new Thread(new Consumer(queue));
        consumerThread.start();
    }
}

// Класс Producer (Производитель)
class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                System.out.println("Producer produces: " + i);
                queue.put(i); // Добавляем элемент в очередь (блокируется, если очередь полна)
                Thread.sleep(100); // Имитация задержки производства
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer was interrupted");
        }
    }
}

// Класс Consumer (Потребитель)
class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer value = queue.take(); // Берем элемент из очереди (блокируется, если очередь пуста)
                System.out.println("Consumer consumes: " + value);
                Thread.sleep(200); // Имитация задержки потребления
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer was interrupted");
        }
    }
}
