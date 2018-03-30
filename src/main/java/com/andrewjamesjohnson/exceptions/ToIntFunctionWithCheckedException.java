package com.andrewjamesjohnson.exceptions;

import java.util.function.ToIntFunction;

/**
 * {@link ToIntFunction} implementation that allows throwing checked exceptions
 *
 * @param <T> the type of the input to the function
 */
@FunctionalInterface
public interface ToIntFunctionWithCheckedException<T> extends ToIntFunction<T> {
    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception any checked exception
     */
    int applyAsIntWithCheckedException(T value) throws Exception;

    @Override
    default int applyAsInt(T value) {
        try {
            return applyAsIntWithCheckedException(value);
        } catch (Exception e) {
            throw new LambdaWrappedCheckedException(e);
        }
    }
}
