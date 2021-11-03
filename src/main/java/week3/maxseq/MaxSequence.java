package week3.maxseq;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static week3.maxseq.Utils.seqtoString;
import static week3.maxseq.Utils.subsequence2000;

public class MaxSequence {

    @Test
    public void test0() {
        Integer[] sequence = {7, 1, 3, 2, 4};
        check(sequence, 3, 2);
    }

    @Test
    public void test1() {
        Integer[] sequence = {7, 1, 3, 2, 4, 6, 5, 8};
        check(sequence, 5, 4);
    }

    @Test
    public void test3() {
        Integer[] sequence = {2, 6, 5, 3, 9, 4, 11};
        check(sequence, 4, 4);
    }

    @Test
    public void test4() {
        Integer[] sequence = {19, 2, 6, 5, 16, 3, 15, 4, 17, 20};
        check(sequence, 5, 6);
    }

    @Test
    public void test5() {
        Integer[] sequence = {2, 17, 0, 11, 14, 15, 18, 4, 5, 16};
        check(sequence, 5, 4);
    }

    @Test
    public void test30() throws IOException {
        Integer[] sequence = subsequence2000(30);
        check(sequence, 8, 2);
    }

    @Test
    public void test40() throws IOException {
        Integer[] sequence = subsequence2000(40);
        check(sequence, 10, 6);
    }

    @Test
    public void test50() throws IOException {
        Integer[] sequence = subsequence2000(50);
        check(sequence, 12, 8);
    }

    @Test
    public void test2000() throws IOException {
        Integer[] sequence = subsequence2000(2000);
        check(sequence, 79, 2724495360L);
    }

    private void check(Integer[] sequence, int maxLengthExpected, long maxCountExpected) {

        boolean verbose = true;
        boolean withBrutForce = false;

        //решаем перебором
        if(withBrutForce) {
            List<int[]> subsequences = SequenceBrutForceAlg.subsequencesOfLen(sequence, maxLengthExpected);
            if (verbose) {
                System.out.println("sequenceCount = " + subsequences.size());
                subsequences.stream()
                        .map(subs -> Arrays.stream(subs).mapToObj(Objects::toString).collect(Collectors.joining("-")))
                        .forEach(System.out::println);
            }
            assertEquals(maxCountExpected, subsequences.size());
        }

        SequenceDynamicForward sdf = new SequenceDynamicForward(sequence);
        sdf.run();

        if (verbose) {
            System.out.println("SequenceDynamicForward maxLength: " + sdf.getMaxLength());
            System.out.println("SequenceDynamicForward vaxCount: " + sdf.getMaxCount());
        }
        assertEquals(maxLengthExpected, sdf.getMaxLength());
        assertEquals(maxCountExpected, sdf.getMaxCount());
    }





    @Test
    public void test6() {

        Integer[] sequence = {19, 2, 6, 5, 16, 3, 15, 4, 17, 20};

        int[][] vars = {{3, 2},
                {4, 2},
                {5, 3},
                {6, 3},
                {7, 3},
                {8, 3},
                {9, 4},
                {10, 5},
        };

        for (int[] x : vars) {
            System.out.println("----array len: " + x[0] + " \t max len: " + x[1]);
            Integer[] seq = Arrays.copyOf(sequence, x[0]);
            System.out.println("seq: " + seqtoString((seq)));

            SequenceBrutForceAlg.subsequencesOfLen(seq, x[1]).stream()
                    .map(Utils::seqtoString)
                    .forEach(System.out::println);
        }
    }
}
