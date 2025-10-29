package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        // Создаем семафор с 3 разрешениями (максимум 3 потока могут работать одновременно)
        Semaphore semaphore = new Semaphore(3);

        // Создаем и запускаем 10 потоков
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new Worker(i, semaphore));
            thread.start();
        }
    }

    // Класс Worker, который выполняет работу
    static class Worker implements Runnable {
        private final int id;
        private final Semaphore semaphore;

        public Worker(int id, Semaphore semaphore) {
            this.id = id;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                // Запрашиваем разрешение у семафора
                semaphore.acquire();
                System.out.println("Поток " + id + " начал выполнение.");

                // Имитация работы (спим 2 секунды)
                Thread.sleep(2000);

                System.out.println("Поток " + id + " завершил выполнение.");
            } catch (InterruptedException e) {
                System.out.println("Поток " + id + " был прерван.");
            } finally {
                // Освобождаем разрешение
                semaphore.release();
            }
        }
    }
}
