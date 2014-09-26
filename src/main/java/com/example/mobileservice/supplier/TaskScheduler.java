package com.example.mobileservice.supplier;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author vrg
 */
public enum TaskScheduler {

    INSTANCE;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private Random random = new Random();

    public ScheduledFuture<?> scheduleTask(Runnable task, long delay, TimeUnit timeUnit) {
        return executorService.schedule(task, delay, timeUnit);
    }

    public ScheduledFuture<?> scheduleTaskToRandomTime(Runnable task, long minDelay, long maxDelay, TimeUnit timeUnit) {

        long variance = maxDelay - minDelay;

        long delay = minDelay + random.nextInt((int) variance);

        return scheduleTask(task, delay, timeUnit);
    }

}
