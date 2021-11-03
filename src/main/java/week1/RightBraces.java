package week1;

public class RightBraces {

    //Сколько существует правильных скобочных последовательностей из трех открывающихся и трех закрывающихся скобок?
    private static final String alphabet = "()";
    private static final char[] vector = new char[4];
    private static int count = 1;

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
                if(isCorrect(result)) {
                    System.out.println(count++ + ": " + result + " " + isCorrect(String.copyValueOf(vector)));
                }
            }
        }
    }

    private static boolean isCorrect(String s){
        int balance = 0;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == '('){
                balance++;
            }else {
                balance --;
            }

            if(balance<0){
                return false;
            }
        }

        return balance==0;
    }
}