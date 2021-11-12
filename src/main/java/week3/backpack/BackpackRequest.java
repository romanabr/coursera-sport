package week3.backpack;

import java.util.List;

public class BackpackRequest {

    private int backpackCapacity;

    private List<Item> items;

    public BackpackRequest(int backpackCapacity, List<Item> items) {
        this.backpackCapacity = backpackCapacity;
        this.items = items;
    }

    public int getBackpackCapacity() {
        return backpackCapacity;
    }

    public void setBackpackCapacity(int backpackCapacity) {
        this.backpackCapacity = backpackCapacity;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
