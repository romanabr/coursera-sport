package week1;

public class Task1 {

//    Выведите в лексикографическом порядке все последовательности длины n,
//    состоящие из чисел от 1 до m.
//    Каждое число может встречаться в последовательности сколько угодно раз или не встречаться совсем.

//    В качестве ответа на задание выберите последовательность для nn = 6, mm = 5,
//    имеющую номер 6659 в лексикографическом порядке.
//    Для примера из условия последовательность с номером 3 имеет вид “2 1”.

//    3 1 4 2 2 4

    private static final String alphabet = "12345";
    private static final char[] vector = new char[6];
    private static int count = 0;

    public static void main(String[] args) {
        rec(0);
    }

    private static void rec(int index) {
        for (int i =0; i<alphabet.length(); i++) {
            vector[index] = alphabet.charAt(i);
            if (index + 1 < vector.length) {
                rec(index + 1);
            } else {
                String result = String.copyValueOf(vector);
                count++;
                if(count==6659){
                    System.out.println(count + ": " + result);
                }
            }
        }
    }

}
