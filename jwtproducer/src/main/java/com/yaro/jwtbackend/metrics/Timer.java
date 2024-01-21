package com.yaro.jwtbackend.metrics;

import javax.management.ObjectName;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A class to monitor HTTP server metrics related to request durations.
 */
public class Timer implements TimerMXBeanInterface
{
    private static final long MAX_RESET_THRESHOLD = 10;
    private static final double MAX_RESET_FACTOR = 0.97;
    private AtomicLong count = new AtomicLong(0);
    private AtomicLong sum = new AtomicLong(0);
    private AtomicLong max = new AtomicLong(0);
    private final ExecutorService maxRessetingScheduler;

    public static Timer register(ObjectName objectName){
        return new Timer(objectName);
    }
    private Timer(ObjectName objectName) {
        registerMetric(objectName);

        this.maxRessetingScheduler = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });

        this.maxRessetingScheduler.submit(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    resetMax();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void resetMax() {
        max.getAndUpdate(curMax -> {
            if (curMax < MAX_RESET_THRESHOLD) return 0;
            else return (long) (curMax * MAX_RESET_FACTOR);
        });
    }

    /**
     * Increments the number of requests
     * @return the total number of requests
     */
    public long inc() {
        return count.incrementAndGet();
    }

    @Override
    public long getCount() {
        return count.get();
    }

    /**
     * Increments the sum value by the request duration
     * and updates the maximum request duration
     * @param value the request duration in milliseconds
     * @return the updated sum of request durations.
     */
    public long add(long value) {
        updMax(value);
        return sum.addAndGet(value);
    }

    @Override
    public float getSum() {
        return sum.get()/1000f;
    }

    private void updMax(long sample) {
        max.updateAndGet(curMax -> (sample > curMax) ? sample : curMax);
    }

    @Override
    public float getMax() {
        return max.get()/1000f;
    }

    public void record(long value, TimeUnit timeUnit){
        inc();
        add(timeUnit.toMillis(value));
    }

}