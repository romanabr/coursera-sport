package week4.shops;

import common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShopStoresDynamic {
    private final SsModel model;
    private int[][] distances;

    private static final Logger log = LoggerFactory.getLogger(ShopStoresDynamic.class);

    public ShopStoresDynamic(SsModel model) {
        this.model = model;
    }

    public int solve(){

        int[] shops = model.getShops();
        int[] stores = model.getStores();;
        Arrays.sort(shops); //a
        Arrays.sort(stores); //b

        Function<int[], String> toStr = (array)-> Arrays.stream(array).mapToObj(Objects::toString).collect(Collectors.joining("\t"));

        distances = new int[model.getShops().length][];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = new int[distances.length];
            for (int j = 0; j < distances.length; j++) {
                distances[i][j] = Math.abs(shops[i]-stores[j]);
            }
        }

        log.info("shops\t{}", toStr.apply(shops));
        for (int i = 0; i < distances.length; i++) {
            log.info("{}\t\t{}", stores[i], toStr.apply(distances[i]));
        }

        return 0;
    }
}