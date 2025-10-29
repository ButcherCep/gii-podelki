package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Обработка исключений comletableFuture
 */
public class Zadacha_11 {
    public static void main(String[] args) throws Exception {
        A aclass = new A();
        B blcass = new B();
        CompletableFuture<String> res1 = aclass.methodA().exceptionally(ex -> {
            System.err.println("Exception 1: " + ex.getMessage());
            return "Default Value";
        });
        CompletableFuture<String> res2 = blcass.methodB();
        CompletableFuture<String> res = res1.thenCombine(res2, (a, b) -> a + b);
        res
                .thenAccept(System.out::println)
                .get();
    }

    static class A {
        CompletableFuture<String> methodA() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                    throw new RuntimeException("Exception 2");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    static class B {
        CompletableFuture<String> methodB() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "b";
            });
        }
    }
}
