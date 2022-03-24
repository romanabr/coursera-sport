package week4.bits.test1.salesman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Salesman {

    public void solve(int[][] d) {
        int nodes = d.length - 1;
        Map<String, Integer> cache = new HashMap<>();

        for (int mask = 0; mask < 1 << nodes; mask++) {
            showMaskInfo(mask, nodes);

            //извлекаем из маски города которые уже посетили
            List<Integer> nodesThrow = maskToBitArray2(mask, nodes);

            List<Integer> nodesTo = new ArrayList<>();
            for (int i = 1; i <= nodes; i++) {
                if (!nodesThrow.contains(i)) {
                    nodesTo.add(i);
                }
            }
            if (nodesTo.size() == 0) {
                nodesTo.add(0);
            }

            System.out.println("\t\t" + nodesThrow + " - " + nodesTo);
            for (int nodeTo : nodesTo) {
                System.out.println("\tCost(0, " + nodesThrow + ", " + nodeTo + ") = minimum of");
                int min = Integer.MAX_VALUE;

                if (nodesThrow.size() == 0) {
                    System.out.println("\t\t\t\t\t d[0, " + nodeTo + "]");
                    min = Math.min(min, d[0][nodeTo]);
                }
                for (int nodeThrow : nodesThrow) {
                    List<Integer> sub = minus(nodesThrow, nodeThrow);
                    String costSubKey = costKey(sub, nodeThrow);
                    int costSubValue = cache.get(costSubKey);
                    int dist = d[nodeThrow][nodeTo];
                    min = Math.min(min, costSubValue + dist);
                    System.out.println("\t\t\t\t\t " + costSubKey + "d[" + nodeThrow + ", " + nodeTo + "] = " + costSubValue + " + " + dist + " = " + (costSubValue + dist));
                }

                System.out.println("\t\t" + costKey(nodesThrow, nodeTo) + " = " + min);
                cache.put(costKey(nodesThrow, nodeTo), min);
            }
        }
    }

    private List<Integer> minus(List<Integer> list, Integer value) {
        List<Integer> result = new ArrayList<>(list);
        result.remove(value);

        return result;
    }

    private String costKey(List<Integer> nodesThrow, Integer nodeTo) {
        return "cost(" + nodesThrow + ", " + nodeTo + ")";
    }

    private List<Integer> maskToBitArray2(int mask, int nodes) {
        List<Integer> bits = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            if ((mask & 1 << i) > 0) {
//                System.out.println(i);
                bits.add(i + 1);
            }
        }
        return bits;
    }

    private void showMaskInfo(int mask, int nodes) {
        System.out.println(mask
                + "\t" + Integer.toBinaryString(mask)
                + "\t" + maskToBitArray2(mask, nodes));
    }

}
