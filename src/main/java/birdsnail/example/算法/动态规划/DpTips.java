package birdsnail.example.算法.动态规划;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态规划使用到的技巧
 */
public class DpTips {


    /**
     * 问题描述：一个二维数组，从左上角[0][0]出发，到达右下角，每一步只能向下或者向右，问到达右下角经过的路径上所有点的和最小值是多少？
     */
    public static int shortPathSum(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        /*
            缓存从(0,0)出发，到达（i,j）点时的最少路径和
         */
        int[][] dp = new int[row][col];
        dp[0][0] = matrix[0][0]; // 起始点

        // 填充第一行，只向右走的情况
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + matrix[0][i];
        }

        for (int r = 1; r < row; r++) {
            dp[r][0] = dp[r - 1][0] + matrix[r][0]; // 第一列，只向下走的情况
            for (int j = 1; j < col; j++) {
                // 从两种走法中选择花费最少的情况
                dp[r][j] = Math.min(dp[r][j - 1], dp[r - 1][j]) + matrix[r][j];
            }
        }

        return dp[row - 1][col - 1];
    }


    /**
     * 技巧一：dp表空间优化
     * <p/>
     * 在常规的二维dp表中，我们需要申请i行j列的二维数组空间，这里其实是存在空间优化的，比如dp表中的值取决于他的左侧的值，上方的值，左上角的值
     *
     * <pre>
     *     |a  |b  |c |
     *     |a1 |b1 |c1|
     *     |a2 |b2 |c2|
     *
     *     (a, b, a1) -->b1, (a1, a2, b1) -->b2
     * </pre>
     * 对于第一行，我们可以顺利求解，因为他没有上方和左上方的值，有了第一行我们可以从左往右求解第二行，以此类推，可以求出最后一行。
     * 但从第三行开始，我们就不需要第一行的数据了，因此我们可以使用两个一维数组A,B来装下当前行和上一行的值，让这两个数组滚动更新，
     * <pre>
     *     A -->上一行，推导出
     *     B -->当前行，推导出下一行放入A中
     * </pre>
     * <p>
     * 假设动态规划表填充到了[i,j]位置，他依赖三个位置，上：[i-1,j], 左：[i,j-1], 左上：[i-1,j-1]，因为从左向右遍历，[0,j]只依赖[0,j-1]上一行
     * 左上的值就是上一行的老值，上方的值也是上一行的老值，因此只需要一个一维数组存放上一次的值就可以了，每到下一行让这个数组从左到右自我更新
     * <p>
     */
    public static int shortPathSumTipSpace(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[] res = new int[col];
        res[0] = matrix[0][0]; // 起始点

        // 填充第一行：只向右走
        for (int i = 1; i < col; i++) {
            res[i] = res[i - 1] + matrix[0][i];
        }

        // 走到下一行就让res自我更新
        for (int r = 1; r < row; r++) {
            res[0] = res[0] + matrix[r][0]; // 第一列的值之取决于上方
            for (int j = 1; j < col; j++) {
                // res[j - 1]的值已经被更新为本次的值,res[j]还是存放的上一行值
                res[j] = Math.min(res[j], res[j - 1]) + matrix[r][j];
                // res[j]更新完成
            }
        }

        return res[col - 1];
    }

    /**
     * 有一个数组，里面的值代表一种硬币面值，每一种面值的纸币都有无限个，选取任意类型和任意数量的纸币，使他们的和为total，问有多少种选择方法？
     *
     * @param money  纸币面值数组
     * @param amount 目标金钱总数
     * @return 达到目标金钱总数的选择数量
     */
    public static int coinsWaysNoLimit(int[] money, int amount) {
        return process(money, 0, amount);
    }

    /**
     * 暴力递归遍历所有情况，当来到了index位置，有若干种情况，选择当前面值0，当前面值1张，当前面值2张。最多为total/money[index] <br/>
     * 一个大问题可以分为若干个规模更小的子问题f(i, total) --> f(i+1, rest-0*面额) + f(i+1,rest-1*面额) + ...f(i+1,rest-n*面额)
     *
     * @param money 面值数组
     * @param index 当前位置
     * @param rest  剩余的钱总数
     */
    private static int process(int[] money, int index, int rest) {
        if (rest < 0) {
            return 0;
        }
        if (index == money.length) {
            return rest == 0 ? 1 : 0;
        }
        if (rest == 0) {
            return 1;
        }

        int ways = 0;
        int value = money[index];
        for (int i = 0; value * i <= rest; i++) {
            ways += process(money, index + 1, rest - i * value);
        }

        return ways;
    }


    /**
     * 动态规划版本:
     * <p/>
     * 技巧二：观察dp的空间依赖，进行优化
     */
    public static int coinsWaysNoLimitDp(int[] money, int amount) {
        int N = money.length;
        int[][] dp = new int[N + 1][amount + 1];

        for (int i = 0; i <= N; i++) {
            dp[i][0] = 1;
        }

        for (int r = N - 1; r >= 0; r--) {
            for (int j = 1; j <= amount; j++) {
                int ways = 0;
                int value = money[r];
                /*
                    这里可以进一步优化。本质是一个求和f(r,j) = f(r+1, 0) + f(r+1, 1) + f(r+1, 2) +.....+f(r+1, j)
                    f(r,j-1)=f(r+1, 0) + f(r+1, 1*v) + f(r+1, 2) +.....+f(r+1, j-1)
                    f(r,j) = f(r,j-1) + f(r+1, j) -->左侧的值 + 下方的值
                 */


                // for (int i = 0; value * i <= j; i++) {
                //     ways += dp[r + 1][j - i * value];
                // }
                dp[r][j] = dp[r + 1][j];
                if ((j - money[r]) >= 0) {
                    dp[r][j] += dp[r][j - money[r]];
                }
            }
        }

        return dp[0][amount];
    }


    /**
     * 一个数组代表一堆硬币，可以任意选取这些硬币，给定一个金额amount，问有多少种方法可以使选择的硬币金额总数为amount。
     * 认为值相同的硬币是相等的。[1,1,1] amount为2，有一种选法(1,1)
     *
     * @param coins  硬币
     * @param amount 金额
     * @return 选择硬币的总额刚好是amount的方法数
     */
    public static int coinsWays3(int[] coins, int amount) {
        Map<Integer, Integer> coinNumbers = new HashMap<>();
        for (int coin : coins) {
            coinNumbers.compute(coin, (k, v) -> v == null ? 1 : v + 1);
        }
        // 拆分为两个数组 硬币面值，该面值类型的硬币的个数
        int[] coinType = new int[coinNumbers.size()];
        int[] counts = new int[coinNumbers.size()];
        int start = 0;
        for (Map.Entry<Integer, Integer> entry : coinNumbers.entrySet()) {
            coinType[start] = entry.getKey();
            counts[start++] = entry.getValue();
        }

        return cwProcess3(coinType, counts, 0, amount);
    }

    /**
     * 暴力递归：从左往右模型，遍历每种面额。来到了index位置，
     * 当前位置的面额为cointType[index],选择当前面额的硬币0个，1个...counts[idnex]个，
     *
     * @param coinValue 面额数组
     * @param counts    每张面额硬币数量
     * @param index     当前位置
     * @param rest      剩余的目标金额
     * @return 达到目标金额的方法数
     */
    private static int cwProcess3(int[] coinValue, int[] counts, int index, int rest) {
        if (rest < 0) {
            return 0;
        }

        int length = coinValue.length;
        if (index == length) {
            return rest == 0 ? 1 : 0;
        }
        if (rest == 0) {
            return 1;
        }

        int ways = 0;
        int value = coinValue[index];
        int num = counts[index]; // 当前面额的硬币共有多少个
        // 当前面额硬币选择个数所有情况求和，从0到num
        for (int i = 0; i <= num && i * value <= rest; i++) {
            ways += cwProcess3(coinValue, counts, index + 1, rest - i * value);
        }

        return ways;
    }

    /**
     * 动态规划版本
     */
    public static int coinsWays3Dp(int[] coins, int amount) {
        Map<Integer, Integer> coinNumbers = new HashMap<>();
        for (int coin : coins) {
            coinNumbers.compute(coin, (k, v) -> v == null ? 1 : v + 1);
        }
        // 拆分为两个数组 硬币面值，该面值类型的硬币的个数
        int[] coinType = new int[coinNumbers.size()];
        int[] counts = new int[coinNumbers.size()];
        int start = 0;
        for (Map.Entry<Integer, Integer> entry : coinNumbers.entrySet()) {
            coinType[start] = entry.getKey();
            counts[start++] = entry.getValue();
        }

        // 填充dp表
        int[][] dp = new int[coinNumbers.size() + 1][amount + 1];
        fillDp(dp, coinType, counts, amount);
        return dp[0][amount];
    }

    /**
     * 技巧2：观察优化，优化for循环
     */
    private static void fillDp(int[][] dp, int[] coinValue, int[] counts, int rest) {
        int length = dp.length;

        for (int i = 0; i < length; i++) {
            dp[i][0] = 1;
        }

        for (int index = length - 2; index >= 0; index--) {
            int value = coinValue[index];
            int num = counts[index]; // 当前面额的硬币共有多少个
            for (int j = 1; j <= rest; j++) {
                // int ways = 0;
                // // 当前面额硬币选择个数所有情况求和，从0到num
                // for (int i = 0; i <= num && i * value <= j; i++) {
                //     ways += dp[index + 1][j - i * value];
                // }

                /*
                    对上面的循环就行优化
                    求和可以优化为下方的值+左侧邻近位置的值
                 */
                dp[index][j] = dp[index + 1][j];
                if (j >= value) {
                    dp[index][j] += dp[index][j - value];
                }
                // 因为硬币有数量限制，下方的值+左侧邻近位置的值相当于计算了选择num+1个硬币的情况,要减去
                if (j >= value * (num + 1)) {
                    dp[index][j] -= dp[index + 1][j - (num + 1) * value];
                }
            }
        }

    }


    /**
     * 一个怪兽拥有的血量为hp，英雄的砍一刀的伤害为【0--hurt】范围内随机一个整数值，英雄最多可以砍times刀，问英雄把怪兽砍死的概率
     * <p/>
     * 每一刀伤害可能为[0，1，2...hurt]共有hurt+1种情况,可以砍times次，总共的情况有{@code (hurt+1)^times}种.
     * <pre>
     *    杀死概率 = hp少于0的所有情况数/总情况数
     * </pre>
     *
     * @param hp    怪兽血量
     * @param hurt  最高伤害
     * @param times 刀数限制
     * @return 砍死的概率
     */
    public static double killProbability(int hp, int hurt, int times) {
        long killWays = killProcess(hp, times, hurt);
        System.out.println("总的杀死方法数：" + killWays);
        return (killWays + 0.0d) / Math.pow(hurt + 1, times);
    }

    /**
     * 暴力递归：求把怪物杀死的方法数
     *
     * @param hp    剩余血量
     * @param times 剩余刀数
     * @param hurt  最高伤害值
     * @return 杀死的方法数
     */
    private static long killProcess(int hp, int times, int hurt) {
        if (times == 0) {// 没有刀数了
            return hp <= 0 ? 1 : 0;
        }
        if (hp <= 0) {// 提前把怪物砍死了,因为题目问的是最多times刀，不是刚好times刀，所以不用剪枝，后续无论是什么伤害的情况，都算成功把怪杀死。
            return (long) Math.pow(hurt + 1, times); // 此公式替代了遍历
        }

        // 每种伤害的情况都考虑到，总的杀死方法就是各种伤害下的和
        long ways = 0;
        for (int i = 0; i <= hurt; i++) {
            ways += killProcess(hp - i, times - 1, hurt);
        }

        return ways;
    }

    /**
     * 动态规划版本: 使用技巧二，优化for循环，变为邻近位置和下方位置相加
     */
    public static double killProbabilityDp(int hp, int hurt, int times) {
        long[][] dp = new long[times + 1][hp + 1];
        dp[0][0] = 1;

        for (int t = 1; t <= times; t++) {
            dp[t][0] = (long) Math.pow(hurt + 1, t); // 提前砍死怪物的情况
            for (int h = 1; h <= hp; h++) {
                dp[t][h] = dp[t - 1][h] + dp[t][h - 1];

                // 多算了在最大伤害情况下多扣了1点血的情况
                if (h - hurt >= 1) {
                    dp[t][h] -= dp[t - 1][h - hurt - 1];
                } else {
                    dp[t][h] -= (long) Math.pow(hurt + 1, t - 1);
                }
            }
        }

        long ways = dp[times][hp];
        System.out.println("dp版本总的杀死方法数：" + ways);
        return ways / Math.pow(hurt + 1, times);
    }

    /**
     * 最少硬币数(leetcode零钱兑换题目)：
     * <p/>
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * 你可以认为每种硬币的数量是无限的。
     *
     * @param coins  硬币面额
     * @param amount 目标金额
     * @return 凑齐目标金额所需的最少硬币数
     */
    public static int minNumberOfCoins(int[] coins, int amount) {
        return mncProcess1(coins, 0, amount);
    }

    /**
     * 方法一：使用暴力递归，从左往右模型。index=0时，遍历选择当前硬币0-N个的情况，目标为amount<b/>
     * index=1是，遍历选择当前硬币0-N个的情况，目标为amount-(上次已选择的金额)
     * <p/>
     * 不需要对coins排序，因为先拿最大的硬币后有可能凑不齐amount，必需要考虑每一种硬币都没有拿的情况，无需排序
     *
     * @param coins 硬币面额
     * @param index coins当前索引位置
     * @param rest  剩余的目标金额
     * @return index位置下，凑齐剩余的目标金额所属的最少硬币数
     */
    private static int mncProcess1(int[] coins, int index, int rest) {
        if (rest < 0) { // 凑不齐目标金额
            return Integer.MAX_VALUE;
        }

        if (index == coins.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE; // 目标金额为0，不选择任何硬币，其他情况视为不能凑齐目标金额
        }

        int value = coins[index];
        int ans = Integer.MAX_VALUE;
        for (int num = 0; num * value <= rest; num++) {
            int next = mncProcess1(coins, index + 1, rest - num * value); // 当前硬币面额选择了num个，之后将问题交给下一个面额
            if (next != Integer.MAX_VALUE) { // 有合法解时才更新结果，防止next+num溢出
                ans = Math.min(ans, next + num);
            }
        }

        return ans;
    }

    /**
     * 最少硬币数：动态规划版本
     */
    public static int minNumberOfCoinsDp(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        dp[coins.length][0] = 0;
        for (int i = 1; i <= amount; i++) {
            dp[coins.length][i] = Integer.MAX_VALUE;
        }

        for (int index = coins.length - 1; index >= 0; index--) {
            int value = coins[index];
            for (int rest = 0; rest <= amount; rest++) {
                // int ans = Integer.MAX_VALUE;
                // for (int num = 0; num * value <= rest; num++) {
                //     int next =dp[index + 1][rest - num * value]; // 当前硬币面额选择了num个，之后将问题交给下一个面额
                //     if (next != Integer.MAX_VALUE) { // 有合法解时才更新结果，防止next+num溢出
                //         ans = Math.min(ans, next + num);
                //     }
                // }

                /*
                优化for循环，取左侧邻近值和下方值的最小值
                 */
                dp[index][rest] = dp[index + 1][rest];
                if (value <= rest && dp[index][rest - value] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - value] + 1); // 左侧邻近值是选择当前一个硬币的情况计算出来的，需要加上1
                }
            }
        }

        return dp[0][amount];
    }


    /**
     * 整数拆分：给定一个正整数，可以拆分为若干个正整数的和，3=[1,1,1],[1,2],[3],要求后面数不能比前面的数小，问一个正整数可以有多少种拆法
     *
     * @param number 给定的数
     * @return 多少种拆法
     */
    public static int splitNumber(int number) {
        return snProcess(1, number);
    }

    /**
     * 通过递归，暴力尝试，列举所有的情况：从选择1开始
     * <p/>
     * 难点是整理递归的base case
     *
     * @param pre  前一个数值
     * @param rest 剩余的数
     * @return 多少种拆法
     */
    private static int snProcess(int pre, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (pre > rest) { // 前一个数比剩余的数大，意味着不可能继续拆分了，因为违背后面的数不能比前面的数小这一条件
            return 0;
        }
        int ways = 0;
        for (int i = pre; i <= rest; i++) {
            ways += snProcess(i, rest - i); // i作为前一个数，传递给下一个递归，问题规模缩小
        }
        return ways;
    }


    /**
     * 拆分整数：动态规划版本,并使用技巧二优化
     */
    public static int splitNumberDp(int number) {
        if (number == 1) {
            return 1;
        }

        int[][] dp = new int[number + 1][number + 1];

        for (int i = 0; i <= number; i++) {
            dp[i][0] = 1;
            dp[i][i] = 1; // pre == rest时，只能拆分为0和自身一种情况
        }

        for (int pre = number -1; pre >= 1; pre--) {
            // 左下半区是pre>rest的情况，值为0，不用填充
            for (int rest = pre; rest <= number; rest++) {
                // int ways = 0;
                // for (int i = pre; i <= rest; i++) {
                //     ways += dp[i][rest - i]; // i作为前一个数，传递给下一个递归，问题规模缩小
                // }

                /*
                优化for循环,原始求和是左侧对角线的和，i递增，rest-i递减
                 */
                dp[pre][rest] = dp[pre][rest - pre] + dp[pre + 1][rest]; // 左侧邻近值和下方值的和
            }
        }

        return dp[1][number];
    }


    public static void main(String[] args) {
        // int[][] matrix = {
        //         {2, 1, 5, 4},
        //         {2, 3, 5, 4},
        //         {8, 3, 1, 4},
        //         {2, 10, 2, 3}
        // };
        //
        // System.out.println(shortPathSum(matrix));
        // System.out.println(shortPathSumTipSpace(matrix));
        //
        // int[] coins = {1, 2, 5};
        // System.out.println(coinsWaysNoLimit(coins, 5));
        int number = 13;
        System.out.println(splitNumber(number));
        System.out.println(splitNumberDp(number));
    }

}
