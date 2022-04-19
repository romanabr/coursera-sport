package week2.petrols;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Petrols {

    @Test
    public void test1() throws IOException {
        Assert.assertEquals(2, petrols("src/main/java/week2/petrols/petrols1.txt"));
    }

    @Test
    public void test2() throws IOException {
        Assert.assertEquals(4, petrols("src/main/java/week2/petrols/petrols2.txt"));
    }

    @Test
    public void test3() throws IOException {
        Assert.assertEquals(1021, petrols("src/main/java/week2/petrols/petrols3.txt"));
    }

    private int petrols(String path) throws IOException {
        List<String> lines = Files.lines(Path.of(path)).collect(Collectors.toList());
        String[] split = lines.get(0).split("\s");
        int totalDistance = Integer.parseInt(split[1]);
        int tank = Integer.parseInt(split[2]);

        List<Integer> petrols = Arrays.stream(lines.get(1).split("\s")).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println("distance: " + totalDistance + ", tank: " + tank + ", petrols.size: " + petrols.size());
        petrols.add(totalDistance);

        int count = 0;
        int pitStop = 0;
        for (int i = 0; i < petrols.size(); i++) {
            for (int j = i; j < petrols.size(); j++) {
                int distance = petrols.get(j);
                if (distance - pitStop > tank) {
                    i = j - 1;
                    pitStop = petrols.get(i);
                    count++;

                    System.out.println("pitStop: "+ pitStop);
                    break;
                }
            }
        }

        return count;
    }
}
