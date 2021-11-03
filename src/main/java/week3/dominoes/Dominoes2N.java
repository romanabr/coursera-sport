package week3.dominoes;

//Замощение полосы доминошками.
// Имеется полоса размера 2×n. Нужно найти число способов замостить ее доминошками размера 1×2.
// Доминошки не должны пересекаться и выходить за пределы полосы.
// Поскольку ответ может быть достаточно большим, требуется вывести его по модулю m (остаток от деления ответа на m).
//Входные данные
//    Натуральные числа n и m.
//Выходные данные
//    Одно целое число - ответ по модулю m.
//
//Пример входных данных
//  4 4
//
//Пример выходных данных
//  1
//Количество замощений для примера равно 5, остаток от деления 5 на 4 равен 1. Введите ответ для n = 42, m = 100.


import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class Dominoes2N {

    int[] unitD2 = {1, 2};
    int[] unitD3 = {1, 3};

    @Test
    public void show2dStatistics() {

        for (int length = 1; length <= 10; length++) {
            System.out.println("---------- " + length + " ------------");
            enumerationRec(0, 0, length, new int[length], new AtomicInteger(), unitD2);
        }
    }

    @Test
    public void show3dStatistics() {
        for (int length = 1; length <= 10; length++) {
            System.out.println("---------- " + length + " ------------");
            enumerationRec(0, 0, length, new int[length], new AtomicInteger(), unitD3);
        }
    }

    @Test
    public void test2d() {
        assertEquals(8, countRec(0, 5, unitD2) );
        assertEquals(13, countRec(0, 6, unitD2) );
        assertEquals(21, countRec(0, 7, unitD2) );
    }

    @Test
    public void testDinamic(){
        countRec(0,10, unitD2);
    }

    @Test
    public void testDinamicWithCache(){

        int number = 10;
        int[] cache = IntStream.range(0, number).map(i -> -1).toArray();

        countRecWithCache(0,number, unitD2, cache);
    }

    private void enumerationRec(int i, int currentSum, int maxSum, int[] array, AtomicInteger resultCounter, int[] unit) {
        for (int n : unit) {
            array[i] = n;
            if (currentSum + n == maxSum) {
                printResult(array, i + 1, resultCounter.incrementAndGet());
            } else if (currentSum + n < maxSum) {
                enumerationRec(i + 1, currentSum + n, maxSum, array, resultCounter, unit);
            }
        }
    }

    private int countRec(int sum, int maxSum, int[] unit) {
        int count = 0;
        for (int n : unit) {
            int currentSum = sum + n;
            if (currentSum == maxSum) {
                count++;
            } else if (currentSum < maxSum) {
                count += countRec(currentSum, maxSum, unit);
            }
        }
        System.out.println("countRec(" + sum + "): " + count);
        return count;
    }

    private int countRecWithCache(int sum, int maxSum, int[] unit, int[] cache) {

        if (cache[sum] > -1) {
            return cache[sum];
        } else {
            int count = 0;
            for (int n : unit) {
                int currentSum = sum + n;
                if (currentSum == maxSum) {
                    count++;
                } else if (currentSum < maxSum) {
                    count += countRecWithCache(currentSum, maxSum, unit, cache);
                }
            }
            cache[sum] = count;
            System.out.println("countRec(" + sum + "): " + count);
            return count;
        }
    }

    void printResult(int[] array, int length, int index) {
        System.out.println(index + "\t" + Arrays.stream(array)
                .limit(length)
                .mapToObj(e -> "" + e)
                .collect(Collectors.joining("")));
    }

    @Test
    public void sample0(){
        int n = 4;
        int m = 4;

        int[] cache = IntStream.range(0, n).map(i -> -1).toArray();
        int result = countRecWithCache(0, n, unitD2, cache);

        Assert.assertEquals(1, result%m);
    }

    @Test
    public void sample1(){
        int n = 42;
        int m = 100;
        int[] cache = IntStream.range(0, n).map(i -> -1).toArray();
        int result = countRecWithCache(0, n, unitD2, cache);

        System.out.println(Integer.MAX_VALUE);

        Assert.assertEquals(37, result%m);
    }

    @Test
    public void sample1fib(){
        int n = 42;
        int m = 100;
        BigInteger result = fib(n);
        Assert.assertEquals(37, result.divideAndRemainder(BigInteger.valueOf(m))[1].intValue());
    }


    //Используя решение предыдущей задачи “Замощение полосы доминошками”, найдите ответ для n = 100000, m = 1000000000.
    @Test
    public void sample2(){
        int n = 100000;
        int m = 1000000000;
        int[] cache = IntStream.range(0, n).map(i -> -1).toArray();
        int result = countRecWithCache(0, n, unitD2, cache);

        System.out.println(Integer.MAX_VALUE);

        Assert.assertEquals(37, result%m);
    }

    @Test
    public void sample2fib(){
        int n = 100000;
//        int n = 1000;
        int m = 1000000000;
        BigInteger result = fib(n);

        System.out.println("result: " + result);
        Assert.assertEquals(707537501, result.divideAndRemainder(BigInteger.valueOf(m))[1].intValue());
    }



    private BigInteger fib(int n){
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.TWO;

        for (int i = 3; i <= n; i++) {
            BigInteger result = a.add(b);
            a = b;
            b = result;

//            System.out.println(" i: " + i +" res: " + result);
        }

        return b;
    }


}
