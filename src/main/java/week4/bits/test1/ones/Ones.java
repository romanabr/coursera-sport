package week4.bits.test1.ones;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Ones {

    @Test
    public void test0(){
        Assert.assertEquals("2 3 0", ones("5 13 0"));
    }

    @Test
    public void test1(){
        Assert.assertEquals("8 15 16 17 15 16 17 15", ones("1361955 207579012 628145516 376140462 883515280 186969585 762888635 326402539"));
    }

    private String ones(String source) {
        return Arrays.stream(source.split("\s"))
                .map(this::countOnes)
                .collect(Collectors.joining(" "));
    }

    private String countOnes(String n){
        int number = Integer.parseInt(n);
        int count = 0;
        for (int i = 0; i < 4*8-1; i++) {
            if ((number & 1<<i) > 0){
                count++;
            }
        }

        System.out.println(Integer.toBinaryString(number) + "\t" + Integer.bitCount(number) +" \t" + count);

        return ""+count;
    }
}
