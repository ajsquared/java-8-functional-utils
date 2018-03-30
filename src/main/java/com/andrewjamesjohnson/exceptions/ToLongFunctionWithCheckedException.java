package com.andrewjamesjohnson.exceptions;

import java.util.function.ToLongFunction;

/**
 * {@link ToLongFunction} implementation that allows throwing checked exceptions
 *
 * @param <T> the type of the input to the function
 */
@FunctionalInterface
public interface ToLongFunctionWithCheckedException<T> extends ToLongFunction<T> {
    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception any checked exception
     */
    int applyAsLongWithCheckedException(T value) throws Exception;

    @Override
    default long applyAsLong(T value) {
        try {
            return applyAsLongWithCheckedException(value);
        } catch (Exception e) {
            throw new LambdaWrappedCheckedException(e);
        }
    }
}
