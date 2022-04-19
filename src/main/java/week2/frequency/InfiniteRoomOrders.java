package week2.frequency;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InfiniteRoomOrders {

    @Test
    public void test1() throws IOException {

        Assert.assertEquals(2, process("src/main/java/week2/frequency/inf-orders.txt"));
    }

    @Test
    public void test2() throws IOException {

        Assert.assertEquals(50038, process("src/main/java/week2/frequency/inf-orders-2.txt"));
    }

    public int process(String path) throws IOException {
        List<Order> orders = readFile(path);
//        orders.forEach(this::show);

        AtomicInteger maxRoom = new AtomicInteger();
        int maxTo = orders.stream().mapToInt(o -> o.to).max().orElse(0);
        System.out.println("maxTo = " + maxTo);
        final int[] schedule = new int[maxTo+1];
        orders.forEach(order -> {
            for (int i = order.from; i <= order.to ; i++) {
                schedule[i]++;
                maxRoom.set(Math.max(maxRoom.get(), schedule[i]));
            }
        });

//        Arrays.stream(schedule).forEach(System.out::println);
        System.out.println("maxRoom = " + maxRoom);
        return maxRoom.get();
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

    class Order {

        public Order(int from, int to) {
            this.from = from;
            this.to = to;
        }

        int from;
        int to;
    }
}
