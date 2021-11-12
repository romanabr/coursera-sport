package week3.binom;

import common.Utils;
import org.junit.Assert;
import org.junit.Test;

public class Binom {

    public int binom_recursive(int k, int n, int level) {
//        System.out.println("..".repeat(level) + "-> c(" + k + ", " + n + ")");
        int result = 0;
        if (n == k || k==0) {
            result = 1;
        } else {
            result = binom_recursive(k - 1, n - 1, level+1) + binom_recursive(k, n - 1, level+1);
        }

//        System.out.println("..".repeat(level)+"<- c(" + k + ", " + n + ") = " + result);
        return result;
    }

    @Test
    public void test_2_3(){
        Assert.assertEquals(3, binom_recursive(2, 3, 0));
    }

    @Test
    public void test_3_4(){
        Assert.assertEquals(4, binom_recursive(3, 4, 0));
    }

    @Test
    public void test_2_6(){
        Assert.assertEquals(15, binom_recursive(2, 6, 0));
    }



    @Test
    public void show_matrix() {
        int K = 10;
        int N = 12;
        int[][] matrix = new int[K][];

        for (int k = 0; k < K; k++) {
            matrix[k] = new int[N];
            for (int n = 0; n < N; n++) {
                if (n >= k) {
                    matrix[k][n] = binom_recursive(k, n, 0);
                }
            }
        }

        Utils.showArray(matrix);
    }

    @Test
    public void test_5_11(){
        int expected = binom_recursive(5, 11, 0);

        long[][] matrix = gen_binom_matrix(5, 11);
        Utils.showArray(matrix);

        Assert.assertEquals(expected, matrix[5][11]);
    }

    private long[][] gen_binom_matrix(int kMax, int nMax){
        long[][] matrix = new long[kMax+1][];

        for (int k = 0; k <= kMax; k++) {
            matrix[k] = new long[nMax+1];
            for (int n = 0; n <= nMax; n++) {
                if (k == 0) {
                    matrix[k][n] = 1;
                } else if (n >= k) {
                    matrix[k][n] = matrix[k][n - 1] + matrix[k - 1][n - 1];
                }
            }
        }

        return matrix;
    }

    @Test
    public void test_20_50(){
        long[][] matrix = gen_binom_matrix(20, 50);
        Utils.showArray(matrix);

        Assert.assertEquals(47129212243960L, matrix[20][50]);
    }
}
