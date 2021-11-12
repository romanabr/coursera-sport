package week3.backpack;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BackpackDynamic {

    private int backpackCapacity;

    private List<Item> items;

    private void readData(String path) {

        try {
            String[] firstLine = Files.lines(Path.of(path)).findFirst().orElse("0 0").split("\s");
            int thingsCount = Integer.parseInt(firstLine[0]);
            backpackCapacity = Integer.parseInt(firstLine[1]);

            AtomicInteger counter = new AtomicInteger();
            items = Files.lines(Path.of(path)).skip(1).map(s -> {
                        String[] split = s.split("\s");
                        return new Item(counter.incrementAndGet(), Integer.parseInt(split[0]), Integer.parseInt(split[0]));
                    })
                    .collect(Collectors.toList());

            Assert.assertEquals(thingsCount, items.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test0(){
        readData("src/main/java/week3/backpack/rucksack0.txt");

        Assert.assertEquals(12, backpackCapacity);
        Assert.assertEquals(3, items.size());
    }

    @Test
    public void test1(){
        readData("src/main/java/week3/backpack/rucksack1.txt");

        Assert.assertEquals(10000, backpackCapacity);
        Assert.assertEquals(100, items.size());
    }
}
