package birdsnail.example.limit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * RateLimit单机限流
 */
public class RateLimitDemo {

    public static void main(String[] args) throws InterruptedException {
        rateLimitWithToken();

    }

    /**
     * 令牌桶算法实现限流
     */
    private static void rateLimitWithToken() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(5.0);
        Thread.sleep(2000L);
        System.out.println("令牌库存：" + rateLimiter.getRate());
        // 没有足够令牌发放时，采用滞后处理的方式，只要有就会返回，请求数量不会对请求本身有影响，影响的是下一次请求
        System.out.println("获取10个令牌耗时：" + rateLimiter.acquire(20));
        // 上次请求获取令牌所需等待的时间由本次请求来承受，也就是代替前一个请求进行等待。
        // 上次请求缺少15个令牌，应该等待3秒钟
        System.out.println("获取1个令牌耗时：" + rateLimiter.acquire(1));
    }


}
