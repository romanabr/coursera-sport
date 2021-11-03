package week1;

public class Task6Combinations {

/*    Расстановка фишек. Имеется полоса размера 1×n, разбитая на единичные клетки.
    Нужно расставить в клетках полосы m фишек, чтобы никакие две фишки не стояли в соседних клетках.
    Выведите все возможные расстановки.

    Входные данные: Натуральные числа nn и mm.
    Выходные данные: Выведите все корректные расстановки фишек. Каждая расстановка должна быть выведена в отдельной строке.
    Клетки, занятые фишками, обозначаются символами ‘*’, свободные клетки - точками ‘.’.
    Выводите расстановки в лексикографическом порядке, считая, что ‘*’ < ‘.’.

    Пример входных данных: 5 2
    Пример выходных данных:
        *.*..
        *..*.
        *...*
        .*.*.
        .*..*
        ..*.*

    В качестве ответа на задание выберите расстановку фишек для nn = 7, mm = 3 с номером 7 в лексикографическом порядке.
    Для примера из условия расстановка с номером 3 имеет вид *...*.
 */

    //Ответ: 7: .*.*.*.
    // 24008: .*.*.....*.*.*.*....*..*.

    private static final String alphabet = "*.";
    private static final char[] field = new char[25]; //field length
    private static final int m = 8; //star count

    private static int linesCount = 0;

    public static void main(String[] args) {
        rec(0);
    }
//(()) )((())
    private static void rec(int index) {
        for (int i = 0; i < alphabet.length(); i++) {
            field[index] = alphabet.charAt(i);

            int starCount = count(index);

            if (index + 1 < field.length) {
                rec(index + 1);
            } else {
                String result = String.copyValueOf(field);
                if(starCount == m && !result.contains("**")){
                    linesCount++;
                    System.out.println(linesCount + ": " + result);
                }

            }
        }
    }

    private static int count(int index) {
        int count = 0;
        for (int i = 0; i <= index; i++) {
            if (field[i] == alphabet.charAt(0)) {
                count++;
            }
        }
        return count;
    }

}
