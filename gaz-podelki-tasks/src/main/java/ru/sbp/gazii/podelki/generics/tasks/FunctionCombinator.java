package ru.sbp.gazii.podelki.generics.tasks;

import java.util.function.Function;

/**
 * Реализовать класс, который может комбинировать функции с разными типами
 */
public class FunctionCombinator {

    <A, B, C> Function<A, C> combine(Function<A, B> first, Function<B, C> second) {
        return first.andThen(second);
    }

    <A, B, C, D> Function<A, D> combine3(Function<A, B> f1, Function<B, C> f2, Function<C, D> f3) {
        return f1.andThen(f2).andThen(f3);
    }

    <A> Function<A, A> identity() {
        return Function.identity();
    }

    <A, B, C> Function<A, C> andThen(Function<A, B> first, Function<B, C> second) {
        return first.andThen(second);
    }
}