package com.yaro.jwtbackend.metrics;

import javax.management.MXBean;

/**
 * An MXBean interface representing metrics related to HTTP server requests.
 */
@MXBean
public interface HttpServerRequestsSecondsMXBeanInterface extends SelfRegisteredMetricInterface {

    /**
     *
     *
     * @return the total count of requests.
     */
    long getCount();

    /**
     *
     *
     * @return the total sum of request durations in seconds.
     */
    float getSum();

    /**
     *
     *
     * @return the maximum request duration within a 1-minute time window, in seconds.
     */
    float getMax();

}
