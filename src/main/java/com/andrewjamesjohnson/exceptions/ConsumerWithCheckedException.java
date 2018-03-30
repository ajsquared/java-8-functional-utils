package com.andrewjamesjohnson.exceptions;

import java.util.function.Consumer;

/**
 * {@link Consumer} implementation that allows throwing checked exceptions
 *
 * @param <T> The type of the input to the operation
 */
@FunctionalInterface
public interface ConsumerWithCheckedException<T> extends Consumer<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws Exception any checked exception
     */
    void acceptWithCheckedException(T t) throws Exception;

    @Override
    default void accept(T t) {
        try {
            acceptWithCheckedException(t);
        } catch (Exception e) {
            throw new LambdaWrappedCheckedException(e);
        }
    }
}
