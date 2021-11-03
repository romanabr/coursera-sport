package week3.coins;

import org.junit.Assert;
import org.junit.Test;

public class CoinsPlus {

    @Test
    public void coins1dynamic() {
        int sum = 39;
        int[] coins = {1, 2, 5, 10};
        int[] cache = new int[sum + 1];

        for (int i = 1; i <= sum; i++) {
            cache[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin >= 0) {
                    cache[i] = Math.min(cache[i - coin] + 1, cache[i]);
                }
            }
        }

        Coins.showCache(cache);
        Assert.assertEquals(6, cache[sum]);
    }

    @Test
    public void coins1dynamicWithCert() {
        int sum = 39;
        int[] coins = {1, 2, 5, 10};
        int[] cache = new int[sum + 1];
        int[] bestCoins = new int[sum + 1];

        for (int i = 1; i <= sum; i++) {
            cache[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin >= 0 && cache[i - coin] + 1 < cache[i]) {
                    cache[i] = cache[i - coin] + 1; //минимальная сумма + 1
                    bestCoins[i] = coin; //запоминаем монетку с которого начинается минимальное разбиение
                }
            }
        }
        Assert.assertEquals(6, cache[sum]);
        Assert.assertEquals("2+2+5+10+10+10", findAns(sum, bestCoins));

//        System.out.println("Cache: ");
//        Coins.showCache(cache);
//        System.out.println("Last coin: ");
//        Coins.showCache(bestCoins);
    }

    private String findAns(int currentSum, int[] bestCoins) {

        int bestCoin = bestCoins[currentSum];
        String result = "" + bestCoin;
        if (currentSum - bestCoin > 0) {
            result += "+" + findAns(currentSum - bestCoin, bestCoins);
        }

        return result;
    }

}
