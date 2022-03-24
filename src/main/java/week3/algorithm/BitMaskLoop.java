package week3.algorithm;

import org.junit.Test;

public class BitMaskLoop {

    @Test
    public void bitMaskLoop() {
        int[] numbers = {-4, 27, 34, 20, 29, 24, 38, 38, 22, 24};

        for (int mask = 0; mask < (1 << numbers.length); mask++) {

            int sum = 0;
            for (int i = 0; i < numbers.length; i++) {
                if ((mask & (1 << i)) > 0) {
                    sum += numbers[i];
                }
            }
            System.out.println(mask + "\t" + Integer.toBinaryString(mask) + "\t" + sum);

            if (sum == 126) {
                break;
            }
        }
    }
}
