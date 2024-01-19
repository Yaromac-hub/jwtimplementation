package com.yaro.jwtbackend.metrics;

import java.util.concurrent.atomic.AtomicLong;

public class CounterMetrics implements CountMXBeanInterface{
    private AtomicLong total = new AtomicLong(0);

    /**
     * Factory method to register and create an instance of CounterMetrics.
     *
     * @param name The name of the metric.
     * @param tags The tags associated with the metric.
     * @return The CounterMetrics instance.
     */
    public static CounterMetrics register(String name, String... tags){
        return new CounterMetrics(name, tags);
    }

    private CounterMetrics(String name, String[] tags) {
        registerMetric(prepareObjectName(name, tags));
    }

    public long inc() {
        return total.incrementAndGet();
    }

    @Override
    public long getTotal() {
        return total.get();
    }

}
