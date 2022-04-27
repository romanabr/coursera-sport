package week3.arithmetic;

import org.junit.Assert;
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
        String exp = "21-27+34+20-29-24+38+38-22-24";
        Assert.assertEquals(exp, new ExprDynamic(model).solve());
    }

    @Test
    public void test2(){
        Model model = read("src/main/java/week3/arithmetic/doc/in2.txt");
        String exp = "91-67-84-50-69-74-78-58-62-64-55-95-81-77-61-91-95-92-77-86-91-54-52-53-92-82-71-66-68-95-97-76-71" +
                "+88-69-62-67-99-85-94-53-61-72-83-73-64-91-61-53-68";
        Assert.assertEquals(exp, new ExprDynamic(model).solve());
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
