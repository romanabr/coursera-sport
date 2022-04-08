package week3.arithmetic;

import common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExprDynamic {
    private static final Logger log = LoggerFactory.getLogger(ExprDynamic.class);
    private int[] numbers;

    public ExprDynamic(int[] numbers) {
        this.numbers = numbers;
    }

    public String solve() {
        log.info("numbers: {}", Arrays.stream(numbers).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
        return "";
    }
}
