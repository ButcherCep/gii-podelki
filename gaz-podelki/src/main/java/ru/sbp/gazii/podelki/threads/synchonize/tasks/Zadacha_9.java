package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import java.util.concurrent.CompletableFuture;

/**
 * Напишите программу, которая использует CompletableFuture
 * для выполнения асинхронных задач и объединения их результатов.
 * */
public class Zadacha_9 {
    public static void main(String[] args) {
        // Задача 1: Асинхронное получение числа
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1 started by " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // Имитация долгой операции
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 10;
        });

        // Задача 2: Асинхронное получение другого числа
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 2 started by " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000); // Имитация долгой операции
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 20;
        });

        // Комбинирование результатов двух задач
        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            System.out.println("Combining results: " + result1 + " + " + result2);
            return result1 + result2; // Суммируем результаты
        });

        // Обработка результата
        combinedFuture.thenAccept(result -> {
            System.out.println("Final result: " + result);
        }).join(); // Ждем завершения всех задач

        // Обработка исключений
        combinedFuture.exceptionally(ex -> {
            System.err.println("Exception occurred: " + ex.getMessage());
            return null;
        });
    }
}
