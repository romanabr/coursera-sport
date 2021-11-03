package week3.rectangle;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RectangleTest {

    private int[][] field;
    private List<Square> requests;

    static class Square{

        int x1, y1, x2, y2;

        public Square(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    @Test
    public void loadTest(){
        initFromFile("src/main/java/week3/rectangle/rectangle0.txt");
        Assert.assertEquals(5, field.length);
        Assert.assertEquals(6, field[0].length);
        Assert.assertEquals(3, requests.size());

    }

    public void initFromFile(String path){

        try {
            String first = Files.lines(Paths.get(path)).findFirst().orElse("");
            String[] sizes = first.split("\\s");
            int countFieldRows = Integer.parseInt(sizes[0]);
            System.out.println(countFieldRows);

            field = Files.lines(Paths.get(path))
                    .skip(1)
                    .limit(countFieldRows)
                    .map(line -> Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).toArray())
                    .toArray(int[][]::new);

            requests = Files.lines(Paths.get(path))
                    .skip(1 + countFieldRows + 1)
                    .map(line -> {
                        int[] ints = Arrays.stream(line.split("\\s")).mapToInt(Integer::parseInt).toArray();
                        return new Square(ints[0], ints[1], ints[2], ints[3]);
                    }).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
