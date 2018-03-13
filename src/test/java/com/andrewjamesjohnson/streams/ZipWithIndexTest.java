package com.andrewjamesjohnson.streams;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(JUnitQuickcheck.class)
public class ZipWithIndexTest {
    @Property
    public void zipWithIndexProducesCorrectIndices(List<String> list) {
        List<Pair<String, Integer>> zippedList = RichStream.of(list).zipWithIndex().collect(Collectors.toList());
        Assert.assertEquals(list.size(), zippedList.size());
        for (int i = 0; i < list.size(); i++) {
            String element = list.get(i);
            Pair<String, Integer> elementIndexPair = zippedList.get(i);
            Assert.assertEquals(element, elementIndexPair.getKey());
            Assert.assertEquals(Integer.valueOf(i), elementIndexPair.getRight());
        }
    }
}