package week3.backpack;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BackpackDynamicTest {


    private static final String TEST1 = "src/main/java/week3/backpack/rucksack0.txt";
    private static final String TEST2 = "src/main/java/week3/backpack/rucksack1.txt";
    private static final String TEST3 = "src/main/java/week2/backpack/backpack1.txt";

    private BackpackRequest readData(String path) {

        try {

            String[] firstLine = Files.lines(Path.of(path)).findFirst().orElse("0 0").split("\s");
            int thingsCount = Integer.parseInt(firstLine[0]);
            int capacity = Integer.parseInt(firstLine[1]);

            AtomicInteger counter = new AtomicInteger();
            List<Item> items = Files.lines(Path.of(path)).skip(1).map(s -> {
                        String[] split = s.split("\s");
                        return new Item(counter.incrementAndGet(), Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                    })
                    .collect(Collectors.toList());



            Assert.assertEquals(thingsCount, items.size());
            return new BackpackRequest(capacity, items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void parse_test1(){
        BackpackRequest data = readData(TEST1);

        Assert.assertEquals(12, data.getBackpackCapacity());
        Assert.assertEquals(3, data.getItems().size());
    }

    @Test
    public void parse_test2(){
        BackpackRequest data = readData(TEST2);

        Assert.assertEquals(10000, data.getBackpackCapacity());
        Assert.assertEquals(100, data.getItems().size());
    }


    @Test
    public void brutForce_test1(){
        BackpackRequest data = readData(TEST1);
        BackpackBrutforceAlg alg = new BackpackBrutforceAlg();
        String solve = alg.solve(data);

        Assert.assertEquals(40, alg.getMaxCost());
        Assert.assertEquals("1 3", solve);
    }

    @Test
    public void brutForce_test3(){
        BackpackRequest data = readData(TEST3);
        BackpackBrutforceAlg alg = new BackpackBrutforceAlg();
        String solve = alg.solve(data);

        Assert.assertEquals(547, alg.getMaxCost());
        Assert.assertEquals("2 3 4 6 7 9 10", solve);
    }

    @Test
    public void dynamic_test1(){
        BackpackRequest data = readData(TEST1);
        BackpackDynamicAlg alg = new BackpackDynamicAlg();
        String solve = alg.solve(data);

        Assert.assertEquals(40, alg.getMaxCost());
        Assert.assertEquals("1 3", solve);
    }

    @Test
    public void dynamic_test3(){
        BackpackRequest data = readData(TEST3);
        BackpackDynamicAlg alg = new BackpackDynamicAlg();
        String solve = alg.solve(data);

        Assert.assertEquals(547, alg.getMaxCost());
        Assert.assertEquals("2 3 4 6 7 9 10", solve);
    }

    @Test
    public void dynamic_test2(){
        BackpackRequest data = readData(TEST2);
        BackpackDynamicAlg alg = new BackpackDynamicAlg();
        String solve = alg.solve(data);

        Assert.assertEquals(58638, alg.getMaxCost());
        Assert.assertEquals("1 7 14 19 42 57 60 83 89 91", solve);
    }
}
