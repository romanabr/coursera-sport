package week1;

//Перебор
public class PathThrough {

    private static int lineCount = 1;
    public static void main(String[] args) {
//        loop();
        recursion();
    }


    private static void loop() {
        int i = 0;
        char min = 'a';
        char max = 'c';

        for (char a = min; a < max; a++) {
            for (char b = min; b < max; b++) {
                for (char c = min; c < max; c++) {
                    System.out.println(i++ + ": " + a + b + c);
                }
            }
        }

    }

    private static void recursion() {

        char[] vector = new char[8];
        rec(vector, 0);
    }

    private static void rec(char[] vector, int index) {
        char min = 'a';
        char max = 'd';

        for (char c = min; c < max; c++) {
            vector[index] = c;
            if (index + 1 < vector.length) {
                rec(vector, index + 1);
            } else {
                System.out.println(lineCount++ + " "+ String.copyValueOf(vector));
            }
        }
    }


}
