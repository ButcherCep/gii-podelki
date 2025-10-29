package ru.sbp.gazii.podelki.generics.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Создать типобезопасный Builder для создания объектов с обязательными и опциональными полями
 */
public class GenericBuilder<T> {

    private T instance;
    private List<Consumer<T>> setters = new ArrayList<>();

    public GenericBuilder(T instance) {
        this.instance = instance;
    }

    static <T> GenericBuilder<T> of(Supplier<T> supplier) {
        return new GenericBuilder<>(supplier.get());
    }

    <F> GenericBuilder<T> with(Setter<T, F> setter, F value) {
            setters.add(target -> setter.set(target, value));
            return this;
    }

    T build() {
        setters.forEach(t -> t.accept(instance));
        return instance;
    }

    @FunctionalInterface
    public interface Setter<T,F> {
        void set(T target, F value);
    }
}
