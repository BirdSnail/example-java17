package birdsnail.example.limit;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于滑动窗口限流<br>
 * 将时间窗口分为若干个小格子，每次窗口向前移动一个小格子。例如时间窗口为100s，分为10个小格子，每个小格子的大小为100/10=10s。每隔10秒前进一格小格子
 */
@Slf4j
public class SlidingWindowLimit {

    private static final AtomicInteger ZERO = new AtomicInteger(0);

    /**
     * 格子数量
     */
    private int num;

    /**
     * 每个小格子大小
     */
    private int gridSize;

    private final LinkedList<WindowGrid> gridList;

    private volatile int gridNo = 1;

    public SlidingWindowLimit(int num, int gridSize) {
        this.num = num;
        this.gridSize = gridSize;
        gridList = new LinkedList<>();
        start();
    }

    private void start() {
        gridList.addLast(new WindowGrid(gridNo++));
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleAtFixedRate(() -> {
            if (gridList.size() >= num) {
                WindowGrid removed = gridList.removeFirst();
                log.info("移除过期窗口|{}", removed);
            }
            WindowGrid newGrid = new WindowGrid(gridNo++);
            gridList.addLast(newGrid);
            log.info("添加新的格子|{}", newGrid);
        }, gridSize, gridSize, TimeUnit.SECONDS);
    }

    public void request(String key) {
        WindowGrid lastGrid = gridList.getLast();
        lastGrid.increment(key);
    }

    public boolean check(String key, int limit) {
        int sum = gridList.stream().mapToInt(it -> it.getCountMap().getOrDefault(key, ZERO).intValue()).sum();
        return sum <= limit;
    }


    @Getter
    @ToString
    static class WindowGrid {

        /**
         * 当前格子编号
         */
        int num;

        long startSecond;

        Map<String, AtomicInteger> countMap;

        public WindowGrid(int num) {
            this.num = num;
            this.startSecond = System.currentTimeMillis() / 1000;
            this.countMap = new ConcurrentHashMap<>();
        }

        public void increment(String key) {
            AtomicInteger count = countMap.computeIfAbsent(key, k -> new AtomicInteger(0));
            count.incrementAndGet();
        }
    }
}
