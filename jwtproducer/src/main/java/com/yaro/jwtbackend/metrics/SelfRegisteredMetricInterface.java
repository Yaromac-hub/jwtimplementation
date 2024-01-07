package com.yaro.jwtbackend.metrics;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface SelfRegisteredMetricInterface {

    default ObjectName prepareObjectName(String name, String[] tags){

        if(!name.contains("_")){
            throw new IllegalArgumentException("Please include '_' into specified metric name - " + name);
        }

        String[] domainNameFormatter = name.replaceFirst("_",":").split(":");

        String objName = domainNameFormatter[0] + ":name=" + domainNameFormatter[1] + "," +
                Arrays.stream(tags).collect(Collectors.joining(",")) + "type=COUNTER";

        try {
            return new ObjectName(objName);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }

    }
    default void registerMetric(ObjectName objName){

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        try {
            server.registerMBean(this, objName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
