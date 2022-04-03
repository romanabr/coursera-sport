package week4.bits.test1.friends;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FriendsTest {

    @Test()
    public void test1() {
        int[][] links = {
                {1, 2},
                {2, 3},
                {3, 1,},
                {2, 4}
        };
        Friends f = new Friends(4, links);
        List<String> solution = f.solve();

        assertTrue(solution.contains("1-2-3"));

    }

    @Test
    public void test1in() throws IOException {
        int[][] links = parse("src/main/java/week4/bits/test1/friends/friends.in");
        Friends f = new Friends(6, links);
        List<String> solution = f.solve();

        assertTrue(solution.contains("1-3-6"));
        assertTrue(solution.contains("2-4-6"));
        assertTrue(solution.contains("1-2-6"));
    }

    @Test
    public void test3() throws IOException {
        int[][] links = parse("src/main/java/week4/bits/test1/friends/friends3.txt");
        Friends f = new Friends(6, links);
        List<String> solution = f.solve();
        assertTrue(solution.contains("1-2-3-6"));
    }

    @Test
    public void test2in() throws IOException {
        int[][] links = parse("src/main/java/week4/bits/test1/friends/friends2.in");
        Friends f = new Friends(20, links);
        List<String> solution = f.solve();
        assertTrue(solution.contains("5-6-11-12-15-17-18"));
        assertTrue(solution.contains("3-5-6-11-15-17-18"));
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

    @Test
    public void addBitTest(){
        int n = 1 << 2;
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toBinaryString(8));
        int x = 8 | n;
        System.out.println(Integer.toBinaryString(x));
    }
}
