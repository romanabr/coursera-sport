package week3.coins;

import common.Utils;
import org.junit.Test;

public class CoinsDinamic {

    @Test
    public void coins1dynamic() {
        int sum = 39;
        int[] coins = {1, 2, 5, 10};

        dinamic(coins, sum);
    }

    private void dinamic(int[] coins, int sum) {
        int[][] d = new int[coins.length][];
        d[0] = new int[sum];

        //инициализация для первой монетки
        for (int i = 0; i < sum; i++) {
            int curSum = i + 1;
            d[0][i] = curSum % coins[0] == 0 ? curSum / coins[0] : 0;
        }

        //инициализация для первой суммы
        for (int j = 1; j < coins.length; j++) {
            d[j] = new int[sum];
            d[j][0] = coins[j] == 1 ? 1 : 0;
        }

        //считаем динамику
        for (int i = 1; i < sum; i++) { //i - index суммы
            for (int j = 1; j < coins.length; j++) {

//                d[i][j] = Math.min();
            }
        }

        Utils.showArray(d);
    }
}