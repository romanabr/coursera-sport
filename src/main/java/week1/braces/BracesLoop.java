package week1.braces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BracesLoop {

    private static final Logger log = LoggerFactory.getLogger(BracesLoop.class);

    char[] dict = "()[]".toCharArray();
    char[] result;
    int indexToGo;
    int resultCounter = 0;
    String resultStr = "";

    public BracesLoop(int n, int indexToGo) {
        this.indexToGo = indexToGo;
        result = new char[2 * n];
    }

    public String start() {
        rec(0);
        return String.valueOf(result);
    }

    private void rec(int index) {

        for (int i = 0; i < dict.length; i++) {
            if (resultCounter >= indexToGo) {
                return;
            }

            result[index] = dict[i];

            if (index + 1 < result.length) {
                rec(index + 1);
            } else {

                if (checkResult(this.result)) {
                    resultCounter++;
                    log.info("{} - correct: {}", resultCounter, String.valueOf(result));
                    if (resultCounter == indexToGo) {
                        resultStr = String.valueOf(result);
                    }
                } else {
                    log.debug("rejected: {}", String.valueOf(result));
                }
            }
        }
    }

    public static boolean checkResult(char[] result) {
        for (int i = result.length - 1; i >= 0; i--) {
            //проверяем правильность скобочной последовательности для каждой закрытой скобки внутри строки
            if (result[i] == ']' || result[i] == ')') {
                boolean b = checkResult(result, i, true);
                if (!b) {
                    return false;
                }
            }
        }

        return checkResult(result, result.length - 1, false);
    }

    //isCheckLocal - определяет режим проверки в локальном диапазоне
    // для строки ((((() в локальном диапазоне функция вернет true
    private static boolean checkResult(char[] result, int from, boolean isCheckLocal) {

        int count1 = 0; //[]
        int count2 = 0; //()

        if (result[from] == ')' || result[from] == ']') {


            for (int i = from; i >= 0; i--) {
                if (result[i] == ']') {
                    count1++;
                } else if (result[i] == '[') {
                    count1--;
                } else if (result[i] == ')') {
                    count2++;
                } else if (result[i] == '(') {
                    count2--;
                }

                log.debug("i: " + i + ", count1: " + count1 + ", count2: " + count2);

                if (count1 < 0 || count2 < 0) {
                    return false;
                }

                if (isCheckLocal) {
                    //если строка заканчивается на ] и скобки [] cбалансированы
                    //результат опеделяется сбалансированы ли скобки друго типа
                    if (result[from] == ']' && count1 == 0) {
                        return count2 == 0;
                    } else if (result[from] == ')' && count2 == 0) {
                        return count1 == 0;
                    }
                }
            }
        } else {
            return false;
        }

        return count1 == 0 && count2 == 0;
    }

}
