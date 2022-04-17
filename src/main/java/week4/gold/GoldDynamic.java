package week4.gold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static common.Utils.showArray;

public class GoldDynamic {
    private static final Logger logger = LoggerFactory.getLogger(GoldDynamic.class);
    private final int[] data;

    public GoldDynamic(int[] data) {
        this.data = data;
    }

    public int solve() {

        int sumToAchieve = Arrays.stream(data).sum();
        boolean[][] d = new boolean[sumToAchieve + 1][];
        //инициализируем первую левую колонку значениями false
        for (int i = 0; i <= sumToAchieve; i++) {
            d[i] = new boolean[data.length + 1];
            d[i][0] = false;
        }
        //инициализируем первую строку значениями true
        for (int j = 0; j <= data.length; j++) {
            d[0][j] = true;
        }

        for (int i = 1; i <= sumToAchieve; i++) {
            for (int j = 1; j <= data.length; j++) {

                //находясь в клетке d[i][j] мы отвечаем на вопрос:
                //можно ли среди элементов data[0], data[1] ..., data[j-1], data[j] найти подмножество, сумма элементов которого равна i
                //если такое подмноджество существует, то в можно найти подмножество среди элементов data[0], data[1] ..., data[j-1]
                //сумма которого будет равна i-data[i]

                //если в столбце слева уже стоит true, значит текущее i может быть набрано на предыдущем наборе,
                // следовательно добавление в него элементов не помешает набрать i,
                // поэтому мы в последующие ячейки записываем true

                int prevIndex = i - data[j - 1];
                if (prevIndex >= 0) {
                    if (d[prevIndex][j - 1] || d[i][j - 1]) {
                        d[i][j] = true;
                    }
                } else if (d[i][j - 1]) {
                    d[i][j] = true;
                }
            }
        }

        String dataStr = Arrays.stream(data).mapToObj(Objects::toString).collect(Collectors.joining(" "));
        logger.info("data: {}", dataStr);
        showArray(d);


        int middle = sumToAchieve / 2;
        int minSumDown = middle;
        for (int i = middle; i > 0; i--) {
            if (d[i][data.length]) {
                minSumDown = i;
                break;
            }
        }

        int minSumUp = sumToAchieve;
        for (int i = middle; i<=sumToAchieve; i++) {
            if (d[i][data.length]) {
                minSumUp = i;
                break;
            }
        }

        int minDifDown = Math.abs(sumToAchieve - 2 * minSumDown);
        int minDifUp = Math.abs(sumToAchieve - 2 * minSumUp);

        logger.info("minSumDown: {}, minDifDown: {}", minSumDown , minDifDown);
        logger.info("minSumUp: {}, minDifUp: {}", minSumUp , minDifUp);

        return Math.min(minDifDown, minDifUp);
    }

}
