package ru.sbp.gazii.podelki.threads.synchonize.tasks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * public class Counter {
 *     private int count = 0;
 *
 *     public synchronized void increment() {
 *         count++;
 *     }
 *
 *     public synchronized int getCount() {
 *         return count;
 *     }
 * } Как можно оптимизировать ?
 * */
public class Zadacha_7 {
}
/**
 * AtomicInteger предоставляет атомарные операции для работы с целыми числами,
 * что позволяет избежать блокировок.
 * */
class Counter_1 {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // Атомарное увеличение счетчика
    }

    public int getCount() {
        return count.get(); // Получение текущего значения
    }
}
/**
 * ReentrantLock предоставляет более гибкую блокировку, чем synchronized.
 * */
class Counter_2 {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // Захватываем блокировку
        try {
            count++;
        } finally {
            lock.unlock(); // Освобождаем блокировку
        }
    }

    public int getCount() {
        lock.lock(); // Захватываем блокировку
        try {
            return count;
        } finally {
            lock.unlock(); // Освобождаем блокировку
        }
    }
}
/**
 * Если чтение происходит чаще, чем запись, можно использовать ReadWriteLock,
 * чтобы разрешить множественное чтение, но блокировать запись.
 * */
class Counter_3 {
    private int count = 0;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void increment() {
        rwLock.writeLock().lock(); // Захватываем блокировку записи
        try {
            count++;
        } finally {
            rwLock.writeLock().unlock(); // Освобождаем блокировку записи
        }
    }

    public int getCount() {
        rwLock.readLock().lock(); // Захватываем блокировку чтения
        try {
            return count;
        } finally {
            rwLock.readLock().unlock(); // Освобождаем блокировку чтения
        }
    }
}
/**
 *LongAdder — это специализированный класс для высоконагруженных сценариев,
 * где множество потоков обновляют значение.
 * Он разделяет счетчик на несколько ячеек, чтобы уменьшить contention.
 * */
class Counter_4 {
    private final LongAdder count = new LongAdder();

    public void increment() {
        count.increment(); // Увеличение счетчика
    }

    public int getCount() {
        return count.intValue(); // Получение текущего значения
    }
}
