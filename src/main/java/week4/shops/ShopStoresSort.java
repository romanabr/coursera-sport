package week4.shops;

import java.util.Arrays;

public class ShopStoresSort {
    private SsModel model;

    public ShopStoresSort(SsModel model) {
        this.model = model;
    }

    public long solve() {

        int[] shops = model.getShops();
        int[] stores = model.getStores();
        Arrays.sort(shops);
        Arrays.sort(stores);

        long sum = 0;
        for (int i = 0; i < shops.length; i++) {
            sum += Math.abs(shops[i] - stores[i]);
        }

        return sum;
    }
}
