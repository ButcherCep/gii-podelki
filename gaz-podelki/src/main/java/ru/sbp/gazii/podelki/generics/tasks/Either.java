package ru.sbp.gazii.podelki.generics.tasks;

import java.util.function.Function;

/**
 * Реализовать Either тип, который может содержать либо значение типа L, либо R
 */

public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    static <L, R> Either<L, R> left(L value) {
        return new Either<>(value, null);
    }

    static <L, R> Either<L, R> right(R value) {
        return new Either<>(null, value);
    }

    boolean isLeft() {
        return left != null;
    }

    boolean isRight() {
        return right != null;
    }

    <T> Either<T, R> mapLeft(Function<L, T> mapper) {
        if (isLeft()) {
            return Either.left(mapper.apply(left));
        } else {
            return Either.right(right); // Сохраняем правую часть
        }
    }

    <T> Either<L, T> mapRight(Function<R, T> mapper) {
        if (isRight()) {
            return Either.right(mapper.apply(right));
        } else {
            return Either.left(left); // Сохраняем левую часть
        }
    }

    <T> T fold(Function<L, T> leftMapper, Function<R, T> rightMapper) {
        return isLeft() ? leftMapper.apply(left) : rightMapper.apply(right);
    }

}