package com.andrewjamesjohnson.streams;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(JUnitQuickcheck.class)
public class ZipTest {
    @Property
    public void zippedStreamLengthIsSmallerOfTwoStreams(List<String> first, List<String> second) {
        int expectedLength = Math.min(first.size(), second.size());
        List<Pair<String, String>> zippedList = RichStream.of(first).zip(RichStream.of(second)).toList();
        Assert.assertEquals(expectedLength, zippedList.size());
    }

    @Property
    public void zippedStreamContentsAreCorrectlyPaired(List<String> first, List<String> second) {
        List<Pair<String, String>> zippedList = RichStream.of(first).zip(RichStream.of(second)).toList();

        for (int i = 0; i < zippedList.size(); i++) {
            Pair<String, String> pair = zippedList.get(i);
            String firstElement = first.get(i);
            String secondElement = second.get(i);

            Assert.assertEquals(firstElement, pair.getLeft());
            Assert.assertEquals(secondElement, pair.getRight());
        }
    }
}