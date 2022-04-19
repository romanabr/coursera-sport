package week4.shops;

public class SsModel {
    private int[] shops;
    private int[] stores;

    public SsModel() {
    }

    public SsModel(int[] shops, int[] stores) {
        this.shops = shops;
        this.stores = stores;
    }

    public int[] getShops() {
        return shops;
    }

    public void setShops(int[] shops) {
        this.shops = shops;
    }

    public int[] getStores() {
        return stores;
    }

    public void setStores(int[] stores) {
        this.stores = stores;
    }
}
