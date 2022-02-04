package week3.backpack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BackpackDynamicAlg {
    private int[][] cache;
    private int[][] trans;

    public String solve(BackpackRequest data) {
        cache = new int[data.getItems().size()][];
        trans = new int[data.getItems().size()][];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new int[data.getBackpackCapacity()];
            trans[i] = new int[data.getBackpackCapacity()];

            Item item = data.getItems().get(i);
            for (int capacityIndex = 0; capacityIndex < data.getBackpackCapacity(); capacityIndex++) {
                int capacityValue = capacityIndex + 1;
                if (capacityValue < item.getWeight()) { //не можем положить предмет в рюкзак, используем предыдущее значение веса
                    cache[i][capacityIndex] = getCostSave(cache, i - 1, capacityIndex);
                    trans[i][capacityIndex] = 0;
                } else {
                    //стоимость рюкзака если в него положить данный предмет равна
                    //стоимости данного предмета + максимальной стоимости рюкзака с вместимостью currCapacity - item.weight
                    int withCost = item.getCost() + getCostSave(cache, i - 1, capacityIndex - item.getWeight());
                    int withoutCost = getCostSave(cache, i - 1, capacityIndex);
                    cache[i][capacityIndex] = Math.max(withCost, withoutCost);
                    trans[i][capacityIndex] = withCost>withoutCost?1:0;
                }
            }
        }

//        Utils.showArray(cache);
//        System.out.println("-----------");
//        Utils.showArray(trans);

        List<Integer> result = new ArrayList<>();
        int weightIndex = trans[0].length - 1;
        for (int i = data.getItems().size() - 1; i >= 0; i--) {
            if (trans[i][weightIndex] > 0) {
                result.add(i+1); //предмет был положен в рюкзак
                weightIndex -= data.getItems().get(i).getWeight() - 1;
            }
        }

        //total cost
        int totalWeight = result.stream().mapToInt(i -> data.getItems().get(i - 1).getWeight()).sum();
        int totalCost = result.stream().mapToInt(i -> data.getItems().get(i - 1).getCost()).sum();

        System.out.println("totalCost = " + totalCost);
        System.out.println("totalWeight = " + totalWeight);

        return result.stream().sorted().map(Object::toString).collect(Collectors.joining(" "));
    }

    private int getCostSave(int[][] cache, int itemIndex, int capacityIndex) {
        if (itemIndex < 0 || capacityIndex < 0) {
            return 0;
        }
        return cache[itemIndex][capacityIndex];
    }

    public int getMaxCost() {
        return cache[cache.length - 1][cache[0].length - 1];
    }
}
