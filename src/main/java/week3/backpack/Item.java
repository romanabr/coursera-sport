package week3.backpack;

public class Item {

    private int number;
    private int weight;
    private int cost;

    public Item(int number, int weight, int cost) {
        this.number = number;
        this.weight = weight;
        this.cost = cost;
    }

    public int getNumber() {
        return number;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
