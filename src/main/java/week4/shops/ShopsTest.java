package week4.shops;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ShopsTest {

    @Test
    public void shops0() {
        SsModel model = initModel("src/main/java/week4/shops/docs/shops0.in");
        assertEquals(2, new ShopStoresBrutForce(model).solve());
        assertEquals(2, new ShopStoresSort(model).solve());
    }

    @Test
    public void shops3() {
        SsModel model = initModel("src/main/java/week4/shops/docs/shops3.in");
        assertEquals(3, new ShopStoresBrutForce(model).solve());
        assertEquals(3, new ShopStoresSort(model).solve());
    }

    @Test
    public void randomData() {
        int n = 6;
        SsModel model = new SsModel();
        model.setShops(randomInt(n, 50));
        model.setStores(randomInt(n, 50));

        int x1 = new ShopStoresBrutForce(model).solve();
        long x2 = new ShopStoresSort(model).solve();
        assertEquals(x1, x2);
    }

    @Test
    public void shops6() {
        int[] shops = {1, 2, 4};
        int[] stores = {3, 5, 6};
        assertEquals(7, new ShopStoresBrutForce(new SsModel(shops,stores)).solve());
        assertEquals(7, new ShopStoresSort(new SsModel(shops,stores)).solve());
    }

    private int[] randomInt(int length, int bound){
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, length).map(i-> random.nextInt(bound)).sorted().toArray();
    }

    @Test
    public void shops() {
        SsModel model = initModel("src/main/java/week4/shops/docs/shops.in");
        assertEquals(1129, new ShopStoresBrutForce(model).solve());
        assertEquals(1129, new ShopStoresSort(model).solve());
    }

    @Test
    public void shop2() {
        SsModel model = initModel("src/main/java/week4/shops/docs/shops2.in");
        assertEquals(95580888489L, new ShopStoresSort(model).solve());
    }


    @Test
    public void testDataLoad() {

        SsModel model = initModel("src/main/java/week4/shops/docs/shops.in");

        assertEquals(10, model.getShops().length);
        assertEquals(135, model.getShops()[0]);

        assertEquals(10, model.getStores().length);
        assertEquals(-488, model.getStores()[0]);
    }

    @Test
    public void shopsInfo() {
        SsModel model = initModel("src/main/java/week4/shops/docs/shops0.in");

        //есть ли дубли?
        Function<int[], Set<Integer>> convert = (array) -> Arrays.stream(array).boxed().collect(Collectors.toSet());
        Set<Integer> uniqShops = convert.apply(model.getShops());
        Set<Integer> uniqStores = convert.apply(model.getStores());

        assertEquals(model.getShops().length, uniqShops.size());
        assertEquals(model.getStores().length, uniqStores.size());

        uniqShops.addAll(uniqStores);
        assertEquals(2 * model.getStores().length, uniqShops.size());
    }

    private SsModel initModel(String file) {
        try {
            Path path = Paths.get(file);
            List<int[]> collect = Files.lines(path).skip(1)
                    .map(line -> Arrays.stream(line.split("\\s")).map(Integer::valueOf).mapToInt(Integer::intValue).toArray())
                    .collect(Collectors.toList());

            SsModel m = new SsModel();
            m.setShops(collect.get(0));
            m.setStores(collect.get(1));

            return m;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
