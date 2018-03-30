package com.andrewjamesjohnson.streams;

import com.andrewjamesjohnson.exceptions.FunctionWithCheckedException;
import com.andrewjamesjohnson.exceptions.PredicateWithCheckedException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
        return of(collection.stream());
    }

    /**
     * Creates a {@code RichStream} from an array
     *
     * @param array The array to wrap
     * @param <T> The type of elements in the array
     * @return A {@code RichStream} wrapping the given array
     */
    public static <T> RichStream<T> of(T[] array) {
        return of(Arrays.stream(array));
    }

    /**
     * Creates a {@code RichStream} from an {@code Iterable}
     * 
     * @param iterable The iterable to wrap
     * @param <T> The type of elements in the iterable
     * @return A {@code RichStream} wrapping the given array
     */
    public static <T> RichStream<T> of(Iterable<T> iterable) {
        return of(StreamSupport.stream(iterable.spliterator(), false));
    }

    /**
     * Creates a {@code RichStream} from an {@code Iterator}
     *
     * @param iterator The iterator to wrap
     * @param <T> The type of elements in the iterator
     * @return A {@code RichStream} wrapping the given array
     */
    public static <T> RichStream<T> of(Iterator<T> iterator) {
        return of(() -> iterator);
    }

    private Stream<T> stream;

    private RichStream(Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * Returns a stream consisting of the elements of this stream that do not match
     * the given predicate.
     *
     * @param predicate A {@code Predicate} returning true if an element should not be included
     * @return the new stream
     */
    public RichStream<T> filterNot(Predicate<? super T> predicate) {
        return filter(predicate.negate());
    }


    /**
     * Returns a stream consisting of the elements of this stream that do not match
     * the given predicate.
     *
     * @param predicate A {@link PredicateWithCheckedException} returning true if an element should not be included
     * @return the new stream
     */
    public RichStream<T> filterNot(PredicateWithCheckedException<? super T> predicate) {
        return filter(predicate.negate());
    }

    /**
     * Returns a stream consisting of {@link Pair}s of the original element and the index of that element
     * Not recommended for use on infinite streams!
     *
     * @return The new stream
     */
    public RichStream<Pair<T, Integer>> zipWithIndex() {
        AtomicInteger index = new AtomicInteger(0);
        return new RichStream<>(stream.map(element -> Pair.of(element, index.getAndIncrement())));
    }

    /**
     * Returns a stream consisting of {@link Pair}s of elements from this stream and another
     * 
     * The resulting stream has the same length as the shorter of the two streams
     * Not recommended for use on infinite streams!
     *
     * @param other The other stream
     * @param <R> The type of elements in the other stream
     * @return The new stream
     */
    public <R> RichStream<Pair<T, R>> zip(Stream<R> other) {
        Iterator<T> iterator = iterator();
        Iterator<R> otherIterator = other.iterator();
        Iterator<Pair<T, R>> pairIterator = new Iterator<Pair<T, R>>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext() && otherIterator.hasNext();
            }

            @Override
            public Pair<T, R> next() {
                return Pair.of(iterator.next(), otherIterator.next());
            }
        };
        return RichStream.of(pairIterator);
    }

    /**
     * Converts this stream to a list
     *
     * @return The new list
     */
    public List<T> toList() {
        return collect(Collectors.toList());
    }

    /**
     * Converts this stream to a set
     *
     * @return The new set
     */
    public Set<T> toSet() {
        return collect(Collectors.toSet());
    }

    /**
     * Converts this stream to a map, with the elements as keys
     *
     * @param valueFunction A {@code Function} on elements to produce the map values
     * @param <V> The type of the map values
     * @return The new map
     */
    public <V> Map<T, V> toMapAsKey(Function<T, V> valueFunction) {
        return collect(Collectors.toMap(Function.identity(), valueFunction));
    }

    /**
     * Converts this stream to a map, with the elements as keys
     *
     * @param valueFunction A {@link FunctionWithCheckedException} on elements to produce the map values
     * @param <V> The type of the map values
     * @return The new map
     */
    public <V> Map<T, V> toMapAsKey(FunctionWithCheckedException<T, V> valueFunction) {
        return collect(Collectors.toMap(Function.identity(), valueFunction));
    }

    /**
     * Converts this stream to a map, with the elements as values
     *
     * @param keyFunction A {@code Function} on elements to produce the map keys
     * @param <K> The type of the map keys
     * @return The new map
     */
    public <K> Map<K, T> toMapAsValue(Function<T, K> keyFunction) {
        return collect(Collectors.toMap(keyFunction, Function.identity()));
    }

    /**
     * Converts this stream to a map, with the elements as values
     *
     * @param keyFunction A {@link FunctionWithCheckedException} on elements to produce the map keys
     * @param <K> The type of the map keys
     * @return The new map
     */
    public <K> Map<K, T> toMapAsValue(FunctionWithCheckedException<T, K> keyFunction) {
        return collect(Collectors.toMap(keyFunction, Function.identity()));
    }

    /**
     * Converts this stream to a map
     *
     * @param keyFunction A {@code Function} on elements to produce the map keys
     * @param valueFunction A {@code Function} on elements to produce the map values
     * @param <K> The type of the map keys
     * @param <V> The type of the map values
     * @return The new map
     */
    public <K, V> Map<K, V> toMap(Function<T, K> keyFunction, Function<T, V> valueFunction) {
        return collect(Collectors.toMap(keyFunction, valueFunction));
    }

    /**
     * Converts this stream to a map
     *
     * @param keyFunction A {@link FunctionWithCheckedException} on elements to produce the map keys
     * @param valueFunction A {@code Function} on elements to produce the map values
     * @param <K> The type of the map keys
     * @param <V> The type of the map values
     * @return The new map
     */
    public <K, V> Map<K, V> toMap(FunctionWithCheckedException<T, K> keyFunction, Function<T, V> valueFunction) {
        return collect(Collectors.toMap(keyFunction, valueFunction));
    }

    /**
     * Converts this stream to a map
     *
     * @param keyFunction A {@code Function} on elements to produce the map keys
     * @param valueFunction A {@link FunctionWithCheckedException} on elements to produce the map values
     * @param <K> The type of the map keys
     * @param <V> The type of the map values
     * @return The new map
     */
    public <K, V> Map<K, V> toMap(Function<T, K> keyFunction, FunctionWithCheckedException<T, V> valueFunction) {
        return collect(Collectors.toMap(keyFunction, valueFunction));
    }

    /**
     * Converts this stream to a map
     *
     * @param keyFunction A {@link FunctionWithCheckedException} on elements to produce the map keys
     * @param valueFunction A {@link FunctionWithCheckedException} on elements to produce the map values
     * @param <K> The type of the map keys
     * @param <V> The type of the map values
     * @return The new map
     */
    public <K, V> Map<K, V> toMap(FunctionWithCheckedException<T, K> keyFunction, FunctionWithCheckedException<T, V> valueFunction) {
        return collect(Collectors.toMap(keyFunction, valueFunction));
    }
    
    // Wrapped methods below

    @Override
    public RichStream<T> filter(Predicate<? super T> predicate) {
        return new RichStream<>(stream.filter(predicate));
    }

    public RichStream<T> filter(PredicateWithCheckedException<? super T> predicate) {
        return new RichStream<>(stream.filter(predicate));
    }

    @Override
    public <R> RichStream<R> map(Function<? super T, ? extends R> mapper) {
        return new RichStream<>(stream.map(mapper));
    }

    public <R> RichStream<R> map(FunctionWithCheckedException<? super T, ? extends R> mapper) {
        return new RichStream<>(stream.map(mapper));
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
        return new RichStream<>(stream.flatMap(mapper));
    }

    public <R> Stream<R> flatMap(FunctionWithCheckedException<? super T, ? extends Stream<? extends R>> mapper) {
        return new RichStream<>(stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    public IntStream flatMapToInt(FunctionWithCheckedException<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    public LongStream flatMapToLong(FunctionWithCheckedException<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    public DoubleStream flatMapToDouble(FunctionWithCheckedException<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public RichStream<T> distinct() {
        return new RichStream<>(stream.distinct());
    }

    @Override
    public RichStream<T> sorted() {
        return new RichStream<>(stream.sorted());
    }

    @Override
    public RichStream<T> sorted(Comparator<? super T> comparator) {
        return new RichStream<>(stream.sorted(comparator));
    }

    @Override
    public RichStream<T> peek(Consumer<? super T> action) {
        return new RichStream<>(stream.peek(action));
    }

    @Override
    public RichStream<T> limit(long maxSize) {
        return new RichStream<>(stream.limit(maxSize));
    }

    @Override
    public RichStream<T> skip(long n) {
        return new RichStream<>(stream.skip(n));
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

    public boolean anyMatch(PredicateWithCheckedException<? super T> predicate) {
        return anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    public boolean allMatch(PredicateWithCheckedException<? super T> predicate) {
        return allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    public boolean noneMatch(PredicateWithCheckedException<? super T> predicate) {
        return noneMatch(predicate);
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
    public RichStream<T> sequential() {
        return new RichStream<>(stream.sequential());
    }

    @Override
    public RichStream<T> parallel() {
        return new RichStream<>(stream.parallel());
    }

    @Override
    public RichStream<T> unordered() {
        return new RichStream<>(stream.unordered());
    }

    @Override
    public RichStream<T> onClose(Runnable closeHandler) {
        return new RichStream<>(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }
}
