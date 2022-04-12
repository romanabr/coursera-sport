package week4.gold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GoldBrutForce {

    private static final Logger logger = LoggerFactory.getLogger(GoldBrutForce.class);
    private int[] data;

    public GoldBrutForce(int[] data) {
        this.data = data;
    }

    public int solve() {

        int sum = Arrays.stream(data).sum();
        int minDelta = Integer.MAX_VALUE;
        List<Integer> minMasks = new ArrayList<>();

        for (int mask = 0; mask < 1 << data.length; mask++) {

            int sum1 = 0;
            for (int i = 0; i < data.length; i++) {
                if ((mask & 1 << i) > 0) {
                    sum1 += data[i];
                }
            }
            int sum2 = sum - sum1;
            int delta = Math.abs(sum2 - sum1);
            logger.debug("mask: {} - {}, sum1: {}, sum2: {}, delta: {}.",
                    mask, Integer.toBinaryString(mask), sum1, sum2, delta);

            if (delta < minDelta) {
                minDelta = delta;
                minMasks = new ArrayList<>();
                minMasks.add(mask);
            } else if (delta == minDelta) {
                minMasks.add(mask);
            }
        }

        logger.info("solutions:{}, minDelta: {}, sum:{}", minMasks.size(), minDelta, sum);
//        minMasks.forEach(m -> logger.info("mask: {} / {}", m, Integer.toBinaryString(m)));
        minMasks.forEach(this::showMask);

        return minDelta;
    }

    private void showMask(int mask){
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            if ((mask & 1 << i) > 0) {
                list1.add(data[i]);
                list2.add(0);
            }else{
                list2.add(data[i]);
                list1.add(0);
            }
        }

        Function<List<Integer>, String> str = (list)-> list.stream().map(Objects::toString).collect(Collectors.joining("-"));
        String str1 = str.apply(list1);
        String str2 = str.apply(list2);

        Function<List<Integer>, Integer> sum = (list)-> list1.stream().mapToInt(i -> i).sum();
        int s1 = sum.apply(list1);
        int s2 = sum.apply(list2);

        logger.info("mask: {} / {}, delta:{}, left: {}={}, right: {}={}",
                mask, Integer.toBinaryString(mask), Math.abs(s1-s2), str1, s1,  str2, s2);

    }
}
