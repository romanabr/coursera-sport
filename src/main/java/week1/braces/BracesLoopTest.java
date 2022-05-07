package week1.braces;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static week1.braces.BracesLoop.checkResult;

public class BracesLoopTest {
    @Test
    public void testCheck() {

        Object[][] data = {
                {"[]", true},
                {"[][]", true},
                {"()", true},
                {"()()", true},
                {"(())", true},
                {"[[]]", true},
                {"[()]", true},
                {"[(]]", false},
                {"()[()]", true},
                {"[(([]()))]", true},

                {"[[[[()", false},
                {"[[[[[]", false},
                {"[[[][[", false},
                {"[[(()])]", false},
                {"[([(()]))]", false},
                {"([)]", false},
        };

        for (Object[] sample : data) {
            Boolean expected = (Boolean) sample[1];
            String string = (String) sample[0];

            assertEquals(string + " expected to be correct: " + expected,
                    expected, checkResult(string.toCharArray()));
        }
    }

    @Test
    public void test2() {
        assertEquals("(())", new BracesLoop(2, 1).start());
        assertEquals("[[]]", new BracesLoop(2, 6).start());
    }

    @Test
    public void test3() {
        assertEquals("([][])",  new BracesLoop(3, 20).start());
    }

    @Test
    public void test7() {
        assertEquals("(([]())([]))()",  new BracesLoop(7, 8233).start());
    }

}
