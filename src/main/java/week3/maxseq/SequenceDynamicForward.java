package week3.maxseq;
//можно посчитать для каждой позиции i две величины:
//        d[i] - длину НВП, оканчивающейся элементом a[i],
//        c[i] - количество таких НВП.

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class SequenceDynamicForward {

    private Integer[] sequence;
    private int[] lengths;
    private long[] counts;

    public SequenceDynamicForward(Integer[] sequence) {
        this.sequence = sequence;
        lengths = new int[sequence.length];
        counts = new long[sequence.length];
    }

    public void run() {

        for (int i = 0; i < sequence.length; i++) {
            lengths[i] = 1;
            for (int j = 0; j < i; j++) {
                if(sequence[j]<sequence[i]) { //левый элемент меньше текущего, значит его цепочку можно продолжить
                    //если ее значение + 1 больше текущего
                    lengths[i] = Math.max(lengths[j]+1, lengths[i]);
                }
            }
        }

        for (int i = 0; i < sequence.length; i++) {
            for (int j = 0; j < i; j++) {
                if (sequence[j] < sequence[i] && lengths[j] + 1 == lengths[i]) {
                    counts[i] += counts[j];
                }
            }
            if (counts[i] == 0) {
                counts[i] = 1;
            }
        }

        System.out.println("SequenceDynamic2 - sequence: \t" + Arrays.stream(sequence).map(Objects::toString).collect(Collectors.joining("\t")));
        System.out.println("SequenceDynamic2 - lenghts: \t" + Arrays.stream(lengths).mapToObj(Objects::toString).collect(Collectors.joining("\t")));
        System.out.println("SequenceDynamic2 - counts: \t" + Arrays.stream(counts).mapToObj(Objects::toString).collect(Collectors.joining("\t")));
    }

    public int[] getLengths() {
        return lengths;
    }

    public long[] getCounts() {
        return counts;
    }

    public int getMaxLength(){
       return Arrays.stream(lengths).max().orElse(0);
    }

    public long getMaxCount(){
        int maxLength = getMaxLength();
        long maxCount = 0;
        for (int i = 0; i <lengths.length; i++) {
            if(lengths[i] == maxLength){
                maxCount += counts[i];
            }
        }
       return maxCount;
    }
}
