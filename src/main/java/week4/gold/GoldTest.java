package week4.gold;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class GoldTest {

    @Test
    public void testGold0() {
        int[] data = {1, 2, 3, 4, 5};
        int minDiff = new Gold(data).solve();
        Assert.assertEquals(1, minDiff);
    }

    @Test
    public void testLoad() {
//        URL resource = this.getClass().getResource("/week4/gold/gold.in");

        int[] data = load("src/main/java/week4/gold/gold.in");
        Assert.assertEquals(15, data.length);
        Assert.assertEquals(55, data[0]);
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


