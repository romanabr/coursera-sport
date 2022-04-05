package common;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    public static String showSet(Set<Integer> set) {
        return set.stream().sorted().map(Utils::natural).map(Objects::toString).collect(Collectors.joining("-"));
    }

    public static String showArray(int[] array) {
        return Arrays.stream(array).sorted().map(Utils::natural).mapToObj(Objects::toString).collect(Collectors.joining("-"));
    }

    public static int natural(int x) {
        return x + 1;
    }


    public static void showArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void showArray(long[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

}
