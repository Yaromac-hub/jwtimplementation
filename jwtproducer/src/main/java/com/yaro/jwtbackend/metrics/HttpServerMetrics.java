package com.yaro.jwtbackend.metrics;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class HttpServerMetrics implements HttpServerRequestsSecondsMXBeanInterface
{
    private AtomicLong count = new AtomicLong(0);
    private AtomicLong sum = new AtomicLong(0);
    private AtomicLong max = new AtomicLong(0);

    private final ExecutorService maxRessetingScheduler;

    public static HttpServerMetrics register(String name, String... tags){
        return new HttpServerMetrics(name, tags);
    }
    public HttpServerMetrics(String name, String[] tags) {
        this.registerMetric(name, tags);

        this.maxRessetingScheduler = Executors.newSingleThreadExecutor(runnable->{
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });

        this.maxRessetingScheduler.submit(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000);
                    this.max.getAndUpdate(max -> {
                        if(max<10) {
                            return 0;
                        }else{
                            return (long) (max * 0.9);
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Increments number of requests
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
     * Increments sum value by request duration
     * @param value request time duration in millis
     * @return the sum of the duration of every request in millis
     */
    public long add(long value) {
        updMax(value);
        System.out.println(value + "added value");
        return sum.addAndGet(value);
    }

    @Override
    public float getSum() {
        return sum.get()/1_000.0f;
    }

    private void updMax(long sample) {
        max.updateAndGet(curMax -> (sample > curMax) ? sample : curMax);
    }

    @Override
    public float getMax() {
        return max.get()/1_000.0f;
    }

}
