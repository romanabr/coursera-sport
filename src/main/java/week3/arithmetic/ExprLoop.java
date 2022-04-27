package week3.arithmetic;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExprLoop {

    @Test
    public void test() {
        int[] numbers = {21, 27, 34, 20, 29, 24, 38, 38, 22, 24};
        int sum = 25;
        Assert.assertEquals("+21-27+34+20-29-24+38+38-22-24",
                processResult(bitMaskLoop(numbers, sum), numbers));
    }

    private int bitMaskLoop(int[] numbers, int totalSum) {

        for (int mask = 0; mask < (1 << numbers.length); mask++) {

            int sum = 0;
            for (int i = 0; i < numbers.length; i++) {
                sum += sign(mask, i) * numbers[i];
            }

            if (sum == totalSum) {
                return mask;
            }
        }

        throw new RuntimeException("Impossible to find correct combination(");
    }

    private int sign(int mask, int bit) {
        return (mask & (1 << bit)) > 0 ? 1 : -1;
    }

    private String processResult(int mask, int[] numbers) {
        return IntStream.range(0, numbers.length)
                .mapToObj(i -> "" + (sign(mask, i) > 0 ? "+" : "-") + numbers[i])
                .collect(Collectors.joining());
    }
}
