package birdsnail.example.算法.动态规划;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 */
public class ClimbStairs {


    public static int solution(int num) {
        int[] cache = new int[num];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = -1;
        }
        return climb(num - 1, cache) + climb(num - 2, cache);
    }

    private static int climb(int remind, int[] cache) {
        if (remind == 0) {
            return 1;
        }
        if (remind < 0) {
            return 0;
        }
        if (cache[remind] >= 0) {
            return cache[remind];
        }

        int result = climb(remind - 1, cache) + climb(remind - 2, cache);
        cache[remind] = result;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(solution(2));
        System.out.println(solution(44));
        System.out.println(solution2(44));
    }

    public static int solution2(int num) {
        int[] dp = new int[num + 1];
        for (int i = 0; i < dp.length; i++) {
            if (i == 0 || i == 1) {
                dp[i] = 1;
            } else {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
        }
        return dp[num];
    }




}
