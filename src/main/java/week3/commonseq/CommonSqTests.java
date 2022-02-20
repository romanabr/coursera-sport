package week3.commonseq;

import common.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CommonSqTests {

    private static final String TEST0 = "src/main/java/week3/commonseq/seq0.txt";
    private static final String TEST1 = "src/main/java/week3/commonseq/seq1.txt";
    private static final String TEST2 = "src/main/java/week3/commonseq/seq2.txt";

    private List<Integer> read(String file, int skip) {

        try {
            List<Integer> result = Arrays.stream(Files.lines(Path.of(file))
                    .skip(skip).limit(1).findFirst().orElse("")
                    .split("\s")).map(Integer::valueOf).collect(Collectors.toList());

            System.out.println(result.stream().map(Object::toString).collect(Collectors.joining("\t")));
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_read() {

        Assert.assertEquals(7, read(TEST0, 1).size());
        Assert.assertEquals(9, read(TEST0, 3).size());
        Assert.assertEquals(20, read(TEST1, 1).size());
        Assert.assertEquals(20, read(TEST1, 3).size());
        Assert.assertEquals(1000, read(TEST2, 1).size());
        Assert.assertEquals(2000, read(TEST2, 3).size());
    }

    private String dinamic(List<Integer> first, List<Integer> second) {
        int[][] dinamic = new int[second.size()][];
        dinamic[0] = new int[first.size()];
        //инициализируем горизонталь
        for (int i = 0; i < first.size(); i++) {
            dinamic[0][i] = first.get(i).equals(second.get(0)) ? 1 : 0;
        }

        //инициализируем вертикаль
        for (int i = 1; i < second.size(); i++) {
            dinamic[i] = new int[first.size()];
            dinamic[i][0] = second.get(i).equals(first.get(0)) ? 1 : 0;
        }

        //заполняем серединку
        for (int i = 1; i < second.size(); i++) {
            for (int j = 1; j < first.size(); j++) {

                dinamic[i][j] = Math.max(dinamic[i - 1][j], dinamic[i][j - 1]);
                if (first.get(j).equals(second.get(i))) {
                    dinamic[i][j] = Math.max(dinamic[i][j], dinamic[i - 1][j - 1] + 1);
                }
            }
        }

        System.out.println("\n\n");
        Utils.showArray(dinamic);

        List<Integer> result = new ArrayList<>();
        certificate(second.size() - 1, first.size() - 1, dinamic, first, result);


        List<String> reverseList = new ArrayList<>(result.size());
        for (int i = result.size() - 1; i >= 0; i--) {
            reverseList.add(result.get(i).toString());
        }
        System.out.println("certificate.length: " + reverseList.size());
        return String.join(",", reverseList);
    }

    private void certificate(int i, int j, int[][] dinamic, List<Integer> first, List<Integer> result) {


        //up-left
        if (dinamic[i][j] > 0) {
            if (i >= 1 && dinamic[i][j] == dinamic[i - 1][j]) {
                certificate(i - 1, j, dinamic, first, result); //up
            } else if (j >= 1 && dinamic[i][j] == dinamic[i][j - 1]) {
                certificate(i, j - 1, dinamic, first, result); //left
            } else if (i >= 1 && j >= 1 && dinamic[i][j] == dinamic[i - 1][j - 1] + 1) {
                System.out.println(first.get(j) + ", [i, j] = [" + i + ", " + j + "]");
                result.add(first.get(j));
                certificate(i - 1, j - 1, dinamic, first, result);
            } else if (i == 0) {

                if (j >= 1 && dinamic[i][j] == dinamic[i][j - 1] + 1) {
                    System.out.println(first.get(j) + ", [i, j] = [" + i + ", " + j + "]");
                    result.add(first.get(j));
                } else {
                    certificate(i, j - 1, dinamic, first, result);
                }

            } else if (j == 0) {
                if (dinamic[i][j] == dinamic[i - 1][j] + 1) {
                    System.out.println(first.get(j) + ", [i, j] = [" + i + ", " + j + "]");
                    result.add(first.get(j));
                } else {
                    certificate(i, j - 1, dinamic, first, result);
                }
            }
        }

        //left-up
//        if (dinamic[i][j] > 0) {
//            if (j >= 1 && dinamic[i][j] == dinamic[i][j - 1]) {
//                certificate(i, j - 1, dinamic, first, result); //left
//            } else if (i >= 1 && dinamic[i][j] == dinamic[i - 1][j]) {
//                certificate(i - 1, j, dinamic, first, result); //up
//            } else if (i >= 1 && j >= 1 && dinamic[i][j] == dinamic[i - 1][j - 1] + 1) {
//                System.out.println(first.get(j) + ", [i, j] = [" + i + ", " + j + "]");
//                result.add(first.get(j));
//                certificate(i - 1, j - 1, dinamic, first, result);
//            } else if (i == 0) {
//
//                if (j >= 1 && dinamic[i][j] == dinamic[i][j - 1] + 1) {
//                    System.out.println(first.get(j) + ", [i, j] = [" + i + ", " + j + "]");
//                    result.add(first.get(j));
//                } else {
//                    certificate(i, j - 1, dinamic, first, result);
//                }
//
//            } else if (j == 0) {
//                if (dinamic[i][j] == dinamic[i - 1][j] + 1) {
//                    System.out.println(first.get(j) + ", [i, j] = [" + i + ", " + j + "]");
//                    result.add(first.get(j));
//                } else {
//                    certificate(i, j - 1, dinamic, first, result);
//                }
//            }
//        }

    }

    @Test
    public void test_0() {
        List<Integer> first = read(TEST0, 1);
        List<Integer> second = read(TEST0, 3);

        String certificate = dinamic(first, second);
        System.out.println("certificate = " + certificate);
        Assert.assertEquals("4,2,1,6", certificate);
    }

    @Test
    public void test_1() {
        List<Integer> first = read(TEST1, 1);
        List<Integer> second = read(TEST1, 3);

        String certificate = dinamic(first, second);
        System.out.println("certificate = " + certificate);
    }

    @Test
    public void test_2() {
        List<Integer> first = read(TEST2, 1);
        List<Integer> second = read(TEST2, 3);

        String certificate = dinamic(first, second);
        System.out.println("certificate = " + certificate);
        Assert.assertEquals(certificate, "174,92,411,963,771,535,614,754,234,751,999,298,13,754,407,392,572,457,161,524,862,314,56,991,656,853,628,338,304,811,299,739,102,353,595,358,339,755,949,633,40,932,74,266,151,329,274,362,608,881,884,108,413,872,756,250,204,204,33,845,989,445,809,403,358,610,584,421,732,4,462,518,18,573,796,51,345,72,563");
    }
}




















