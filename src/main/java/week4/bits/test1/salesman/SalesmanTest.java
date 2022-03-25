package week4.bits.test1.salesman;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class SalesmanTest {

    int[][] d0 = {
            {0, 1, 4, 6},
            {1, 0, 5, 2},
            {4, 5, 0, 3},
            {6, 2, 3, 0}};

    int[][] d1 = {
            {0, 12, 10, 19, 8},
            {12, 0, 3, 7, 6},
            {10, 3, 0, 2, 20},
            {19, 7, 2, 0, 4},
            {8, 6, 20, 4, 0}
    };

    @Test
    public void test0(){
        Salesman s = new Salesman(d0);
        s.solve();
        Assert.assertEquals(10, s.getMinDistance());
        Assert.assertEquals("0 1 3 2", s.getPaths().get(0));
    }

    @Test
    public void test1() {
        Salesman s = new Salesman(d1);
        s.solve();
        Assert.assertEquals(29, s.getMinDistance());
        Assert.assertEquals("0 1 2 3 4", s.getPaths().get(0));
    }

    @Test
    public void test2() throws IOException {
        int[][] d2 = Files.lines(Paths.get("src/main/java/week4/bits/test1/salesman/salesman2.in"))
                .skip(1)
                .map(line -> Arrays.stream(line.split("\\s"))
                        .mapToInt(Integer::valueOf)
                        .toArray())
                .toArray(int[][]::new);

        Salesman s = new Salesman(d2);
        s.solve();
        System.out.println("minDistance: " + s.getMinDistance());
        s.getPaths().forEach(System.out::println);

        Assert.assertEquals(111, s.getMinDistance());
        Assert.assertEquals("0 3 10 8 6 13 14 15 2 7 1 4 9 12 11 5", s.getPaths().get(0));
    }
}
