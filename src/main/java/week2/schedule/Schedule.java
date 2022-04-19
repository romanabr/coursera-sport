package week2.schedule;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Schedule {

//    Задача о расписании. У программиста есть nn заказов. Для каждого заказа известны дедлайн d_{i} - срок,
//    до которого нужно выполнить заказ, и стоимость заказа c_{i}. На выполнение каждого заказа требуется один день.
//    Программист начинает работать с 1-го дня, и если у заказа дедлайн d_{i}, то этот заказ нужно выполнить
//    до конца d_{i}-го дня. Например, если дедлайн равен 1, то программист успеет выполнить этот заказ в первый день.
//    Нужно составить оптимальное расписание - определить, какие заказы программист может выполнять и в каком порядке,
//    чтобы максимизировать суммарную стоимость выполненных заказов.
//
//    Входные данные:
//        В первой строке задано число n - количество заказов. Следующие n строк содержат описания заказов:
//        ii-я строка содержит два целых числа - дедлайн d_{i} и стоимость c_{i}.
//
//    Выходные данные
//        Выведите одно целое число - максимальную суммарную стоимость выполненных заказов.
//
//    Пример входных данных:
//        5
//        2 5
//        2 4
//        5 3
//        1 2
//        3 1
//
//    Пример выходных данных:
//        13

    static class Task {
        Integer deadline;
        Integer price;

        public Task(Integer deadline, Integer price) {
            this.deadline = deadline;
            this.price = price;
        }
    }

    @Test
    public void sample1() {

        // дедлайн - стоимость
        int[][] input1 = {{2, 5}, {2, 4}, {5, 3}, {1, 2}, {3, 1}};
        long sum = func(input1);
        Assert.assertEquals(13, sum);
    }

    @Test
    public void sample2() {

        // дедлайн - стоимость
        int[][] input2 = {{2, 92}, {2, 80}, {1, 66}, {7, 55}, {5, 55}, {5, 38}, {4, 35}, {7, 19}, {7, 16}, {5, 2}};
        long sum = func(input2);
        Assert.assertEquals(374, sum);
    }

    @Test @Ignore
    public void testRegexp(){
        String input = "1 3 66";
        String[] split = input.split("\s");
        System.out.println(split);
    }

    @Test
    public void sample3() throws IOException {

        int[][] input = Files.lines(Path.of("src/main/java/week2/schedule/schedule2.input.txt")).skip(1)
//                .peek(line -> System.out.println(line))
                .map(s -> Arrays.stream(s.split("\s")).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);

        long sum = func(input);
        Assert.assertEquals(2305658251934L, sum);
    }

    private long func(int[][] input){
        List<Task> taskList = Arrays.stream(input)
                .map(array -> new Task(array[0], array[1]))
                .sorted((t1, t2) -> (!t1.deadline.equals(t2.deadline)) ? t2.deadline.compareTo(t1.deadline) : t2.price.compareTo(t1.price))
                .collect(Collectors.toList());

        int maxDeadline = taskList.stream().mapToInt(t -> t.deadline).max().orElse(0);

        long[] result = new long[maxDeadline];
        for (int i = result.length - 1; i >= 0; i--) {

            int day = i + 1;
            Optional<Task> bestTask = taskList.stream().filter(t -> t.deadline >= day).max(Comparator.comparing(t -> t.price));

            if (bestTask.isPresent()) {
                result[i] = bestTask.get().price;
                taskList.remove(bestTask.get());
            }
        }

        String resultString = Arrays.stream(result).mapToObj(i -> "" + i).collect(Collectors.joining("-"));

        long sum = Arrays.stream(result).sum();
        System.out.println(sum + "\t"+ resultString );
        return sum;
    }
}
