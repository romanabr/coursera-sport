package week4.bits.test1.friends;

import java.util.*;
import java.util.stream.Collectors;

public class Friends {
    private final int[][] links;
    private final int[] friends;

    public Friends(int peoplesCount, int[][] links) {
        this.links = links;
        friends = new int[peoplesCount];
    }

    public void solve() {
        Set<List<Integer>> results = new HashSet<>();

        prepareCache();
        for (int p = 0; p < friends.length; p++) {

            List<Integer> group = new ArrayList<>();
            group.add(p);
            for (int f = 0; f < friends.length; f++) {
                if ((1 << f & friends[p]) > 0 && p != f) { //если этот человек в друзьях у текущего человека
                    group.add(f);
                }
            }

            int[] groupArray = group.stream().mapToInt(Integer::intValue).toArray();
            System.out.println("person: " + natural(p) + ", group: " + Arrays.stream(groupArray)
                    .mapToObj(i -> "" + natural(i)).collect(Collectors.joining(" ")));

            List<Integer> bestNodes = testGroup(groupArray);
            bestNodes = bestNodes.stream().sorted().collect(Collectors.toList());
            results.add(bestNodes);

            System.out.println("\tbestNodes: " + bestNodes.stream()
                    .map(this::natural)
                    .map(Object::toString)
                    .collect(Collectors.joining("-")) + "\t size: " + bestNodes.size());
        }

        int maxLen = 0;
        for (List<Integer> list : results) {
            maxLen = Math.max(maxLen, list.size());
        }

        List<List<Integer>> maxLenResults = new ArrayList<>();
        for (List<Integer> list : results) {
            if (list.size() == maxLen) {
                maxLenResults.add(list);
            }
        }


        System.out.println("\nMax company size: " + maxLen);
        System.out.println("Results: ");
        maxLenResults.stream()
                .map(list -> list.stream().map(this::natural).map(Object::toString).collect(Collectors.joining("-")))
                .forEach(s -> System.out.println("\t"+s));
    }

    private int natural(int x) {
        return x + 1;
    }

    //пример группы [2, 4, 6] где элемент равен номеру человека
    private List<Integer> testGroup(int[] group) {
        int max = 0;
        List<Integer> bestNodes = Collections.emptyList();
        for (int mask = 0; mask < 1 << group.length; mask++) {
            List<Integer> nodes = new ArrayList<>();
            for (int n = 0; n < group.length; n++) {
                if ((1 << n & mask) > 0) {
                    nodes.add(group[n]);
                }
            }

            //для группы [2, 4, 6], начальная маска должна быть 101010
            int result = 0;
            for (int n : nodes) {
                result = result | 1 << n;
            }

            for (int n : nodes) {
                result = result & friends[n];
            }

            int count = bitsCount(result, friends.length);
            if (count > max) {
                max = count;
                bestNodes = nodes;
            }
//            System.out.println("mask: " + mask + " " + Integer.toBinaryString(mask)
//                    + " count: " + count
//                    + " result: " + Integer.toBinaryString(result)
//                    + " nodes: " + nodes.stream().map(i -> "" + (i + 1)).collect(Collectors.joining("-")));

        }

        return bestNodes;
    }

    private void prepareCache() {
        for (int i = 0; i < friends.length; i++) {
            addFriend(i, i);
        }

        for (int[] link : links) {
            addFriend(link[0] - 1, link[1] - 1);
            addFriend(link[1] - 1, link[0] - 1);
        }

        for (int i = 0; i < friends.length; i++) {
            System.out.println((i + 1) + " " + Integer.toBinaryString(friends[i]));
        }

    }

    private int bitsCount(int value, int maxBitIndex) {
        int count = 0;
        for (int bit = 0; bit < maxBitIndex; bit++) {
            if ((1 << bit & value) > 0) {
                count++;
            }
        }

        return count;
    }


    private void addFriend(int personNumber, int frendNumber) {
        int mask = friends[personNumber];
        friends[personNumber] = mask | 1 << frendNumber;
    }
}
