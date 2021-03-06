package com.andrewjamesjohnson.exceptions;

import java.util.function.Function;

/**
 * {@link Function} implementation that allows throwing a checked exception
 *
 * @param <T> The type of the input to the function
 * @param <R> The type of the result of the function
 */
@FunctionalInterface
public interface FunctionWithCheckedException<T, R> extends Function<T, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception any checked exception
     */
    R applyWithCheckedException(T t) throws Exception;

    @Override
    default R apply(T t) {
        try {
            return applyWithCheckedException(t);
        } catch (Exception e) {
            throw new LambdaWrappedCheckedException(e);
        }
    }
}
