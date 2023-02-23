package birdsnail.example.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineExample {

    public static Cache<String, Object> createCaffeine() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(3, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    private static class CacheDataBaseMock {

        private Map<String, Object> map = new HashMap<>();
        private Cache<String, Object> cache = createCaffeine();

        public void put(String key, Object value) {
            System.out.printf("put [key:%s, value:%s]%n", key, value);
            map.put(key, value);
            cache.put(key, value);
        }

        public Object get(String key) {
            Object cacheValue = cache.getIfPresent(key);
            if (cacheValue != null) {
                return cacheValue;
            }
            System.out.println("缓存没有命中 key:" + key);
            Object value = map.get(key);
            if (value != null) {
                cache.put(key, value);
            }
            return value;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        CacheDataBaseMock db = new CacheDataBaseMock();
        String key = "xxx";
        // db.get(key);
        db.put(key, "hello lol");
        System.out.println(key + "=" + db.get(key)); // 命中缓存
        Thread.sleep(4000L);
        System.out.println(key + "=" + db.get(key)); // 没有命中
        System.out.println("第二次查询：" + key + "=" + db.get(key)); // 命中缓存
    }

}
