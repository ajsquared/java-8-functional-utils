package com.andrewjamesjohnson.exceptions;

import com.andrewjamesjohnson.streams.RichStream;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(JUnitQuickcheck.class)
public class PredicateWithCheckedExceptionTest {
    private static class DummyException extends Exception { }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Property
    public void checkedExceptionsWrappedFromPredicate(List<Integer> list) {
        if (!list.isEmpty()) {
            exception.expect(LambdaWrappedCheckedException.class);
            exception.expectCause(IsInstanceOf.instanceOf(DummyException.class));
        }

        RichStream.of(list).filter(i -> {
            throw new DummyException();
        }).toList();
    }
}