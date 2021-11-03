package week3.bugs;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Bugs {

    @Test
    public void testReadField() throws IOException {
        int[][] field = readField("src/main/java/week3/bugs/bug0.txt");

        assertEquals(3, field.length);
        assertEquals(4, field[0].length);
        assertEquals(4, field[1].length);
    }

    private int[][] readField(String path) throws IOException {

        return Files.lines(Paths.get(path))
                .skip(1)
                .map(line -> Arrays.stream(line.split("\\s"))
                        .mapToInt(Integer::valueOf)
                        .toArray())
                .toArray(int[][]::new);
    }


    private Track bestTrack = new Track("", 0);
    private AtomicInteger counter = new AtomicInteger();

    private class Track {
        String path;
        Integer value;

        public Track(String path, Integer value) {
            this.path = path;
            this.value = value;
        }
    }

    @Test
    public void brutForce0() throws IOException {
        int[][] field = readField("src/main/java/week3/bugs/bug0.txt");

        //делаем перебор
        brutForceStep(0, 0, field, bestTrack);

        System.out.println("bestPath: " + bestTrack.path + "\t" + bestTrack.value + "\t" + counter);

        assertEquals("RRDRD", bestTrack.path);
        assertEquals(22, bestTrack.value.intValue());
    }

    @Test
    public void brutForce1() throws IOException {
        int[][] field = readField("src/main/java/week3/bugs/bug1.txt");
        assertEquals(7, field.length);
        assertEquals(8, field[0].length);


        Instant start = Instant.now();
        //делаем перебор
        brutForceStep(0, 0, field, bestTrack);

        System.out.println("bestPath: " + bestTrack.path + "\t" + bestTrack.value + "\t" + counter + "\t"
                + ChronoUnit.MILLIS.between(start, Instant.now()));
        assertEquals("RDDRRDRDDRRRD", bestTrack.path);
        assertEquals(804, bestTrack.value.intValue());
    }

    @Test //не решается полным перебором
    public void brutForce2() throws IOException {
        int[][] field = readField("src/main/java/week3/bugs/bug2.txt");
        assertEquals(100, field.length);
        assertEquals(120, field[0].length);

        //не решается полным перебором
    }

    @Test
    public void dynamic0() throws IOException {
        int[][] field = readField("src/main/java/week3/bugs/bug0.txt");
        assertEquals(3, field.length);
        assertEquals(4, field[0].length);

        int[][] cache = preprocessField(field);

        showField(field);
        showField(cache);
        Consumer<int[][]> func = (array) -> {
        };

        func.accept(field);
        func.accept(cache);


        //field
        //5 3 2 2
        //2 1 7 3
        //4 3 1 2

        //cache
        //5   8   10  12
        //7   9   17  20
        //11  14  18  22
        assertEquals(5, cache[0][0]);
        assertEquals(12, cache[0][3]);
        assertEquals(11, cache[2][0]);
        assertEquals(22, cache[2][3]); //result

        //RRDRD
        String path = dynamicPath(0, 0, "", cache);
        assertEquals("RRDRD", path);
    }

    @Test
    public void dynamic1() throws IOException {
        int[][] field = readField("src/main/java/week3/bugs/bug1.txt");
        int[][] cache = preprocessField(field);
        assertEquals(804, cache[6][7]);

        showField(cache);
        String path = findPath(field, cache);
        System.out.println("path = " + path);
        assertEquals("RDDRRDRDDRRRD", path);


        //todo: какой правильный ?
//        assertEquals("RDDRRDRDDRRRD", dynamicPath(0, 0, "", cache));
//        assertEquals("RDDDDDDRRRRRR", dynamicPath(0, 0, "", cache));
    }

    @Test
    public void dynamic2() throws IOException {
        int[][] field = readField("src/main/java/week3/bugs/bug2.txt");
        assertEquals(100, field.length);
        assertEquals(120, field[0].length);
        int[][] cache = preprocessField(field);

        assertEquals(16149, cache[99][119]);
        assertEquals("DDDRDRRRRRRRRRRRRRRDRRRDRRRDRDRDDRRRDRDRRRRRRDRRRRRRDRRDRRRRDRDDDRRDRRDRRDDDRRDRDRDDDRDRRRDRDDDDRRDDRDRDRDDDDDRDDDDRRRDDDDRDDDRRRDRDRDRDRDDDRRRRDDRDDDDRDDDDRDDDRDRRRRRRDDRRRRDDDRDRRRRDRDRRDDRDDRDRRRDRRDRDRDDDDDDRRRDRRR", findPath(field, cache));
    }

    private String findPath(int[][] field, int[][] cache) {

        List<String> pathList = new ArrayList<>();

        int i = cache.length - 1;
        int j = cache[0].length - 1;

        while (true) {
            int upValue = i - 1 >= 0 ? cache[i - 1][j] : 0;
            int lValue = j - 1 >= 0 ? cache[i][j - 1] : 0;
            if (upValue == 0 && lValue == 0) {
                break;
            }

            int prevValue = cache[i][j] - field[i][j];
            if (prevValue == upValue) {
                pathList.add("D");
                i--;
            } else if (prevValue == lValue) {
                pathList.add("R");
                j--;
            } else {
                break;
            }
        }

        Collections.reverse(pathList);
        return String.join("", pathList);
    }

    private String dynamicPath(int i, int j, String path, int[][] cache) {

        int rValue = j + 1 < cache[i].length ? cache[i][j + 1] : 0;
        int dValue = i + 1 < cache.length ? cache[i + 1][j] : 0;

        if (rValue + dValue == 0) {
            //не можем больше двигаться
            return path;
        } else if (rValue > dValue) {
            return dynamicPath(i, j + 1, path + "R", cache);
        } else {
            return dynamicPath(i + 1, j, path + "D", cache);
        }
    }

    private int[][] preprocessField(int[][] field) {
        int[][] result = new int[field.length][];
        for (int i = 0; i < field.length; i++) {
            result[i] = new int[field[i].length];
            for (int j = 0; j < field[i].length; j++) {
                int up = i - 1 >= 0 ? result[i - 1][j] : 0;
                int left = j - 1 >= 0 ? result[i][j - 1] : 0;

                result[i][j] = field[i][j] + Math.max(up, left);
            }
        }

        return result;
    }


    private void brutForceStep(int i, int j, int[][] field, Track track) {

        int value = track.value + field[i][j];

        if (i + 1 < field.length) {
            brutForceStep(i + 1, j, field, new Track(track.path + "D", value));
        }

        if (j + 1 < field[i].length) {
            brutForceStep(i, j + 1, field, new Track(track.path + "R", value));
        }

        if (i == field.length - 1 && j == field[0].length - 1) { //мы в последней клетке
            counter.incrementAndGet();
            System.out.println(track.path + "\t" + value + "\t" + counter.get());

            if (value > bestTrack.value) {
                bestTrack = new Track(track.path, value);
//                System.out.println(track.path + "\t" + value + "\t"+  counter.get());
            }
        }
    }

    private void showField(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}

