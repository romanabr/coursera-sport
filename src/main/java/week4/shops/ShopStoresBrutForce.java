package week4.shops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShopStoresBrutForce {

    private static final Logger log = LoggerFactory.getLogger(ShopStoresBrutForce.class);

    private int[] shops;
    private int[] stores;
    private int minimum = Integer.MAX_VALUE;
    private String bestMask = "unknown";

    public ShopStoresBrutForce(SsModel model) {
        this.shops = model.getShops();
        this.stores = model.getStores();
    }

    public int solve() {

        int[] mask = IntStream.range(0, stores.length).toArray();
        rec(mask, 0);
        log.info("best mask: {}", bestMask);

        return minimum;
    }

    private void rec(int[] mask, int level) {
        for (int i = 0; i < mask.length; i++) {

            //has duplicates
            boolean hasDuplicates = false;
            for (int j = 0; j < level; j++) {
                if (mask[j] == i) {
                    hasDuplicates = true;
                    break;
                }
            }

            if (!hasDuplicates) {
                mask[level] = i;
                if (level + 1 < mask.length) {
                    rec(mask, level + 1);
                } else {

                    int sum = 0;
                    for (int x = 0; x < mask.length; x++) {
                        sum += Math.abs(shops[x] - stores[mask[x]]);
                    }

                    //print mask
                    String value = Arrays.stream(mask).mapToObj(String::valueOf).collect(Collectors.joining("-"));
                    log.debug("mask: {}, sum: {}", value, sum);
                    if (sum < minimum) {
                        minimum = sum;
                        bestMask = value;
                    }
                }
            }
        }
    }


}
