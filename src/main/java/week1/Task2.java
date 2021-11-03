package week1;

//Вопрос 2
//Выведите в лексикографическом порядке все перестановки чисел от 1 до nn.
//Входные данные
//Натуральное число nn.
//Выходные данные
//Выведите перестановки в лексикографическом порядке, каждую перестановку - в отдельной строке. Числа в перестановках должны разделяться одиночными пробелами.
//
//Пример входных данных
//3
//
//Пример выходных данных
//1 2 3
//1 3 2
//2 1 3
//2 3 1
//3 1 2
//3 2 1
//
//В качестве ответа на задание выберите перестановку для nn = 7 с номером 4468 в лексикографическом порядке. Для примера из условия перестановка с номером 4 имеет вид “2 3 1”.

public class Task2 {

    private static int linecount = 0;
    public static void main(String[] args) {

        int n = 7; //длина строки

        char[] array = new char[n];
        rec(0, array);
    }

    private static void rec(int index, char[] array) {
        String alphabet = "1234567";
//        String alphabet = "123";

        for(int i=0; i<alphabet.length(); i++){
            char ch = alphabet.charAt(i);
            if (String.copyValueOf(array).substring(0, index).indexOf(ch) < 0) {
                array[index] = ch;
            } else {
                continue;
            }

            if (index + 1 < array.length) {
                rec(index + 1, array);
            } else {
                linecount++;
                if(linecount==4468){
                    System.out.println(linecount +"\t"+ String.copyValueOf(array));
                }
            }
        }
    }
}
