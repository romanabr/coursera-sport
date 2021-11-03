package week1;

public class Task2_3_BracesCombinations {

    private static char[] data;
    private static String alphabet = "()";
    private static int lineCount = 1;


    public static void main(String[] args) {
        int n = 10;

        data = new char[2*n];
        rec(0);
    }

    private static void rec(int index) {
        for (int i = 0; i < alphabet.length(); i++) {
            char ch = alphabet.charAt(i);
            data[index] = ch;

            if (index + 1 < data.length) {
                rec(index + 1);
            } else {

                String result = String.valueOf(data);
                if(isCorrect(result)){
                    System.out.println(lineCount ++ + " result = " + result);
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
