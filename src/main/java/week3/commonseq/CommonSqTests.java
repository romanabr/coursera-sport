package week3.commonseq;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonSqTests {

    private static final String TEST0 = "src/main/java/week3/commonseq/seq0.txt";
    private static final String TEST1 = "src/main/java/week3/commonseq/seq1.txt";
    private static final String TEST2 = "src/main/java/week3/commonseq/seq2.txt";

    private List<Integer> read(String file, int skip) throws IOException {

        return Arrays.stream(Files.lines(Path.of(file))
                .skip(skip).limit(1).findFirst().orElse("")
                .split("\s")).map(Integer::valueOf).collect(Collectors.toList());
    }

    @Test
    public void test_read() throws IOException {

        Assert.assertEquals(7, read(TEST0, 1).size());
        Assert.assertEquals(9, read(TEST0, 3).size());
        Assert.assertEquals(20, read(TEST1, 1).size());
        Assert.assertEquals(20, read(TEST1, 3).size());
        Assert.assertEquals(1000, read(TEST2, 1).size());
        Assert.assertEquals(2000, read(TEST2, 3).size());
    }
}
