package week1;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Task3Terms {

//    Разбиение числа на слагаемые.
//    Выведите все разбиения числа nn на натуральные слагаемые.
//    Разбиения, отличающиеся только порядком слагаемых, считаются одинаковыми, поэтому выводите слагаемые в каждом разбиении в порядке неубывания.
//
//    Входные данные:  Натуральное число n.
//    Выходные данные: Выведите разбиения числа nn на слагаемые в лексикографическом порядке, каждое разбиение - в отдельной строке.
//    Числа в каждом разбиении должны идти в порядке неубывания, разделяться знаками “+” (без пробелов) и в сумме давать n.
//
//    Пример входных данных: 5
//    Пример выходных данных:
//        1+1+1+1+1
//        1+1+1+2
//        1+1+3
//        1+2+2
//        1+4
//        2+3
//        5
//
//    В качестве ответа на задание выберите разбиение на слагаемые числа nn = 7 с номером 10 в лексикографическом порядке.
//    Для примера из условия разбиение с номером 4 имеет вид "1+2+2”.

//    Ответ:
//        7) 1+ 3 + 3
//        13672) 2+3+3+4+4+6+13

    private static final int n = 35;
    private static final int[] answer = new int[n]; //максимальная длина вектора n*1=n
    private static int lineCounter = 0;

    public static void main(String[] args) {

        rec(0, n);
    }

    private static void rec(int index, int max) {
//        System.out.println("rec("+index+", "+ max+")");

        for (int i = 1; i <= max; i++) {

            answer[index] = i;
            int curSum = curSum(index);
            int tail = n - curSum;

            if (tail > 0) {
                rec(index + 1, tail);
            } else if (tail == 0) {
                int[] solution = Arrays.copyOf(answer, index + 1);

                if(isSortedCorrect(solution)){
                    lineCounter++;
                    System.out.println(lineCounter +")\t"+ Arrays.stream(solution).mapToObj(n -> "" + n).collect(Collectors.joining("+")));
                }
            }
        }
    }

    private static int curSum(int index) {
        int sum = 0;
        for (int i = 0; i <= index; i++) {
            sum += answer[i];
        }
        return sum;
    }

    private static boolean isSortedCorrect(int[] solution){
        //проверяем порядок сортировки полученного решения
        //подходят только решения в которых слева цифра меньше либо равна цифре с права
        //1+2+2  ok
        //2+1+2  нет
        for (int j = 0; j <solution.length-1; j++) {
            if(solution[j] > solution[j+1]){
                return false;
            }
        }
        return true;
    }
}
