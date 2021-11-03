package week2.orders;

//Задача о выборе заявок. 
//    Имеется аудитория и nn заявок на проведение занятий в ней. Каждая заявка - это временной интервал [l , r),
//    причем левая граница включается в интервал, а правая - нет. Нужно выбрать наибольшее число заявок, чтобы они не пересекались по времени.
//Входные данные
//    В первой строке задано целое число n - количество заявок. 
//    Следующие n строк описывают заявки: i-я строка содержит два целых числа - l и r
//
//Выходные данные
//    Выведите одно целое число - наибольшее число заявок, которые можно удовлетворить.

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Orders {

    class Order {

        public Order(int from, int to) {
            this.from = from;
            this.to = to;
        }

        int from;
        int to;
    }

    @Test
    public void test0() throws IOException {

        List<Order> orders = readFile("src/main/java/week2/orders0.input.txt");

        assertEquals(2, findOrdersByTo(orders));
        assertEquals(2, findOrdersByFrom(orders));
        assertEquals(2, findOrdersShortest(orders));

    }

    @Test
    public void test1() throws IOException {

        List<Order> orders = readFile("src/main/java/week2/orders1.input.txt");

        assertEquals(4, findOrdersByTo(orders));
        assertEquals(2, findOrdersByFrom(orders));
        assertEquals(4, findOrdersShortest(orders));
    }

    @Test
    public void test2() throws IOException {

        List<Order> orders = readFile("src/main/java/week2/orders2.input.txt");

        assertEquals(372, findOrdersByTo(orders));
        assertEquals(5, findOrdersByFrom(orders));
        assertEquals(367, findOrdersShortest(orders));

    }

    private int findOrdersByTo(List<Order> orders) {
        System.out.println("orders.size() = " + orders.size());

        //сортируем по дню завершения заявки
        orders = orders.stream()
                .sorted(Comparator.comparing(o -> o.to))
//                .peek(this::show)
                .collect(Collectors.toList());


        int min = 0;
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (min < o.from) {
                result.add(o);
                min = o.to;
            }
        }
        System.out.println("result.size() = " + result.size());
        result.forEach(this::show);
        return result.size();
    }

    private int findOrdersByFrom(List<Order> orders) {

        //сортируем по дню завершения заявки
        orders = orders.stream()
                .sorted(Comparator.comparing(o -> o.from))
//                .peek(this::show)
                .collect(Collectors.toList());
        int max = -1;
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.from > max) {
                result.add(o);
                max = o.to;
            }
        }
        System.out.println("result.size() = " + result.size());
        result.forEach(this::show);
        return result.size();
    }

    private int findOrdersShortest(List<Order> orders) {

        //сортируем по дню завершения заявки
        orders = orders.stream()
                .sorted((o1, o2) -> {
                    int d1 = o1.from - o1.to + 1;
                    int d2 = o2.from - o2.to + 1;

                    if (d1 == d2) {
                        return Integer.compare(o1.from, o2.from);
                    } else {
                        return -1*Integer.compare(d1, d2);
                    }
                })
                .peek(this::show)
                .collect(Collectors.toList());

        int maxDay = orders.stream().mapToInt(o-> o.to).max().orElse(0);
        boolean[] timeline = new boolean[maxDay];

        List<Order> result = new ArrayList<>();
        for(Order o: orders){

            boolean empty = true;
            for (int i = o.from; i < o.to; i++) {
                if(timeline[i]){
                    empty = false;
                    break;
                }
            }

            if(empty){
                for (int i = o.from; i < o.to; i++) {
                    timeline[i] = true;
                }
                result.add(o);
            }
        }

        System.out.println("result.size() = " + result.size());
        result.forEach(this::show);
        return result.size();
    }


    private List<Order> readFile(String sourcePath) throws IOException {
        return Files.lines(Path.of(sourcePath)).skip(1).map(line -> {
            String[] split = line.split("\s");
            return new Order(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1);
        }).collect(Collectors.toList());
    }

    private void show(Order order) {
        System.out.println("[" + order.from + ", " + order.to + "]: " + (order.to - order.from + 1));
    }
}
