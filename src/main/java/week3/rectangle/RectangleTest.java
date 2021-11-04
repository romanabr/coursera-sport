package week3.rectangle;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static common.Utils.showArray;

public class RectangleTest {

    private int[][] field;
    private List<Square> requests;
    private int[][] index;

    static class Square {

        int x1, x2, y1, y2;

        public Square(int x1, int x2, int y1, int y2) {
            this.x1 = x1 - 1;
            this.y1 = y1 - 1;
            this.x2 = x2 - 1;
            this.y2 = y2 - 1;
        }
    }

    @Test
    public void loadTest() {
        initFromFile("src/main/java/week3/rectangle/rectangle0.txt");
        Assert.assertEquals(5, field.length);
        Assert.assertEquals(6, field[0].length);
        Assert.assertEquals(3, requests.size());

    }

    private void initFromFile(String path) {

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
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void buildIndex() {
        index = new int[field.length][];
        for (int i = 0; i < field.length; i++) {
            index[i] = new int[field[i].length];
            for (int j = 0; j < field[i].length; j++) {
                if (i == 0 && j == 0) {
                    index[0][0] = field[0][0];
                } else if (j == 0) {
                    index[i][j] = index[i - 1][j] + field[i][j];
                } else if (i == 0) {
                    index[i][j] = index[i][j - 1] + field[i][j];
                } else {
                    index[i][j] = index[i - 1][j] + index[i][j - 1] - index[i - 1][j - 1] + field[i][j];
                }
            }
        }
    }

    @Test
    public void test0() {
        initFromFile("src/main/java/week3/rectangle/rectangle0.txt");
        buildIndex();
        showArray(field);
        System.out.println();
        showArray(index);

        Assert.assertEquals(13, request(requests.get(0)));
        Assert.assertEquals(18, request(requests.get(1)));
        Assert.assertEquals(29, request(requests.get(2)));

    }

    @Test
    public void test1() {
        initFromFile("src/main/java/week3/rectangle/rectangle1.txt");
        buildIndex();

        System.out.println(requests.size());
         long sum = requests.stream().mapToLong(this::request).sum();
        System.out.println("sum = " + sum);

    }

    private int request(Square s) {

        int result = index[s.x2][s.y2];

        if (s.x1 > 0) {
            result -= index[s.x1 - 1][s.y2];

        }
        if (s.y1 > 0) {
            result -= index[s.x2][s.y1 - 1];
        }

        if (s.x1 > 0 && s.y1 > 0) {
            result += index[s.x1 - 1][s.y1 - 1];
        }

        return result;
    }
}
