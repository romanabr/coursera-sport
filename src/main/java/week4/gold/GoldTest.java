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
    public void testGold0() {
        int[] data = {1, 2, 3, 4, 5};
        int minDiff = new GoldBrutForce(data).solve();
        assertEquals(1, minDiff);
    }

    @Test
    public void testGold1() {
        int[] data = load("src/main/java/week4/gold/gold.in");
        int minDiff = new GoldBrutForce(data).solve();
        assertEquals(20, minDiff);
    }


    @Test
    public void testLoad() {
        int[] data = load("src/main/java/week4/gold/gold.in");
        assertEquals(15, data.length);
        assertEquals(55, data[0]);
    }

    @Test
    public void dynamicData() {
        int[] data = load("src/main/java/week4/gold/gold.in");

        for (int i = 2; i <= data.length; i++) {
            log.info(" ---- {} -----", i);
            new GoldBrutForce(Arrays.copyOf(data, i)).solve();
        }
    }

    @Test
    public void testGold0Dynamic() {
        int[] data = {1, 2, 3, 4, 5};
        assertEquals(1, new GoldBrutForce(data).solve());
        assertEquals(1, new GoldDynamic(data).solve());
    }

    @Test
    public void testGold01Dynamic() {
        int[] data = {5, 6, 3};
        assertEquals(2, new GoldBrutForce(data).solve());
        assertEquals(2, new GoldDynamic(data).solve());
    }

    @Test
    public void testGold1Dynamic() {
        int[] data = load("src/main/java/week4/gold/gold.in");
        assertEquals(20, new GoldBrutForce(data).solve());
        assertEquals(20, new GoldDynamic(data).solve());
    }

    @Test
    public void testGold2Dynamic() {
        int[] data = load("src/main/java/week4/gold/gold2.in");
        assertEquals(253, new GoldDynamic(data).solve());
    }

    @Test
    public void testGold3Dynamic() {
        int[] data = load("src/main/java/week4/gold/gold3.in");
        OptionalInt max = Arrays.stream(data).max();
        System.out.println(max);
//        993 511 294
//        assertEquals(253, new GoldDynamic(data).solve());
        //java.lang.OutOfMemoryError: Java heap space
    }


    private int[] load(String path) {


        try {
            String line = Files.lines(Path.of(path)).skip(1).findFirst().orElse("");
            return Arrays.stream(line.split("\s")).mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


