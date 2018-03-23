package com.andrewjamesjohnson.functional;

import com.andrewjamesjohnson.streams.RichStream;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(JUnitQuickcheck.class)
public class PairFunctionTest {
    @Property
    public void andThenChainsFunctions(List<Integer> list) {
        PairFunction<Integer, Integer, Integer> firstPairFunction = (l, r) -> r;
        PairFunction<Integer, Integer, Integer> chainedPairFunction = firstPairFunction.andThen(i -> i * -1);

        List<Integer> actualList = RichStream.of(list).map(e -> Pair.of(e, e * -1)).map(chainedPairFunction).toList();
        Assert.assertEquals(list, actualList);
    }

    @Property
    public void bothApplyMethodsEquivalent(String left, String right) {
        PairFunction<String, String, String> pairFunction = (l, r) -> l + r;
        Assert.assertEquals(pairFunction.apply(Pair.of(left, right)), pairFunction.apply(left, right));
    }
}