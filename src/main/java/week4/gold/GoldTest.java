package week4.gold;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class GoldTest {

    private static final Logger log = LoggerFactory.getLogger(GoldTest.class);

    @Test
    public void testGold0() {
        int[] data = {1, 2, 3, 4, 5};
        int minDiff = new GoldBrutForce(data).solve();
        Assert.assertEquals(1, minDiff);
    }

    @Test
    public void testGold1() {
        int[] data = load("src/main/java/week4/gold/gold.in");
        int minDiff = new GoldBrutForce(data).solve();
        Assert.assertEquals(20, minDiff);
    }


    @Test
    public void testLoad() {
        int[] data = load("src/main/java/week4/gold/gold.in");
        Assert.assertEquals(15, data.length);
        Assert.assertEquals(55, data[0]);
    }

    @Test
    public void dynamicData() {
        int[] data = load("src/main/java/week4/gold/gold.in");

        for (int i = 2; i <= data.length ; i++) {
            log.info(" ---- {} -----", i);
            new GoldBrutForce(Arrays.copyOf(data, i)).solve();
        }
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


