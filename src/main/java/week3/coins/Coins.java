package week3.coins;

//Требуется набрать заданную сумму ss наименьшим числом монет номиналами a1, a2, ... an
//Можно использовать любое число монет каждого номинала.
//
//Входные данные
//    В первой строке заданы два натуральных числа n и s.
//    Следующая строка содержит n различных натуральных чисел - номиналы монет.
//
//Выходные данные
//    Выведите одно целое число - наименьшее число монет, которыми можно набрать сумму s.
//
//Пример входных данных:
//    4 39
//    1 2 5 10
//
//Пример выходных данных:
//    6
//
//Введите ответ на тест в файле change.in.
//        20 100000
//        1 6 7 10 22 23 25 36 37 59 60 61 79 80 101 139 289 355 357 429

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Coins {

    private AtomicLong cacheHit = new AtomicLong();
    private AtomicLong dynamicCalc = new AtomicLong();

    @Test
    public void coins1dynamic() {
        int sum = 39;
        int[] coins = {1, 2, 5, 10};
        int[] cache = new int[sum + 1];

        for (int i = 1; i <= sum; i++) {
            cache[i] = dynamic(i, cache, coins);
        }

        showCache(cache);

        System.out.println("cacheHit = " + cacheHit);
        System.out.println("dynamicCalc = " + dynamicCalc);
        Assert.assertEquals(6, cache[sum]);
    }


    @Test
    public void coins2dynamic() {
        int sum = 100000;
        int[] coins = {1, 6, 7, 10, 22, 23, 25, 36, 37, 59, 60, 61, 79, 80, 101, 139, 289, 355, 357, 429};
        int[] cache = new int[sum + 1];

        for (int i = 1; i <= sum; i++) {
            cache[i] = dynamic(i, cache, coins);
        }

        showCache(cache);

        System.out.println("cacheHit = " + cacheHit);
        System.out.println("dynamicCalc = " + dynamicCalc);
        Assert.assertEquals(235, cache[sum]);

    }

    private int dynamic(int sum, int[] cache, int[] coins) {

        if(cache[sum]>0){
            cacheHit.incrementAndGet();
            return cache[sum];
        }

        dynamicCalc.incrementAndGet();
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (sum - coin > 0) {
                min = Math.min(min, dynamic(sum - coin, cache, coins) + 1);
            } else if (sum - coin == 0) {
                min = Math.min(min, 1);
            } else { //монета больше суммы
                //мин не обновляем
            }
        }
        return min;
    }




    @Test
    public void coins1dynamicWithCertificate() {
        int sum = 39;
        int[] coins = {1, 2, 5, 10};
        Map<Integer, List<Integer>> cache = new HashMap<>();

        for (int i = 1; i <= sum; i++) {
            List<Integer> result = dynamic(i, cache, coins);
            cache.put(i, result);
            System.out.println(i + "\t" + result.stream().map(Object::toString).collect(Collectors.joining("+")));
        }
        Assert.assertEquals(6, cache.get(sum).size());
    }

    @Test
    public void coins2dynamicWithCertificate() {
        int sum = 100000;
        int[] coins = {1, 6, 7, 10, 22, 23, 25, 36, 37, 59, 60, 61, 79, 80, 101, 139, 289, 355, 357, 429};
        Map<Integer, List<Integer>> cache = new HashMap<>();

        for (int i = 1; i <= sum; i++) {
            List<Integer> result = dynamic(i, cache, coins);
            cache.put(i, result);
            System.out.println(i + "\t" + result.stream().map(Object::toString).collect(Collectors.joining("+")));
        }
        Assert.assertEquals(235, cache.get(sum).size());
    }

    private List<Integer> dynamic(int sum, Map<Integer, List<Integer>>  cache, int[] coins) {

        if (cache.get(sum) != null) {
            cacheHit.incrementAndGet();
            return cache.get(sum);
        }

        int min = Integer.MAX_VALUE;
        List<Integer> result = null;
        for (int coin : coins) {
            List<Integer> list = new ArrayList<>();
            list.add(coin);

            if (sum - coin > 0) {
                list.addAll(dynamic(sum - coin, cache, coins));
            } else if (sum - coin == 0) {
                //в списке одна монета
            } else { //монета больше суммы
                //мин не обновляем
                continue;
            }

            if (list.size() < min) {
                min = list.size();
                result = list;
            }

        }
        return result;
    }

    public static void showCache(int[] cache){
        for (int i = 0; i < cache.length; i++) {
            System.out.println(i +": " + cache[i]);
        }
    }
}
