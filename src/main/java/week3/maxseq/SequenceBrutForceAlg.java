package week3.maxseq;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SequenceBrutForceAlg {


    @Test
    public void test1(){
        Integer[] sequence = {7, 1, 3, 2, 4, 6, 5, 8};
        List<int[]> subsequences = subsequencesOfLen(sequence, 5);

        subsequences.stream()
                .map(subs -> Arrays.stream(subs).mapToObj(Objects::toString).collect(Collectors.joining("-")))
                .forEach(System.out::println);
    }

    public static List<int[]> subsequencesOfLen(Integer[] sequence, int len) {

        int[] result = new int[len];
        List<int[]> subsequences = new ArrayList<>();
        recLoop(result, 0, sequence, subsequences);

        return subsequences;
    }

    private static void recLoop(int[] result, int currentLevel, Integer[] sequence, List<int[]> subsequences){
        for (int i = 0; i < sequence.length; i++) {
            result[currentLevel] = i;
            if(currentLevel+1 < result.length){
                recLoop(result, currentLevel+1, sequence, subsequences);
            }else if(currentLevel==result.length-1){

                boolean isOk = isGrowing(result);
                if(isOk){
                    int[] subsequence = new int[result.length];
                    for (int j = 0; j <result.length; j++) {
                        subsequence[j] = sequence[result[j]];
                    }

                    if(isGrowing(subsequence)){
//                        String subsequenceStr = Arrays.stream(subsequence).mapToObj(Objects::toString).collect(Collectors.joining("-"));
//                        String indiesStr = Arrays.stream(result).mapToObj(Objects::toString).collect(Collectors.joining("-"));
//                        System.out.println(indiesStr + "\t" + subsequenceStr);
                        subsequences.add(subsequence);
                    }
                }
            }
        }
    }

    private static boolean isGrowing(int[] result) {
        int currentMax = -1;
        for (int i : result) {
            if (i > currentMax) {
                currentMax = i;
            } else {
                return false;
            }
        }
        return true;
    }

}
