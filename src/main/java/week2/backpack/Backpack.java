package week2.backpack;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//Непрерывная задача о рюкзаке.
//        Вор пробрался на склад, на котором есть nn предметов.
//        Для каждого предмета известны его вес w_{i}w и стоимость c_{i}c.
//        У вора есть рюкзак, который вмещает суммарный вес не более WW.
//        Вор может делить предметы на части, причем стоимость каждой части пропорциональна стоимости предмета.
//        Помогите вору набрать в рюкзак предметы максимальной суммарной стоимости.
//
//Входные данные
//
//    В первой строке содержатся два целых числа - nn и WW.
//        Следующие nn строк содержат описания предметов, каждый предмет задан в отдельной строке.
//        Предмет описывается двумя натуральными числами - весом w_{i}w и стоимостью c_{i}c
//. Гарантируется, что отношение c_{i} / w_{i}c для каждого предмета - целое число.

//Выходные данные
//        Выведите одно число - максимальную суммарную стоимость предметов в рюкзаке.
//        Ограничения задачи гарантируют, что это число целое

// вес - стоимость

public class Backpack {

    @Test
    public void backpack0() throws IOException {

        Assert.assertEquals(18, (int)backpack("src/main/java/week2/backpack/backpack0.txt"));
    }

    @Test
    public void backpack1() throws IOException {
        Assert.assertEquals(550, (int)backpack("src/main/java/week2/backpack/backpack1.txt"));
    }

    @Test
    public void backpack2() throws IOException {
        Assert.assertEquals(7909205, (int)backpack("src/main/java/week2/backpack/backpack2.txt"));
    }

    public double backpack(String path) throws IOException {

        List<String> lines = Files.lines(Path.of(path)).collect(Collectors.toList());

        int capacity = Integer.parseInt(lines.get(0).split("\s")[1]);

        List<Thing> things = lines.stream()
                .skip(1)
                .map(line -> {
                    String[] split = line.split("\s");
                    return new Thing(split[0], split[1]);
                })
//                .sorted(Comparator.comparing(thing -> thing.value))
                .sorted((t1, t2) -> -1 * Double.compare(t1.value, t2.value))
                .collect(Collectors.toList());

//        System.out.println(things);
        int currentWeitht = 0;
        double backpackValue = 0;
        for (Thing t : things) {
            if (currentWeitht + t.weight <= capacity) {
                currentWeitht += t.weight;
                backpackValue += t.price;
            } else {
                int weight = capacity - currentWeitht;
                backpackValue += t.value * weight;
                break;
            }
        }
        System.out.println("backpackValue = " + backpackValue);
        return backpackValue;
    }

    private static class Thing {
        int weight = 0;
        int price = 0;
        double value;

        public Thing(int weight, int price, double value) {
            this.weight = weight;
            this.price = price;
            this.value = value;
        }

        public Thing(String count, String price) {
            this.weight = Integer.parseInt(count);
            this.price = Integer.parseInt(price);
            this.value = 1.0 * this.price / this.weight;
        }


    }
}
