package com.yaro.jwtbackend.metrics;

import org.apache.log4j.Logger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public interface SelfRegisteredMetricInterface {

    Logger log = Logger.getLogger(SelfRegisteredMetricInterface.class);

    String[] commonTags = {"application","sas-creditregistry"};

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
            log.error(e.toString(), e);
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
            if(!server.isRegistered(objName)) {
                server.registerMBean(this, objName);
            }
            else if(server.getObjectInstance(objName).getClassName().equals(this.getClass().getName())){
                server.unregisterMBean(objName);
                server.registerMBean(this, objName);
            }
            else{
                throw new InstanceAlreadyExistsException("Metric with the same name already exist in the context");
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw new RuntimeException(e);
        }
    }

    default void validateNameAndTags(String name, String[] tags) {
        if (!name.contains("_")) {
            throw new IllegalArgumentException("Please include '_' into specified metric name: " + name);
        }
        if (tags.length % 2 != 0) {
            throw new IllegalArgumentException("Incorrect set of tag values provided");
        }
    }

    default List<String> buildTagList(String name, String[] tags) {
        List<String> tagList = new ArrayList<>();
        tagList.add("name=" + name);
        for (int i = 0; i < tags.length; i += 2) {
            String key = tags[i];
            String value = tags[i + 1];
            tagList.add(key + "=" + value);
        }
        for(int i = 0; i < commonTags.length; i += 2){
            String key = commonTags[i];
            String value = commonTags[i + 1];
            tagList.add(key + "=" + value);
        }
        return tagList;
    }

}
