package com.yaro.jwtbackend.metrics;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public interface SelfRegisteredMetricInterface {
    default void registerMetric(String name){
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(this, new ObjectName(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
