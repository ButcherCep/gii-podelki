package ru.sbp.gazii.podelki.threads.synchonize.tasks;


import org.graalvm.collections.Pair;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Берем из 3ех разынх источников данные в рандомный промежуток времени, агригируем и печатаем
 * (1) -> 33.3, 35.6, 32.1
 * (2) -> 70%, 80%, 85%
 * (3) -> 5 м/c, 6 м/c
 */
public class Zadacha_10 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TemperatureService temperatureService = new TemperatureService();
        HumidityService humidityService = new HumidityService();
        WinSpeed winSpeed = new WinSpeed();

        // Асинхронно получаем данные от сервисов
        for (int i = 0; i < 10; i++) {
            CompletableFuture<Pair<Integer, Double>> temperatureFuture = temperatureService.getTemperatureAsync();
            CompletableFuture<Pair<Integer, String>> humidityFuture = humidityService.getHumidityAsync();
            CompletableFuture<Pair<Integer, String>> speedFuture = winSpeed.getSpeedAsync();

            // Объединяем результаты
            CompletableFuture<StringBuilder> future1 = temperatureFuture
                    .thenCombine(humidityFuture, (res1, res2) -> {
                        StringBuilder builder = new StringBuilder("Результат итерации: ");
                        builder.append(" Темп: ");
                        builder.append(res1.getRight());
                        builder.append(" Влажность: ");
                        builder.append(res2.getRight());
                        builder.append(" ");
                        return builder;
                    }).thenCombine(speedFuture, (res1, res2) -> {
                        StringBuilder builder = new StringBuilder();
                        builder.append(res1);
                        builder.append("Скорость: ");
                        builder.append(res2.getRight());
                        return builder;
                    });
            System.out.println("Итерация: "+i);
            future1.thenAccept(System.out::println).get();
        }
    }


    // Сервис температуры
    static class TemperatureService {
        public CompletableFuture<Pair<Integer, Double>> getTemperatureAsync() {
            Random random = new Random();
            return CompletableFuture.supplyAsync(() -> {
                try {
                    // Имитация задержки в 2 секунды
                    Thread.sleep(random.nextInt(6000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // Генерация случайных данных
                Pair<Integer, Double> temperatureData = Pair.create(1, random.nextDouble() * 30);
                System.out.println("Получение температуры");
                return temperatureData;
            });
        }
    }

    // Сервис влажности
    static class HumidityService {
        public CompletableFuture<Pair<Integer, String>> getHumidityAsync() {
            Random random = new Random();
            return CompletableFuture.supplyAsync(() -> {
                try {
                    // Имитация задержки в 5 секунд
                    Thread.sleep(random.nextInt(6000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // Генерация случайных данных
                Pair<Integer, String> humidityData = Pair.create(1, random.nextInt(100) + "%");
                System.out.println("Получение влажности");
                return humidityData;
            });
        }
    }

    static class WinSpeed {
        public CompletableFuture<Pair<Integer, String>> getSpeedAsync() {
            Random random = new Random();
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(random.nextInt(6000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Pair<Integer, String> speedData = Pair.create(1, random.nextInt(100) + " м/c");
                System.out.println("Получение скорости");
                return speedData;
            });
        }
    }
}