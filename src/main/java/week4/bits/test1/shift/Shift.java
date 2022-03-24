package week4.bits.test1.shift;

import org.junit.Test;

public class Shift {

    @Test
    public void task1() {
        int x1 = (31 >> 2);
        int x2 = (1 << 6);
        int x3 = ((31 >> 2) | (1 << 6));

        System.out.println(x1);
        System.out.println(x2);
        System.out.println(x3);
    }


    @Test
    public void task2() {
        int n = 10;
        int x = 0;
        for (int i = 0; i < n; i++) {
            x |= (1 << i);
        }

        System.out.println(x);
        System.out.println(Math.pow(2, n)-1);
    }
}
