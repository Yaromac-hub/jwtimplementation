package com.yaro.jwtbackend.metrics;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public interface SelfRegisteredMetricInterface {

    /**
     * Prepares an ObjectName based on the provided metric name and tags.
     *
     * @param name The metric name with a specific format.
     * @param tags The tags associated with the metric.
     * @return The prepared ObjectName.
     */
    default ObjectName prepareObjectName(String name, String[] tags){
        validateNameAndTags(name, tags);

        String[] parts = name.replaceFirst("_",":").split(":");
        String domain = parts[0];
        String metricName = parts[1];

        List<String> tagList = buildTagList(metricName, tags);
        String objName = domain + ":" + String.join(",", tagList);

        try {
            return new ObjectName(objName);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Registers the metric with the provided ObjectName.
     *
     * @param objName The ObjectName to register.
     */
    default void registerMetric(ObjectName objName){
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(this, objName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateNameAndTags(String name, String[] tags) {
        if (!name.contains("_")) {
            throw new IllegalArgumentException("Please include '_' into specified metric name: " + name);
        }
        if (tags.length % 2 != 0) {
            throw new IllegalArgumentException("Incorrect set of tag values provided");
        }
    }

    private List<String> buildTagList(String name, String[] tags) {
        List<String> tagList = new ArrayList<>();
        tagList.add("name=" + name);
        for (int i = 0; i < tags.length; i += 2) {
            String key = tags[i];
            String value = tags[i + 1];
            tagList.add(key + "=" + value);
        }
        return tagList;
    }

}
