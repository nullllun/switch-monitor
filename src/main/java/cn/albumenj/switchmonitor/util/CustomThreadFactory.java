package cn.albumenj.switchmonitor.util;

import java.util.concurrent.ThreadFactory;

/**
 * 线程池管理
 *
 * @author Albumen
 */
public class CustomThreadFactory implements ThreadFactory {
    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize
     * priority, name, daemon status, {@code ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to
     * create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(4);
        return thread;
    }
}
