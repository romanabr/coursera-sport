package week1;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Task2_6_DoubleBraces {

    private static final String alphabet = "()[]";

    private boolean isCorrect(String line) {

        int b1 = 0; //balance ()
        int b2 = 0; //balance []

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '(') {
                b1++;

                if (line.length() > i + 1 && line.charAt(i + 1) == ']') return false;


            } else if (ch == ')') {
                b1--;
            } else if (ch == '[') {
                b2++;
                if (line.length() > i + 1 && line.charAt(i + 1) == ')') return false;

            } else if (ch == ']') {
                b2--;
            }

            if (b1 < 0 || b2 < 0) {
                return false;
            }

        }

        return b1 == 0 && b2 == 0;
    }

    @Test
    public void isCorrectTest() {
        Assert.assertFalse(isCorrect("([)]"));
    }

    @Test
    public void task2_6() {
        String input = """
                ()()[[[]()]]([()][][()[]])[]()()
                [[]](()()[[[]]][]()()()[()])()]
                []()[](((()]))(()()[][])[]([])[]
                [[]][()[)[]([][]]()][]()()(())()
                (()[([][]())[()][()][][]])([])()
                """;

        Arrays.stream(input.split("\n"))
                .forEach(s -> System.out.println("isCorrect(s) = " + s + " : " + isCorrect(s)));
    }

    @Test
    public void task2_7() {
        int n = 3;
        char[] array = new char[2 * n];
        AtomicInteger lineCount = new AtomicInteger();

        process(0, array, lineCount);

        System.out.println("list = " + list.size());

        list.stream().sorted(Comparator.naturalOrder()).forEach(System.out::println);
    }

    List<String> list = new ArrayList<>();

    private void process(int index, char[] array, AtomicInteger lineCount) {

        for (int i = 0; i < alphabet.length(); i++) {
            char ch = alphabet.charAt(i);
            array[index] = ch;

            if (index + 1 < array.length) {
                process(index + 1, array, lineCount);
            } else {
                String result = String.valueOf(array);
                if (isCorrect(result)) {
                    int count = lineCount.incrementAndGet();
//                    if (count == 8233 || count == 8232|| count == 8234)
                        System.out.println(count + ": " + result);
                        list.add(result + " "+ count);
                }
            }

        }
    }
}

//
//1: ((()))         ((())) 1
//2: (()())         (()()) 2
//3: (())()         (())() 3
//4: (())[]         (())[] 4
//5: (()[])         (()[]) 5
//6: (([]))         (([])) 6
//7: ()(())         ()(()) 7
//8: ()()()         ()()() 8
//9: ()()[]         ()()[] 9
//10: ()([])        ()([]) 10
//11: ()[()]        ()[()] 11
//12: ()[[]]        ()[[]] 12
//13: ()[]()        ()[]() 13
//14: ()[][]        ()[][] 14
//15: ([())]        ([())] 15
//16: ([()])        ([()]) 16
//17: ([[])]        ([[])] 17
//18: ([[]])        ([[]]) 18
//19: ([]())        ([]()) 19 <--
//20: ([])()        ([])() 20
//21: ([])[]        ([])[] 21
//22: ([][])        ([][]) 22 <--
//23: [(())]        [(())] 23 <--
//24: [(()])        [(()]) 24
//25: [()()]        [()()] 25 <--
//26: [()[]]        [()[]] 26
//27: [()]()        [()]() 27
//28: [()][]        [()][] 28
//29: [([])]        [([])] 29
//30: [([]])        [([]]) 30
//31: [[()]]        [[()]] 31
//32: [[[]]]        [[[]]] 32
//33: [[]()]        [[]()] 33
//34: [[][]]        [[][]] 34
//35: [[]]()        [[]]() 35
//36: [[]][]        [[]][] 36
//37: [](())        [](()) 37
//38: []()()        []()() 38
//39: []()[]        []()[] 39
//40: []([])        []([]) 40
//41: [][()]        [][()] 41
//42: [][[]]        [][[]] 42
//43: [][]()        [][]() 43
//44: [][][]        [][][] 44