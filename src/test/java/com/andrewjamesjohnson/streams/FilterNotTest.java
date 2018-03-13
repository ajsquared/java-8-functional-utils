package com.andrewjamesjohnson.streams;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RunWith(JUnitQuickcheck.class)
public class FilterNotTest {
    @Property
    public void filterNotReturnsOppositeElementsToFilter(Collection<Integer> collection) {
        Predicate<Integer> testPredicate = i -> i > 0;
        List<Integer> filterResult = RichStream.of(collection).filter(testPredicate).collect(Collectors.toList());
        List<Integer> filterNotResult = RichStream.of(collection).filterNot(testPredicate).collect(Collectors.toList());
        Assert.assertEquals(collection.size(), filterResult.size() + filterNotResult.size());
        filterResult.stream().map(filterNotResult::contains).forEach(Assert::assertFalse);
        filterNotResult.stream().map(filterResult::contains).forEach(Assert::assertFalse);
    }
}