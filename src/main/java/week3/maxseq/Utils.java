package week3.maxseq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static Integer[] generate(int size, int max){

        Set<Integer> set = new LinkedHashSet<>();
        Random random = new Random();
        for (int i = 0; i < max * 3 && set.size()<size; i++) {
            set.add(random.nextInt(max));
        }

        return set.stream().toArray(Integer[]::new);
    }

    public static Integer[] subsequence2000(int limit) throws IOException {
        String line = Files.lines(Path.of("src/main/java/week3/maxseq/lis.txt")).skip(1).findFirst().orElse("");
        return Arrays.stream(line.split("\s"))
                .map(Integer::parseInt)
                .limit(limit)
                .toArray(Integer[]::new);
    }

    public static String seqtoString(int[] seq){
       return Arrays.stream(seq).mapToObj(Objects::toString).collect(Collectors.joining("-"));
    }
    public static String seqtoString(Integer[] seq){
       return Arrays.stream(seq).map(Objects::toString).collect(Collectors.joining("-"));
    }
}
