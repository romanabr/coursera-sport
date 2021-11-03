package week3.maxseq;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static week3.maxseq.SequenceDynamic1.dynamic;

public class SequenceDynamicBackward {


    public static long countSequences(Integer[] sequence, boolean verbose) {

        int[] cache = dynamic(sequence);
        int maxSequenceLength = Arrays.stream(cache).max().orElse(0);
        int[] possibilities = countPossibilitiesCache(cache, maxSequenceLength);
        long[] transitions = processPossibilities(possibilities);
        long sequenceCountDynamic = getSequenceCountDynamic(maxSequenceLength, possibilities, transitions);

        if(verbose){
            System.out.println("maxSequenceLength = " + maxSequenceLength);
            System.out.println("sequence: " + Arrays.stream(sequence).map(Objects::toString).collect(Collectors.joining("\t")));
            System.out.println("cache: " + Arrays.stream(cache).mapToObj(Objects::toString).collect(Collectors.joining("\t")));
            System.out.println("possibilities: " + Arrays.stream(possibilities).mapToObj(Objects::toString).collect(Collectors.joining("\t")));
            System.out.println("transitions: " + Arrays.stream(transitions).mapToObj(Objects::toString).collect(Collectors.joining("\t")));
            System.out.println("sequenceCountDynamic = " + sequenceCountDynamic);
        }

        return sequenceCountDynamic;
    }


    public static long[] processPossibilities(int[] possibilities) {
        long[] trans = new long[possibilities.length];

        long max = 0;
        for (int i = possibilities.length - 1; i >= 0; i--) {
            if (possibilities[i] == 1) {
                trans[i] = 1;
            }else{
                long count = 0;
                for (int j = i; j < possibilities.length; j++) {
                    if(possibilities[j]==possibilities[i]-1){
                        count += trans[j];
                    }
                }
                trans[i] = count;
                max = Math.max(count, max);
            }
        }

        return trans;
    }

    public static int[] countPossibilitiesCache(int[] cache, int max) {
        int[] possibilities = new int[cache.length];
        boolean started = false;

        int localMin = max;
        for (int i = 0; i < cache.length; i++) {
            if (!started) {
                if (cache[i] < max) {
                    possibilities[i] = 0;
                } else {
                    possibilities[i] = cache[i];
                    started = true;
                }
                continue;
            }

            if (cache[i] == localMin - 1) {
                localMin--;
            }

            if(cache[i]>=localMin){
                possibilities[i]=cache[i];
            }else{
                possibilities[i] = 0;
            }
        }
        return possibilities;
    }


    public static long getSequenceCountDynamic(int max, int[] possibilities, long[] transitions) {
        long sequenceCountDynamic = 0;
        for (int i = 0; i < possibilities.length; i++) {
            if(possibilities[i]== max){
                sequenceCountDynamic += transitions[i];
            }
        }
        return sequenceCountDynamic;
    }
}
