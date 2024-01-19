package com.yaro.jwtbackend.metrics;

import java.util.concurrent.atomic.AtomicLong;

public class GaugeMetrics implements GaugeMXBeanInterface{
    private AtomicLong value = new AtomicLong(0);

    /**
     * Factory method to register and create an instance of GaugeMetrics.
     *
     * @param name The name of the metric.
     * @param tags The tags associated with the metric.
     * @return The GaugeMetrics instance.
     */
    public static GaugeMetrics register(String name, String... tags){
        return new GaugeMetrics(name, tags);
    }

    private GaugeMetrics(String name, String[] tags) {
        registerMetric(prepareObjectName(name, tags));
    }

    public long inc() {
        return value.incrementAndGet();
    }

    public long dec() {
        return value.decrementAndGet();
    }

    @Override
    public long getValue() {
        return value.get();
    }

}
