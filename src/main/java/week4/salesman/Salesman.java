package week4.salesman;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Salesman {

    private int[][] d;
    private Map<String, Integer> cache = new HashMap<>();
    private static final boolean VERBOSE = false;

    private int minDistance;
    private List<String> paths = new ArrayList<>();

    public Salesman(int[][] d) {
        this.d = d;
    }

    public void solve() {
        int nodes = d.length - 1;


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

            if(VERBOSE) System.out.println("\t\t" + nodesThrow + " - " + nodesTo);
            for (int nodeTo : nodesTo) {
                if(VERBOSE) System.out.println("\tCost(0, " + nodesThrow + ", " + nodeTo + ") = minimum of");
                int min = Integer.MAX_VALUE;

                if (nodesThrow.size() == 0) {
                    if(VERBOSE) System.out.println("\t\t\t\t\t d[0, " + nodeTo + "]");
                    min = Math.min(min, d[0][nodeTo]);
                }
                for (int nodeThrow : nodesThrow) {
                    List<Integer> sub = minus(nodesThrow, nodeThrow);
                    String costSubKey = costKey(sub, nodeThrow);
                    int costSubValue = cache.get(costSubKey);
                    int dist = d[nodeThrow][nodeTo];
                    min = Math.min(min, costSubValue + dist);
                    if(VERBOSE) System.out.println("\t\t\t\t\t " + costSubKey + "d[" + nodeThrow + ", " + nodeTo + "] = " + costSubValue + " + " + dist + " = " + (costSubValue + dist));
                }

                if(VERBOSE) System.out.println("\t\t" + costKey(nodesThrow, nodeTo) + " = " + min);
                cache.put(costKey(nodesThrow, nodeTo), min);
            }
        }

        List<Integer> subs = IntStream.range(1, nodes + 1).boxed().collect(Collectors.toList());
        minDistance = cache.get(costKey(subs, 0));

        Integer[] certificate = new Integer[d.length];
        createCertificate(subs, 0, certificate, 0);
    }

    private void createCertificate(List<Integer> nodesThrow, Integer nodeTo, Integer[] certificate, int level) {
        Integer min = cache.get(costKey(nodesThrow, nodeTo));
        //System.out.println("subs: " + nodesThrow + "\t min: " + min);

        for (int nodeThrow : nodesThrow) {
            List<Integer> sub = minus(nodesThrow, nodeThrow);
            String costSubKey = costKey(sub, nodeThrow);
            int dist = cache.get(costSubKey) + d[nodeThrow][nodeTo];

            if (dist == min) {
                certificate[level] = nodeThrow;

                createCertificate(sub, nodeThrow, certificate, level+1);
                if (certificate.length == level + 2) {
                    String cert = Arrays.stream(certificate).limit(level + 1)
                            .map(Object::toString)
                            .collect(Collectors.joining(" "));
                    paths.add("0 " + cert);
                }
            }
        }
    }

    public int getMinDistance() {
        return minDistance;
    }

    public List<String> getPaths() {
        return paths;
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
        if(VERBOSE) {
            System.out.println(mask
                    + "\t" + Integer.toBinaryString(mask)
                    + "\t" + maskToBitArray2(mask, nodes));
        }
    }

}
