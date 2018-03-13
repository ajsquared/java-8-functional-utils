package com.andrewjamesjohnson.streams;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(JUnitQuickcheck.class)
public class CollectionConversionTests {
    @Property
    public void toListProducesTheExpectedList(List<String> list) {
        List<String> newList = RichStream.of(list).toList();
        Assert.assertEquals(list, newList);
    }

    @Property
    public void toSetProducesTheExpectedSet(List<String> list) {
        Set<String> expectedSet = new HashSet<>(list);
        Assert.assertEquals(expectedSet, RichStream.of(list).toSet());
    }

    @Property
    public void toMapAsKeyProducesTheExpectedMap(List<String> list) {
        Map<String, String> newMap = RichStream.of(list).toMapAsKey(String::toUpperCase);
        Assert.assertEquals(list.size(), newMap.size());
        list.forEach(element -> Assert.assertEquals(element.toUpperCase(), newMap.get(element)));
    }

    @Property
    public void toMapAsValueProducesTheExpectedMap(List<String> list) {
        Map<String, String> newMap = RichStream.of(list).toMapAsValue(String::toUpperCase);
        Assert.assertEquals(list.size(), newMap.size());
        list.forEach(element -> Assert.assertEquals(element, newMap.get(element.toUpperCase())));
    }

    @Property
    public void toMapProducesTheExpectedMap(List<String> list) {
        Map<String, String> newMap = RichStream.of(list).toMap(String::toUpperCase, String::toLowerCase);
        Assert.assertEquals(list.size(), newMap.size());
        list.forEach(element -> Assert.assertEquals(element.toLowerCase(), newMap.get(element.toUpperCase())));
    }
}