package week3.backpack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BackpackBrutforceAlg {

    private BackpackRequest data;
    private boolean[] index;
    private int maxCost;
    private List<Item> bestItems;

    public String solve(BackpackRequest data) {
        this.data = data;

        index = new boolean[data.getItems().size()];
        rec(0);

        System.out.println("--- Solution --- ");
        System.out.println("maxCost: "+ maxCost);
        System.out.println("costs: "+ bestItems.stream().map(item -> "" + item.getCost()).collect(Collectors.joining("-")));
        System.out.println("weight: "+ bestItems.stream().map(item -> "" + item.getWeight()).collect(Collectors.joining("-")));

        return bestItems.stream().map(Item::getNumber).sorted().map(Object::toString).collect(Collectors.joining(" "));
    }

    public int getMaxCost() {
        return maxCost;
    }

    private void rec(int i) {

        boolean[] vars = {false, true};

        for (boolean var : vars) {
            index[i] = var;
            if (i + 1 == index.length) {
                int weight = 0;
                int cost = 0;
                List<Item> items = new ArrayList<>();

                List<String> weightDebug = new ArrayList<>();
                List<String> costDebug = new ArrayList<>();

                for (int j = 0; j < data.getItems().size(); j++) {
                    Item item = data.getItems().get(j);
                    if (index[j]) {
                        weight += item.getWeight();
                        cost += item.getCost();
                        items.add(item);

                    }

                    weightDebug.add(index[j] ? "" + item.getWeight() : "X");
                    costDebug.add(index[j] ? "" + item.getCost() : "X");
                }

                if (weight <= data.getBackpackCapacity() && cost > maxCost) {
                    maxCost = cost;
                    bestItems = items;
                }

                System.out.println("Weight: "+ String.join("-", weightDebug)
                        + "=" + weight +"/"+data.getBackpackCapacity()
                        + "\t Cost: "+String.join("-", costDebug)
                        + "=" + cost +" (localMax: "+maxCost+")");

            } else if (i < index.length) {
                rec(i + 1);
            }
        }
    }


}
