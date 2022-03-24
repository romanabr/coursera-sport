package week4.bits.test1.salesman;

import org.junit.Test;

public class SalesmanTest {

    int[][] d0 = {
            {0, 1, 4, 6},
            {1, 0, 5, 2},
            {4, 5, 0, 3},
            {6, 2, 3, 0}};

    int[][] d1 = {
            {0, 12, 10, 19, 8},
            {12, 0, 3, 7, 6},
            {10, 3, 0, 2, 20},
            {19, 7, 2, 0, 4},
            {8, 6, 20, 4, 0}
    };

    @Test
    public void test0(){
        new Salesman().solve(d0);
    }

    @Test
    public void test2() {
        new Salesman().solve(d1);
    }
}
