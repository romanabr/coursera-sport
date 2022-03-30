package week4.bits.test1.friends;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Friends {

    private final int[][] links;
    private final int[] friends;

    public Friends(int peoplesCount, int[][] links) {
        this.links = links;
        friends = new int[peoplesCount];
    }

    public void solve() {
        prepareCache();

        for (int p = 0; p < friends.length; p++) {

            List<Integer> group = new ArrayList<>();
            group.add(p);
            for (int f = 0; f < friends.length; f++) {
                if ((1 << f & friends[p]) > 0 && p != f) { //если этот человек в друзьях у текущего человека
//                    System.out.println("p: " + (p + 1) + ", f: " + (f + 1) + " " + Integer.toBinaryString(friends[f]));
                    group.add(f);
                }
            }


            testGroup(group.stream().mapToInt(Integer::intValue).toArray());
        }
    }

    private void testGroup(int[] group) {

        System.out.println("group: " + Arrays.stream(group).mapToObj(i -> "" + (i + 1)).collect(Collectors.joining(" ")));
        ;

        for (int mask = 0; mask < 1 << group.length; mask++) {
            List<Integer> nodes = new ArrayList<>();
            for (int n = 0; n < group.length; n++) {
                if ((1 << n & mask) > 0) {
                    nodes.add(group[n]);
                }
            }

            if (nodes.size() > 0) {
                int result = friends[nodes.get(0)];
                for (int n : nodes) {
                    result = result & friends[n];
                }


                int count = 0;
                for (int n = 0; n < group.length; n++) {
                    if ((1 << n & result) > 0) {
                        count++;
                    }
                }
                System.out.println("mask: "+mask +" "+Integer.toBinaryString(mask)
                        +" count: " + count
                        + " result: "+ Integer.toBinaryString(result)
                        +" nodes: " + nodes.stream().map(i -> ""+(i+1)).collect(Collectors.joining("-")));
            }
        }
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


    private void addFriend(int personNumber, int frendNumber) {
        int mask = friends[personNumber];
        friends[personNumber] = mask | 1 << frendNumber;
    }

}
