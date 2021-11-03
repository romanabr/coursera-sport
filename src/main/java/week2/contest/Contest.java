package week2.contest;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class Contest {

    @Test
    public void test0() throws IOException {
        Result result = process("src/main/java/week2/contest/contest-0.txt");
        Assert.assertEquals(70, result.fine);
        Assert.assertEquals(2, result.taskCount);
    }

    @Test
    public void test1() throws IOException {
        Result result = process("src/main/java/week2/contest/contest-1.txt");

        Assert.assertEquals(446, result.taskCount);
        Assert.assertEquals(7302802, result.fine);
    }

//    @Test
//    public void test2() throws IOException {
//        Assert.assertEquals(49943, process("src/main/java/week2/contest/contest-2.txt"));
//    }

    private Result process(String path) throws IOException {

        List<String> lines = Files.lines(Path.of(path)).collect(Collectors.toList());
        int taskNumber = Integer.parseInt(lines.get(0).split("\s")[0]);
        int contestTime = Integer.parseInt(lines.get(0).split("\s")[1]);

        List<Integer> taskTimes = Arrays.stream(lines.get(1)
                .split("\s"))
                .mapToInt(Integer::parseInt).boxed()
                .sorted()
                //.peek(System.out::println)
                .collect(Collectors.toList());

        System.out.println("task +\t fine  \t taskCount \t time");

//        int fine = 0, taskCount = 0, time = 0;
        Result result = new Result();
        for (Integer task: taskTimes){

            if(result.time + task < contestTime) {
                result.fine += result.time + task;
                result.taskCount++;
                result.time += task;
            }else{
                break;
            }

            System.out.println(task +"\t" + result.fine +"\t"+ result.taskCount + "\t"+ result.time);
//            System.out.println("fine = " + fine);
//            System.out.println("taskCount = " + taskCount);
//            System.out.println("time = " + time);
//
        }

        System.out.println("--------");
        System.out.println("fine = " + result.fine);
        System.out.println("taskCount = " + result.taskCount);
        System.out.println("time = " + result.time);
        return result;
    }

    class Result{
        int fine = 0, taskCount = 0, time = 0;
    }
}
