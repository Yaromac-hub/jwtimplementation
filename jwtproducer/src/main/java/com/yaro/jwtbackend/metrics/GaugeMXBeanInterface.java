package com.yaro.jwtbackend.metrics;

@javax.management.MXBean
public interface GaugeMXBeanInterface extends SelfRegisteredMetricInterface {
    /**
     * @return current value
     */
    long getValue();
}