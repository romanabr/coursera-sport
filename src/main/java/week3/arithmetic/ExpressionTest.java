package week3.arithmetic;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {

    @Test
    public void test1(){
        Model model = read("src/main/java/week3/arithmetic/doc/in1.txt");
        model.numbers[0] -= model.sum;

        String result = new ExprDynamic(model.numbers).solve();
        System.out.println("result = " + result);
    }

    @Test
    public void testDataLoad() {

        Model model = read("src/main/java/week3/arithmetic/doc/in1.txt");
        assertEquals(25, model.sum);
        assertEquals(21, model.numbers[0]);

    }

    private Model read(String file) {
        try {
            Path path = Paths.get(file);
            List<int[]> collect = Files.lines(path)
                    .map(line -> Arrays.stream(line.split("\\s")).map(Integer::valueOf).mapToInt(Integer::intValue).toArray())
                    .collect(Collectors.toList());

            Model m = new Model();
            m.sum = collect.get(0)[1];
            m.numbers = collect.get(1);

            return m;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
