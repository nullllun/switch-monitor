package cn.albumenj.switchmonitor.util;

import cn.albumenj.switchmonitor.schedule.SwitchesUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author Albumen
 */
public class CustomExecutors {

    private final static Logger logger = LoggerFactory.getLogger(SwitchesUpdate.class);

    public static ExecutorService newFixExecutorService(int count) {
        return new ThreadPoolExecutor(count, count, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new CustomThreadFactory());
    }

    public static void waitExecutor(ExecutorService executorService) {
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.warn("ExecutorService Interrupted!");
        }
    }
}
