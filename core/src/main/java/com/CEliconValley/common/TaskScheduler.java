package com.CEliconValley.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
    private static final ScheduledExecutorService scheduler =
        Executors.newSingleThreadScheduledExecutor();

    public static void schedule(Runnable task, long delay, TimeUnit unit) {
        scheduler.schedule(task, delay, unit);
    }

    public static void shutdown() {
        scheduler.shutdown();
    }
}
