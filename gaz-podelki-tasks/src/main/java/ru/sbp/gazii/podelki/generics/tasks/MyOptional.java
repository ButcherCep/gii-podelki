package ru.sbp.gazii.podelki.generics.tasks;

import java.util.function.Function;
import java.util.function.Predicate;


class MyOptional<T> {
    private static final MyOptional<?> EMPTY_VALUE = new MyOptional<>(null);
    private final T value;

    private MyOptional(T value) {
        this.value = value;
    }

    public static <T> MyOptional<T> of(T value) {
        return new MyOptional<>(value);
    }

    public static <T> MyOptional<T> empty() {
        @SuppressWarnings("unchecked")
        MyOptional<T> result = (MyOptional<T>) EMPTY_VALUE;
        return result;
    }

    public MyOptional<T> filter(Predicate<T> p) {
        if (value != null && p.test(value)) {
            return this;
        } else {
            return empty();
        }
    }

    public <V> MyOptional<V> map(Function<T, V> function) {
        if (function == null || value == null) {
            return empty();
        }
        V result = function.apply(value);
        return result != null ? new MyOptional<>(result) : empty();
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }
}
