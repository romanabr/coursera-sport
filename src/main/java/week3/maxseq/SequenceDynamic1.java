package week3.maxseq;

public class SequenceDynamic1 {

    public static int[] dynamic(Integer[] sequence) {

        int[] cache = new int[sequence.length];
        int absMax = 0;
        for (int i = sequence.length - 1; i >= 0; i--) { //обходим последовательность с конца
            //для каждого члена sequence[i] находим больший его элемент c права
            // и в поле cache[i] сохраняем максимальное из возможных значений предыдущего + 1
            for (int j = i; j < sequence.length; j++) {
                if (sequence[i] <= sequence[j]) {
                    cache[i] = Math.max(cache[i], cache[j] + 1);
                    //сразу определяем самую длинную цепочку
                    absMax = Math.max(cache[i], absMax);
                }
            }
        }
//        System.out.println("sequence: \t" + Arrays.stream(sequence).map(Objects::toString).collect(Collectors.joining("\t")));
//        System.out.println("cache: \t" + Arrays.stream(cache).mapToObj(Objects::toString).collect(Collectors.joining("\t")));
//        System.out.println("absMax = " + absMax);

        return cache;
    }
}
