package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Реализуйте потокобезопасный кэш с использованием ConcurrentHashMap и Future.
 */
public class ThreadSafetyCash {
    public static void main(String[] args) throws InterruptedException {
        UserCache userCache = new UserCache();

        // Создаем несколько потоков для тестирования кэша
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            int userId = i % 3; // Используем 3 пользователя для демонстрации кэширования
            executorService.submit(() -> {
                try {
                    User user = userCache.getUser(userId);
                    System.out.println("Thread " + Thread.currentThread().getName() + " got user: " + user);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}

@Data
@AllArgsConstructor
class User {
    public int id;
    public String name;
    public int age;
}

class UserCache {
    // Кэш, где ключ - id пользователя, значение - Future<User>
    private final ConcurrentHashMap<Integer, Future<User>> cache = new ConcurrentHashMap<>();
    // Сервис для загрузки пользователей (имитация)
    private final UserService userService = new UserService();

    public User getUser(int id) throws ExecutionException, InterruptedException {
        while (true) {
            Future<User> future = cache.get(id);

            // Если Future уже существует в кэше
            if (future != null) {
                System.out.println("Пользователь "+id+"  уже в кэше ");
                return future.get(); // Возвращаем результат (ждем завершения)
            }

            // Если Future отсутствует, создаем новую задачу
            FutureTask<User> futureTask = new FutureTask<>(() -> userService.loadUser(id));
            System.out.println("Пользователь "+id+" отсутствует в кэше, создаем новую задачу ");
            // Пытаемся добавить FutureTask в кэш
            future = cache.putIfAbsent(id, futureTask);

            if (future == null) {
                System.out.println("Пользователь "+id+" добавлен в кэш " + id);
                // Если добавление успешно, запускаем задачу
                futureTask.run();
                future = futureTask;
            }

            // Возвращаем результат
            return future.get();
        }
    }
}


class UserService {
    public User loadUser(int id) {
        Random random = new Random();
        // Имитация задержки загрузки данных
        try {
            Thread.sleep(1000); // Задержка 1 секунда
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Возвращаем фиктивного пользователя
        return new User(id, "User" + id, random.nextInt(20));
    }
}


