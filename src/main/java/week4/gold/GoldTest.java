package week4.gold;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.OptionalInt;

import static org.junit.Assert.assertEquals;

public class GoldTest {

    private static final Logger log = LoggerFactory.getLogger(GoldTest.class);

    @Test
    public void testLoad() {
        int[] data = load("src/main/java/week4/gold/gold.in");
        assertEquals(15, data.length);
        assertEquals(55, data[0]);
    }

    @Test
    public void testGold0() {
        int[] data = {1, 2, 3, 4, 5};
        assertEquals(1, new GoldBrutForce(data).solve());
        assertEquals(1, new GoldDynamic(data).solve());
    }

    @Test
    public void testGold1() {
        int[] data = load("src/main/java/week4/gold/gold.in");
        assertEquals(20, new GoldBrutForce(data).solve());
        assertEquals(20, new GoldDynamic(data).solve());
    }


    @Test
    public void testGold01Dynamic() {
        int[] data = {5, 6, 3};
        assertEquals(2, new GoldBrutForce(data).solve());
        assertEquals(2, new GoldDynamic(data).solve());
    }

    @Test
    public void testGold2Dynamic() {
        int[] data = load("src/main/java/week4/gold/gold2.in");
        assertEquals(253, new GoldDynamic(data).solve());
    }

    @Test
    public void testGold3() {
        int[] data = load("src/main/java/week4/gold/gold3.in");
        assertEquals(20, data.length);
        OptionalInt max = Arrays.stream(data).max();
        System.out.println(max);
//        993 511 294
        assertEquals(1808, new GoldBrutForce(data).solve());
        //java.lang.OutOfMemoryError: Java heap space
        int left = 71675079;
        int right = 71675079;

        int left1 = 0 + 0 + 838536094 + 0 + 0 + 906036573 + 993511294 + 0 + 0 + 0 + 802314304 + 0 + 0 + 0 + 0 + 0 + 0 + 826244110 + 0 + 0;
        int right1 = 175153496 + 592065322 + 0 + 649685193 + 157025731 - 0 - 0 + 171484783 + 220971947 + 943556574 - 0 + 281465716 + 794901123 + 638815932 + 921760634 + 960666392 + 945324195 - 0 + 716532966 + 492197859;
        int x = left1 - right1;
        System.out.println("x: " + x);

//        22:32:10.463 INFO : mask: 132196 / 100000010001100100, delta:0, left: 0-0-838536094-0-0-906036573-993511294-0-0-0-802314304-0-0-0-0-0-0-826244110-0-0=71675079, right: 175153496-592065322-0-649685193-157025731-0-0-171484783-220971947-943556574-0-281465716-794901123-638815932-921760634-960666392-945324195-0-716532966-492197859=71675079
//        22:32:10.464 INFO : mask: 916379 / 11011111101110011011, delta:0, left: 175153496-592065322-0-649685193-157025731-0-0-171484783-220971947-943556574-0-281465716-794901123-638815932-921760634-960666392-945324195-0-716532966-492197859=71673271, right: 0-0-838536094-0-0-906036573-993511294-0-0-0-802314304-0-0-0-0-0-0-826244110-0-0=71673271

    }


    private int[] load(String path) {
        try {
            String line = Files.lines(Path.of(path)).skip(1).findFirst().orElse("");
            return Arrays.stream(line.split("\s")).mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGold4() {
        int[] data = load("src/main/java/week4/gold/gold4.in");
        assertEquals(124759532, nonHonestGreedy(data));
    }

    @Test
    public void testGold4_1() {
        int[] data = {1, 2, 3, 4};
        assertEquals(4, nonHonestGreedy(data));
    }

    private int nonHonestGreedy(int[] data) {
        int sum = Arrays.stream(data).sum();
        int halfSum = Arrays.stream(data).sorted().limit(data.length / 2).sum();
        return sum - 2 * halfSum;
    }

}


