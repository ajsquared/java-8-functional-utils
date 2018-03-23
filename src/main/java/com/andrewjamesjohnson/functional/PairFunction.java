package com.andrewjamesjohnson.functional;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link Function} on {@link Pair} that also behaves as a {@link BiFunction} on the elements of a pair
 *
 * @param <L> The type of the first element of the pair
 * @param <R> The type of the second element of the pair
 * @param <U> The type of the result of the function
 */
@FunctionalInterface
public interface PairFunction<L, R, U> extends Function<Pair<L, R>, U>, BiFunction<L, R, U> {
    @Override
    default U apply(Pair<L, R> pair) {
        Objects.requireNonNull(pair);
        return apply(pair.getLeft(), pair.getRight());
    }

    @Override
    default <V> PairFunction<L, R, V> andThen(Function<? super U, ? extends V> after) {
        return (l, r) -> after.apply(apply(l, r));
    }
}
