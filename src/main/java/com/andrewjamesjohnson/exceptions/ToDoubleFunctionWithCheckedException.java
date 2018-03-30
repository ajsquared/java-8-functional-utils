package com.andrewjamesjohnson.exceptions;

import java.util.function.ToDoubleFunction;

/**
 * {@link ToDoubleFunction} implementation that allows throwing checked exceptions
 *
 * @param <T> the type of the input to the function
 */
@FunctionalInterface
public interface ToDoubleFunctionWithCheckedException<T> extends ToDoubleFunction<T> {
    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     * @throws Exception any checked exception
     */
    double applyAsDoubleWithCheckedException(T value) throws Exception;

    @Override
    default double applyAsDouble(T value) {
        try {
            return applyAsDoubleWithCheckedException(value);
        } catch (Exception e) {
            throw new LambdaWrappedCheckedException(e);
        }
    }
}
