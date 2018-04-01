# java-8-functional-utils [![Build Status](https://travis-ci.org/ajsquared/java-8-functional-utils.svg?branch=master)](https://travis-ci.org/ajsquared/java-8-functional-utils)

`java-8-functional-utils` is a library that offers various enhancements for funtional programming in Java 8.

This is mainly an experimental project and shouldn't be considered robust enough for critical applications.

## `RichStream`

`RichStream` is the core of the enhancements provided by this library. A `RichStream` can be created from a stream, a collection, an array, or an iterator:

```java
RichStream.of(stream);
RichStream.of(list.stream());
```

`RichStream` has all the same methods as Java's built-in `Stream` class, with the exception that when appropriate they will return a `RichStream` instead of a `Stream`.

However, `RichStream` has several enhancements as well.


### `filterNot`

`filterNot` is a quick way of negating the results of `filter`. Similar to `filter`, it takes a `Predicate`, but keeps only elements that *do not* match it.

### `zip` and `zipWithIndex`

`zip` combines two streams into a single stream pairing elements from the two original streams. The resulting stream has the same length as the shorter of the two original streams.

`zipWithIndex` pairs each element of the stream with its index, starting from 0.

Neither method is recommended for use with infinite streams.

### Collection Conversions

`RichStream` offers several shortcuts for terminally transforming a stream into a collection:

1. `toList`: Turns the stream into a `List`
2. `toSet`: Turns the stream into a `Set`
3. `toMapAsKey`: Turns the stream into a `Map` with the stream elements as keys, applying a `Function` to produce the values
4. `toMapAsValue`: Turns the stream into a `Map` with the stream elements as values, applying a `Function` to produce the keys
5. `toMap`: Turns the stream into a `Map`, applying one `Function` to produce the keys and another to produce the values

### Checked Exceptions in Lambdas

Java 8's lambda expressions do not support checked exceptions. As such they must be rewritten to avoid them or catch and rethrow an unchecked exception.

This project adds a `ConsumerWithCheckedException`, `FunctionWithCheckedException`, `PredicateWithCheckedException` and some primitive specializations thereof to support lambdas that do allow for checked exceptions. Methods on `RichStream` like `map` or `filter` then take either the built-in version without checked exceptions or these new implementations. 