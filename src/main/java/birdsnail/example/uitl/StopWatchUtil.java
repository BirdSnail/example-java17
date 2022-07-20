package birdsnail.example.uitl;

import org.apache.commons.lang3.time.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StopWatchUtil {

    public static StopWatch createStarted() {
        StopWatch sw = new StopWatch();
        sw.start();
        return sw;
    }

    public static void loggingStopped(StopWatch stopWatch, String taksName) {
        if (!stopWatch.isStopped()) {
            stopWatch.stop();
            log.info("[" + taksName + "]耗时：" + stopWatch.getTime());
        }
    }

}
