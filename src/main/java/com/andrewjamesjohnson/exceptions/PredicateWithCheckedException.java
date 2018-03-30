package com.andrewjamesjohnson.exceptions;

import java.util.function.Predicate;

/**
 * {@link Predicate} implementation that allows throwing a checked exception
 *
 * @param <T> The type of the input to the predicate
 */
@FunctionalInterface
public interface PredicateWithCheckedException<T> extends Predicate<T> {
    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     * @throws Exception Any checked exception
     */
    boolean testWithCheckedException(T t) throws Exception;

    @Override
    default boolean test(T t) {
        try {
            return testWithCheckedException(t);
        } catch (Exception e) {
            throw new LambdaWrappedCheckedException(e);
        }
    }
}
