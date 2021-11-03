package week2.icecream;

import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.junit.Test;

public class Icecream {

    @Test
    public void test0() throws IOException {
        Assert.assertEquals(2, process2("src/main/java/week2/icecream/icecream0.txt"));
    }

    @Test
    public void test1() throws IOException {
        Assert.assertEquals(6, process2("src/main/java/week2/icecream/icecream1.txt"));
    }

    @Test
    public void test2() throws IOException {
        Assert.assertEquals(88, process2("src/main/java/week2/icecream/ice2.in"));
    }

    private int process2(String path) throws IOException {
        String[] names = Files.lines(Path.of(path)).skip(1).toArray(String[]::new);

        Set<String> current = new HashSet<>();
        int count = 1;
        for (int i = 0; i < names.length; i++) {

            if(current.contains(names[i])){
                System.out.println(count +"\t" + current);

                current = new HashSet<>();
                count++;
            }
            current.add(names[i]);
        }
        System.out.println(count +"\t" + current);

        return count;
    }

    private int process(String path) throws IOException {
        List<String> lines = Files.lines(Path.of(path)).collect(Collectors.toList());
        Map<String, AtomicInteger> data = new HashMap<>();

        AtomicInteger max = new AtomicInteger();
        lines.stream().skip(1).forEach(name -> {
            AtomicInteger counter = data.get(name);
            if(counter==null){
                counter = new AtomicInteger(0);
                data.put(name, counter);
            }

            max.set(Math.max(max.get(), counter.incrementAndGet()));

        });

        return max.get();
    }

}
