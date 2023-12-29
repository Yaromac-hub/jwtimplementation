package com.yaro.jwtbackend.metrics;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface SelfRegisteredMetricInterface {

    default void registerMetric(String name, String[] tags){
        if(!name.contains("_")){
            throw new IllegalArgumentException("Please include '_' into specified metric name - " + name);
        }
        String[] domainNameFormatter = name.replaceFirst("_",":").split(":");

        String objName = domainNameFormatter[0] + ":name=" + domainNameFormatter[1] + "," +
                Arrays.stream(tags).collect(Collectors.joining(","));
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(this, new ObjectName(objName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
