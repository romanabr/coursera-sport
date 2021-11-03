package week1;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Task2_5_Salesman {

    private static int NODES = 10;

    private static final String input4 = """
            0 1 4 6
            1 0 5 2
            4 5 0 3
            6 2 3 0
            """;

    private static final String input10 = """
            0 41 67 0 78 5 91 4 18 67
            41 0 34 69 58 45 95 2 95 99
            67 34 0 24 62 81 42 53 47 35
            0 69 24 0 64 27 27 92 26 94
            78 58 62 64 0 61 36 82 71 3
            5 45 81 27 61 0 91 21 38 11
            91 95 42 27 36 91 0 16 69 22
            4 2 53 92 82 21 16 0 12 33
            18 95 47 26 71 38 69 12 0 73
            67 99 35 94 3 11 22 33 73 0
            """;

    private static final int[][] DISTANCES = parseDistance(input10);
    private static int[] path = new int[NODES];

    private static int minPathLen = Integer.MAX_VALUE;
    private static String minPath = "";


    public static void main(String[] args) {

        System.out.println(DISTANCES);
        process(0, 0);

        System.out.println("minPathLen = " + minPathLen);
        System.out.println("minPath = " + minPath);
    }

    //index - позиция в массиве path, порядковый номер города
    private static void process(int index, int currentDistance) {

        for (int i = 0; i < NODES; i++) {
            path[index] = i;

            { //проверяем отсутствие дублей в получившемся пути

                if(path[0]>0){
                    continue;
                }

                int[] subpath = Arrays.copyOf(path, index + 1);
                int uniqMembers = Arrays.stream(subpath).boxed().collect(Collectors.toSet()).size();
                if(uniqMembers<subpath.length){
                    continue;
                }
            }

            if(index+1 < path.length){
                process(index+1, currentDistance);
            }else {
                String result = Arrays.stream(path).mapToObj(n -> "" + n).collect(Collectors.joining(" "));
                int distance = calcDistance(index);
                System.out.println("result = " + result + "\t" + distance) ;

                if(distance<minPathLen){
                    minPathLen = distance;
                    minPath = result;
                }
            }
        }
    }

    private static int calcDistance(int index) {
        int res = 0;
        for (int i = 0; i < path.length; i++) {
            int from = path[i];
            int to = i + 1 < path.length ? path[i + 1] : path[0];

            res += DISTANCES[from][to];
        }

        return res;
    }

    private static int[][] parseDistance(String source){
        int[][] result = new int[NODES][NODES];
        String[] lines = source.split("\n");
        for (int i = 0; i <lines.length; i++) {
            String[] split = lines[i].split("\\s");
            for (int j = 0; j < split.length; j++) {
                result[i][j] = Integer.parseInt(split[j]);
            }
        }

        return result;
    }
}
