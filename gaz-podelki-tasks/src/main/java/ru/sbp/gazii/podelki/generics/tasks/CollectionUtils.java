package ru.sbp.gazii.podelki.generics.tasks;


//Создай интерфейс и класс для выполнения различных операций
// над коллекциями, используя дженерики для обеспечения
// типобезопасности.

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {

    static <T> T findMax(Collection<? extends T> collection,
                         Comparator<? super T> comparator) {
        return collection.stream().max(comparator).orElse(null);
    }

    static <T> List<T> filter(Collection<? extends T> collection,
                              Predicate<? super T> predicate) {
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    static <T, R> List<R> transform(Collection<? extends T> collection,
                                    Function<? super T, ? extends R> function) {
        return collection.stream().map(function).collect(Collectors.toList());
    }

}
