package com.yaro.jwtbackend.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MeterRegistry {

    static String[] commonTags = {"application","sas-creditregistry"};
    private static Logger log = LoggerFactory.getLogger(MeterRegistry.class);
    private static ConcurrentHashMap<ObjectName, Timer> meterRegistryTimerSet = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ObjectName, Gauge> meterRegistryGaugeSet = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ObjectName, Counter> meterRegistryCounterSet = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, ObjectName> objNamesSet = new ConcurrentHashMap<>();
    private static final ObjectName malformedObjectName = prepareObjectName("metric_with_malformed_name");
    public static boolean FAIL_ON_BAD_METRIC_NAME_PROVIDED = false;

    /**
     * Factory method to register and create an instance of Timer.
     *
     * @param name The name of the metric.
     * @param tags The tags associated with the metric.
     * @return The Timer instance.
     */
    public static Timer timer(String name, String... tags){
        return meterRegistryTimerSet.computeIfAbsent(getObjectName(name, tags),
          k -> Timer.register(k));
    }

    /**
     * Factory method to register and create an instance of GaugeMetrics.
     *
     * @param name The name of the metric.
     * @param tags The tags associated with the metric.
     * @return The GaugeMetrics instance.
     */
    public static Gauge gauge(String name, String... tags){
        return meterRegistryGaugeSet.computeIfAbsent(getObjectName(name, tags),
                k -> Gauge.register(k));
    }

    /**
     * Factory method to register and create an instance of CounterMetrics.
     *
     * @param name The name of the metric.
     * @param tags The tags associated with the metric.
     * @return The CounterMetrics instance.
     */
    public static Counter counter(String name, String... tags){
        return meterRegistryCounterSet.computeIfAbsent(getObjectName(name, tags),
                k -> Counter.register(k));
    }

    private static ObjectName getObjectName(String name, String[] tags){
        return objNamesSet.computeIfAbsent(name + Arrays.stream(tags)
                        .sorted().collect(Collectors.joining()),
                k -> prepareObjectName(name,tags));
    }

    /**
     * Prepares an ObjectName based on the provided metric name and tags.
     *
     * @param name The metric name with a specific format.
     * @param tags The tags associated with the metric.
     * @return The prepared ObjectName.
     */
    private static ObjectName prepareObjectName(String name, String... tags){
        try {
            validateNameAndTags(name, tags);

            String[] parts = name.replaceFirst("_",":").split(":");
            String domain = parts[0];
            String metricName = parts[1];

            List<String> tagList = buildTagList(metricName, tags);
            String objName = domain + ":" + String.join(",", tagList);

            return new ObjectName(objName);
        } catch (MalformedObjectNameException | IllegalArgumentException e) {
            log.error(e.toString());
            if (FAIL_ON_BAD_METRIC_NAME_PROVIDED){
                throw new RuntimeException(e.toString());
            }
            return malformedObjectName;
        }
    }

    private static void validateNameAndTags(String name, String[] tags) {
        if (!name.contains("_")) {
            throw new IllegalArgumentException("Please include '_' into specified metric name: " + name);
        }
        if (tags.length % 2 != 0) {
            throw new IllegalArgumentException("Incorrect set of tag values provided");
        }
    }

    private static List<String> buildTagList(String name, String[] tags) {
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
