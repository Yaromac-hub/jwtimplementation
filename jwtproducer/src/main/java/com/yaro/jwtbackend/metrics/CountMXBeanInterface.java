package com.yaro.jwtbackend.metrics;

@javax.management.MXBean
public interface CountMXBeanInterface extends SelfRegisteredMetricInterface {
    /**
     * @return the total counted value
     */
    long getTotal();
}