package com.andrewjamesjohnson.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Wrapper class to support enhancements to {@link Stream}
 *
 * @param <T> The type of elements contained in the stream
 */
public final class RichStream<T> implements Stream<T> {
    /**
     * Creates a {@code RichStream} from a {@code Stream}
     *
     * @param stream The stream to wrap
     * @param <T> The type of elements in the stream
     * @return A {@code RichStream} wrapping the given stream
     */
    public static <T> RichStream<T> of(Stream<T> stream) {
        return new RichStream<>(stream);
    }

    /**
     * Creates a {@code RichStream} from a {@code Collection}
     *
     * @param collection The collection to wrap
     * @param <T> The type of elements in the collection
     * @return A {@code RichStream} wrapping the given collection
     */
    public static <T> RichStream<T> of(Collection<T> collection) {
        return new RichStream<>(collection.stream());
    }

    /**
     * Creates a {@code RichStream} from an array
     *
     * @param array The array to wrap
     * @param <T> The type of elements in the array
     * @return A {@code RichStream} wrapping the given array
     */
    public static <T> RichStream<T> of(T[] array) {
        return new RichStream<>(Stream.of(array));
    }

    private Stream<T> stream;

    private RichStream(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return stream.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return stream.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return stream.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return stream.distinct();
    }

    @Override
    public Stream<T> sorted() {
        return stream.sorted();
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return stream.sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return stream.peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return stream.limit(maxSize);
    }

    @Override
    public Stream<T> skip(long n) {
        return stream.skip(n);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return stream.sequential();
    }

    @Override
    public Stream<T> parallel() {
        return stream.parallel();
    }

    @Override
    public Stream<T> unordered() {
        return stream.unordered();
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return stream.onClose(closeHandler);
    }

    @Override
    public void close() {
        stream.close();
    }
}