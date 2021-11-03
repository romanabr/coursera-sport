package week1;

import java.util.HashSet;
import java.util.Set;

//Перестановки
public class Permutations {

    //    Задача: получить все возможные перестановки в строке длинны n состоящих из m симвовлов
    public static void main(String[] args) {

        int n = 5; //длина строки

        char[] array = new char[n];
        rec(0, array);
    }

    private static void rec(int i, char[] array) {
        int m = 5; //кол-во символов в строке, начиная от 'a'

        for (char c = 'a'; c < 'a' + m; c++) {
            if (String.copyValueOf(array).substring(0, i).indexOf(c) < 0) {
                array[i] = c;
            } else {
                continue;
            }

            if (i + 1 < array.length) {
                rec(i + 1, array);
            } else {
                System.out.println(String.copyValueOf(array));
            }
        }
    }
}