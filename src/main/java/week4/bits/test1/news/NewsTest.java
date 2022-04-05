package week4.bits.test1.news;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NewsTest {

    @Test()
    public void test1() {
        int[][] links = {
                {1, 2},
                {2, 3},
                {3, 4,}
        };
        News news = new News(4, links);
        List<String> solution = news.solve();

        solution.forEach(System.out::println);

        assertEquals(4, solution.size());
        assertTrue(solution.contains("1-3"));
        assertTrue(solution.contains("2-3"));
        assertTrue(solution.contains("1-4"));
        assertTrue(solution.contains("2-4"));
    }

    @Test
    public void test1in() throws IOException {
        int[][] links = parse("src/main/java/week4/bits/test1/news/news.in");
        News news = new News(10, links);
        List<String> solution = news.solve();

        solution.forEach(System.out::println);

        assertEquals(13, solution.size());
        assertTrue(solution.contains("3-4-5"));
        assertTrue(solution.contains("3-5-6")); //3 человека
    }

    @Test
    public void test2in() throws IOException {
        int[][] links = parse("src/main/java/week4/bits/test1/news/news2.in");
        News news = new News(20, links);
        List<String> solution = news.solve();

        solution.forEach(System.out::println);

        assertTrue(solution.contains("1-3-8-9-11-17")); //6 человек
    }

    private int[][] parse(String path) throws IOException {
        int[][] result = Files.lines(Paths.get(path))
                .skip(1)
                .map(line -> Arrays.stream(line.split("\\s"))
                        .mapToInt(Integer::valueOf)
                        .toArray())
                .toArray(int[][]::new);

        return result;
    }

//    @Test
//    public void genData() {
//        int[] ints = IntStream.range(0, 100).map(i -> ThreadLocalRandom.current().nextInt(150,  200)).toArray();
//
//        System.out.println("array: \n" + Arrays.stream(ints).mapToObj(Objects::toString).collect(Collectors.joining(", ")));
//        System.out.println("max: " + Arrays.stream(ints).max().orElse(0));
//        System.out.println("min: " + Arrays.stream(ints).min().orElse(0));
//        System.out.println("avg: " + Arrays.stream(ints).average().orElse(0));
//        System.out.println("sum: " + Arrays.stream(ints).sum());
//    }
//    array:
//            169, 190, 162, 180, 185, 172, 165, 170, 190, 165, 191, 151, 161, 179, 187, 186, 160, 177, 199, 167, 172, 167, 169, 170, 170, 150, 166, 186, 166, 192, 156, 155, 194, 151, 183, 172, 182, 169, 169, 172, 151, 175, 153, 197, 167, 191, 177, 169, 169, 167, 159, 169, 166, 188, 157, 151, 153, 190, 176, 199, 158, 175, 150, 151, 153, 157, 196, 160, 198, 179, 173, 192, 165, 174, 194, 153, 186, 176, 165, 176, 161, 154, 186, 172, 152, 179, 165, 193, 194, 180, 158, 168, 157, 172, 193, 152, 159, 152, 180, 152
//    max: 199
//    min: 150
//    avg: 171.51
//    sum: 17151
}
