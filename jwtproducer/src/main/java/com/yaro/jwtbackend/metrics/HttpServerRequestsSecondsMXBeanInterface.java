package com.yaro.jwtbackend.metrics;

@javax.management.MXBean
public interface HttpServerRequestsSecondsMXBeanInterface extends SelfRegisteredMetricInterface {
    /**
     *
     * @return the total number of requests
     */
    long getCount();

    /**
     *
     * @return the sum of the duration of every request in seconds
     */
    float getSum();

    /**
     *
     * @return the maximum request duration for every N requests
     */
    float getMax();

}