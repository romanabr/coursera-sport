package week4.friends;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static common.Utils.natural;

public class Friends {

    private static final Logger logger = LoggerFactory.getLogger(Friends.class);

    //массив масок, номер маски соответсвует номеру человека,
    // каждый бит маски соответсвует связи этого человека с человеком под номером бита
    private final int[] friends;

    public Friends(int peoplesCount, int[][] links) {
        friends = new int[peoplesCount];

        //инициализируем связи
        for (int[] link : links) {
            addFriend(link[0] - 1, link[1] - 1);
            addFriend(link[1] - 1, link[0] - 1);
        }

        IntStream.range(0, friends.length).forEach(i -> {
            addFriend(i, i); //добавляем в маску самого человека
            logger.info("{} {}", natural(i), Integer.toBinaryString(friends[i])); //вывод содержимого кеша со связями
        });
    }

    private void addFriend(int personNumber, int friendNumber) {
        int mask = friends[personNumber];
        friends[personNumber] = mask | 1 << friendNumber;
    }

    public List<String> solve() {
        Set<List<Integer>> results = new HashSet<>();
        for (int p = 0; p < friends.length; p++) {

            //возвращает массив номеров друзей, например: [2, 1, 3, 4]
            int[] group = getFriendsOfPerson(p);

//            int[] groupArray = group.stream().mapToInt(Integer::intValue).toArray();
            logger.info("person: {}, group: {}", natural(p), Arrays.stream(group)
                    .mapToObj(i -> "" + natural(i)).collect(Collectors.joining(" ")));

            List<Integer> bestNodes = testGroup(group);
            results.add(bestNodes);

            logger.info("bestNodes: {}\t size: {}", bestNodes.stream()
                    .map(Utils::natural)
                    .map(Object::toString)
                    .collect(Collectors.joining("-")), bestNodes.size());
        }

        int maxLen = results.stream().mapToInt(List::size).max().orElse(0);
        List<List<Integer>> maxLenResults = results.stream()
                .filter(list -> list.size() == maxLen)
                .collect(Collectors.toList());

        logger.info("Max company size: {}", maxLen);
        logger.info("Results: ");

        List<String> result = maxLenResults.stream()
                .map(list -> list.stream().map(Utils::natural).map(Object::toString).collect(Collectors.joining("-")))
                .collect(Collectors.toList());
        result.forEach(s -> logger.info("\t {}",s));
        return result;

    }


    private int[] getFriendsOfPerson(int p) {

        //если этот человек в друзьях у текущего человека
        return IntStream.range(0, friends.length).filter(f -> (1 << f & friends[p]) > 0).toArray();
    }

    //пример группы [2, 4, 6] где элемент равен номеру человека
    private List<Integer> testGroup(int[] group) {
        int max = 0;
        List<Integer> bestNodes = Collections.emptyList();
        for (int mask = 0; mask < 1 << group.length; mask++) {
            //Для каждой из возможных комбинаций людей в группе находим максимальное количество полных друзей
            List<Integer> nodes = new ArrayList<>();
            for (int n = 0; n < group.length; n++) {
                if ((1 << n & mask) > 0) {
                    nodes.add(group[n]);
                }
            }

            //для группы [2, 4, 6], начальная маска должна быть 101010
            //строим начальную маску для этой группы
            int result = 0;
            for (int n : nodes) {
                result = result | 1 << n;
            }

            //пересекаем по И маски всех друзей
            for (int n : nodes) {
                result = result & friends[n];
            }

            //вычисляем кол-во общих битов в результирующей маске
            int count = bitsCount(result, friends.length);
            if (count > max) {
                max = count;
                bestNodes = nodes;
            }

            logger.debug("mask: {} {}, count: {}, result:{}, nodes:{}", mask, Integer.toBinaryString(mask),
                    count, Integer.toBinaryString(result),
                    nodes.stream().map(i -> "" + (i + 1)).collect(Collectors.joining("-")));
        }
        return bestNodes.stream().sorted().collect(Collectors.toList());
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
}
