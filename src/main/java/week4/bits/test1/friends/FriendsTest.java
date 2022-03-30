package week4.bits.test1.friends;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

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
    }

    @Test
    public void test2() throws IOException {
        int[][] links = parse("src/main/java/week4/bits/test1/friends/friends.in");
        Friends f = new Friends(6, links);
        f.solve();
    }

    @Test
    public void test3() throws IOException {
        int[][] links = parse("src/main/java/week4/bits/test1/friends/friends3.txt");
        Friends f = new Friends(6, links);
        f.solve();
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
