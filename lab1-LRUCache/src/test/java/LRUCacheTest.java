import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class LRUCacheTest {
    private Cache<Integer, Integer> cache;
    private Random random;

    @Before
    public void before() {
        cache = new LRUCache<>(10);
        random = new Random();
    }

    @Test
    public void testContract() {
        cache = new LRUCache<>(1000);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            int key = random.nextInt() % 10;
            if (random.nextBoolean()) {
                int val = random.nextInt();
                map.put(key, val);
                cache.put(key, val);
            } else {
                Optional<Integer> exceptVal = Optional.ofNullable(map.getOrDefault(key, null));
                Optional<Integer> val = cache.get(key);
                Assert.assertEquals(exceptVal, val);
            }
        }
    }

    @Test
    public void testEmpty() {
        Assert.assertTrue(cache.get(10).isEmpty());
    }

    @Test
    public void testPutGet() {
        for (int i = 0; i < 1000; i++) {
            cache.put(i, i * 7);
            Assert.assertEquals(Optional.of(i * 7), cache.get(i));
        }
    }

    @Test
    public void testOneCapacity() {
        cache = new LRUCache<>(1);
        cache.put(3, 0);
        Assert.assertTrue(cache.get(3).isPresent());
        cache.put(4, 0);
        Assert.assertTrue(cache.get(4).isPresent());
        Assert.assertTrue(cache.get(3).isEmpty());
    }

    @Test
    public void testTwoCapacity() {
        cache = new LRUCache<>(2);
        cache.put(1, 0);
        cache.put(2, 0);
        cache.put(3, 0);
        Assert.assertTrue(cache.get(1).isEmpty());
        Assert.assertTrue(cache.get(2).isPresent());
        Assert.assertTrue(cache.get(3).isPresent());

        cache.put(2, 0);
        cache.put(4, 0);
        Assert.assertTrue(cache.get(1).isEmpty());
        Assert.assertTrue(cache.get(2).isPresent());
        Assert.assertTrue(cache.get(3).isEmpty());
        Assert.assertTrue(cache.get(4).isPresent());
    }

    @Test
    public void testCapacity() {
        int cap = 5;
        cache = new LRUCache<>(cap);
        for (int i = 0; i < cap; i++) {
            cache.put(i, 0);
        }
        int l = 0, r = cap - 1;
        for (int i = 0; i < 1000; i++) {
            cache.put(r + 1, 0);
            l++;
            r++;
            for (int j = 0; j < 1100; j++) {
                if (j >= l && j <= r) {
                    Assert.assertTrue(cache.get(j).isPresent());
                } else {
                    Assert.assertTrue(cache.get(j).isEmpty());
                }
            }
        }
    }
}
