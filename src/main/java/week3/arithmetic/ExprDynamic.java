package week3.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static common.Utils.showArray;

public class ExprDynamic {
    private static final Logger log = LoggerFactory.getLogger(ExprDynamic.class);
    private Model model;

    public ExprDynamic(Model model) {
        this.model = model;
    }

    public String solve() {

        int[] numbers = Arrays.copyOf(model.numbers, model.numbers.length);
        numbers[0] = Math.abs(model.numbers[0] - model.sum);


        log.info("numbers: {}", Arrays.stream(numbers).mapToObj(Objects::toString).collect(Collectors.joining(" ")));

        //находим половину
        int sum = Arrays.stream(numbers).sum();
        int halfSum = sum / 2;
        log.info("sum: {} and half: {}", sum, halfSum);
        if (sum % 2 == 1) {
            throw new RuntimeException("Сумма чисел нечетная, sum = " + sum);
        }

        boolean[][] d = new boolean[halfSum + 1][];
        //инициализируем первую левую колонку значениями false
        for (int i = 0; i <= halfSum; i++) {
            d[i] = new boolean[numbers.length + 1];
            d[i][0] = false;
        }
        //инициализируем первую строку значениями true
        for (int j = 0; j <= numbers.length; j++) {
            d[0][j] = true;
        }

        for (int i = 1; i <= halfSum; i++) {
            for (int j = 1; j <= numbers.length; j++) {

                //находясь в клетке d[i][j] мы отвечаем на вопрос:
                //можно ли среди элементов data[0], data[1] ..., data[j-1], data[j] найти подмножество, сумма элементов которого равна i
                //если такое подмноджество существует, то в можно найти подмножество среди элементов data[0], data[1] ..., data[j-1]
                //сумма которого будет равна i-data[i]

                //если в столбце слева уже стоит true, значит текущее i может быть набрано на предыдущем наборе,
                // следовательно добавление в него элементов не помешает набрать i,
                // поэтому мы в последующие ячейки записываем true
                int prevIndex = i - numbers[j - 1];
                if (prevIndex >= 0) {
                    if (d[prevIndex][j - 1] || d[i][j - 1]) {
                        d[i][j] = true;
                    }
                } else if (d[i][j - 1]) {
                    d[i][j] = true;
                }
            }
        }

        log.info("data:\t {}", Arrays.stream(numbers).mapToObj(Objects::toString).collect(Collectors.joining("\t")));
        showArray(d, "\t");

        return generateCertificate(d, numbers);
    }

    private String generateCertificate(boolean[][] d, int[] numbers) {
        //считаем сертификат
        if (!d[d.length - 1][numbers.length - 1]) {
            throw new RuntimeException("Imposible to find numbers which sum would be equals " + (d.length - 1));
        }

        //определяем индексы в массиве исходных чисел составляющих одну группу
        Set<Integer> group = new HashSet<>();
        int level = d.length - 1;
        for (int j = numbers.length - 1; j > 0; j--) {
            if (d[level][j] && !d[level][j - 1]) {
                int number = numbers[j - 1];
                log.debug("j: {}, number[j]: {}", j, number);
                level -= number;
                group.add(j - 1);
            }
        }

        int resultSum = group.stream().mapToInt(i -> numbers[i]).sum();
        log.info("results: {}, resultSum: {}", group, resultSum);

        StringBuilder out = new StringBuilder();
        int actualSum = model.numbers[0];
        out.append(actualSum);
        for (int i = 1; i < numbers.length; i++) {
            int sign = group.contains(i) ? 1 : -1;
            String x = (sign > 0 ? "+" : "-") + numbers[i];
            actualSum += sign * numbers[i];
            out.append(x);

            log.info("x[{}]: {}", i, x);
        }
        log.info("model.sum:{} , actual.sum: {}, exression: {}", model.sum, actualSum, out);

        return out.toString();
    }
}
