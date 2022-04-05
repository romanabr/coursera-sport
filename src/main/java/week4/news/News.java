package week4.news;

import common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week4.friends.Friends;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static common.Utils.natural;
import static common.Utils.showSet;

public class News {
    private static final Logger logger = LoggerFactory.getLogger(Friends.class);

    //массив масок, номер маски соответсвует номеру человека,
    // каждый бит маски соответсвует связи этого человека с человеком под номером бита
    private final int[] friends;
//    private int allPeopleMask;


    public News(int peoplesCount, int[][] links) {
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

//        allPeopleMask = 0;
//        for (int i = 0; i < friends.length; i++) {
//            allPeopleMask = allPeopleMask | 1 << i;
//        }
//        logger.debug("allPeopleMask: {} {}", allPeopleMask, Integer.toBinaryString(allPeopleMask));
    }

    private void addFriend(int personNumber, int friendNumber) {
        int mask = friends[personNumber];
        friends[personNumber] = mask | 1 << friendNumber;
    }

    public List<String> solve() {

        List<List<Integer>> solution = new ArrayList<>();

        for (int mask = 0; mask < 1 << friends.length; mask++) {

            Set<Integer> peopleWhoKnows = new HashSet<>();
            List<Integer> peopleWhoTell = new ArrayList<>();
            for (int person = 0; person < friends.length; person++) {
                if ((mask & 1 << person) > 0) {
                    peopleWhoTell.add(person);
                    int[] friendsOfPerson = getFriendsOfPerson(person);
                    for (int ofPerson : friendsOfPerson) {
                        peopleWhoKnows.add(ofPerson);
                    }
                }
            }
            if (peopleWhoKnows.size() == friends.length) {
                solution.add(peopleWhoTell);
            }

            logger.debug("mask.dec: {}, mask.bin:{}, result.size:{}, result: {}",
                    mask, Integer.toBinaryString(mask), peopleWhoKnows.size(), showSet(peopleWhoKnows));
        }

        int min = solution.stream().mapToInt(List::size).min().orElse(Integer.MAX_VALUE);
        List<List<Integer>> result = solution.stream().filter(list -> list.size() == min).collect(Collectors.toList());

        return result.stream()
                .map(list -> list.stream().sorted()
                        .map(Utils::natural)
                        .map(Objects::toString)
                        .collect(Collectors.joining("-")))
                .collect(Collectors.toList());
    }


    private int[] getFriendsOfPerson(int p) {

        //если этот человек в друзьях у текущего человека
        return IntStream.range(0, friends.length).filter(f -> (1 << f & friends[p]) > 0).toArray();
    }


}
