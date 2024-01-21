package com.yaro.jwtbackend.metrics;

import org.junit.jupiter.api.Test;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerMetricsTest {

    @Test
    public void testNames() throws InterruptedException {
        MeterRegistry.timer("httpserverseconds",
                        "status", "10")
                .record(100, TimeUnit.SECONDS);

        MeterRegistry.timer("http_server_seconds",
                        "status", "20", "")
                .record(100, TimeUnit.SECONDS);

        Timer timer = MeterRegistry.timer("metric_with_malformed_name");

        float count = timer.getCount();
        float sum = timer.getSum();
        float max = timer.getMax();
        assertEquals(2,count);
        assertEquals(200f,sum,10);
        assertEquals(100f,max,20);

        MeterRegistry.FAIL_ON_BAD_METRIC_NAME_PROVIDED = true;
        assertThrows(RuntimeException.class, () -> MeterRegistry.timer("badmetricname"));

        MeterRegistry.FAIL_ON_BAD_METRIC_NAME_PROVIDED = false;
        assertDoesNotThrow(() -> MeterRegistry.timer("badmetricname"));
    }


    @Test
    public void testObjectNames() throws MalformedObjectNameException {
        ObjectName objn1 = new ObjectName("abc:name=name,tag1=value1,tag2=value2,tag3=value3");
        ObjectName objn2 = new ObjectName("abc:name=name,tag2=value2,tag1=value1,tag3=value3");
        ObjectName objn3 = new ObjectName("abc:name=name,tag1=value1,tag3=value3,tag2=value2");

        assertEquals(objn1,objn2);
        assertEquals(objn1,objn3);
        assertEquals(objn2,objn3);
    }


    @Test
    public void testThreadSafetyMetric() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        // Start 20 threads to concurrently record metrics
        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    MeterRegistry.timer("http_server_seconds",
                                    "status", "10")
                            .record(100, TimeUnit.SECONDS);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        Timer timer = MeterRegistry.timer("http_server_seconds",
                "status", "10");

        float count = timer.getCount();
        float sum = timer.getSum();
        float max = timer.getMax();
        assertEquals(200*1000,count);
        assertEquals(200*1000*100f,sum,200*1000*100*0.02);
        assertEquals(100f,max,100*0.02);
    }

    @Test
    public void testGaugeMetric() throws InterruptedException {
        MeterRegistry.gauge("sql_connection_pool").inc(10);
        MeterRegistry.gauge("sql_connection_pool").dec(3);

        Gauge gauge = MeterRegistry.gauge("sql_connection_pool");

        float value = gauge.getValue();
        assertEquals(10-3,value);
    }

    @Test
    public void testCounterMetric() {
        MeterRegistry.counter("data_search", "data", "inn").inc();
        MeterRegistry.counter("data_search", "data", "inn").inc();
        MeterRegistry.counter("data_search", "data", "pfr").inc(10);

        Counter innCounter = MeterRegistry.counter("data_search", "data", "inn");
        Counter pfrCounter = MeterRegistry.counter("data_search", "data", "pfr");

        float innValue = innCounter.getTotal();
        assertEquals(2,innValue);

        float pfrValue = pfrCounter.getTotal();
        assertEquals(10,pfrValue);
    }

    @Test
    public void testTimerMetric() {

        Timer timer200 = MeterRegistry.timer("http_server_seconds",
                "uri","/addDocument", "status", "200");
        timer200.record(100l, TimeUnit.SECONDS);

        Timer timer500 = MeterRegistry.timer("http_server_seconds",
                "uri","/addDocument", "status", "500");
        timer500.record(1l, TimeUnit.MINUTES);

        Timer timer401 = MeterRegistry.timer("http_server_seconds",
                "uri","/addDocument", "status", "401");
        timer401.record(10_000_000_000l, TimeUnit.NANOSECONDS);


        float timer_status_200_count = timer200.getCount();
        float timer_status_200_sum = timer200.getSum();
        float timer_status_200_max = timer200.getMax();
        assertEquals(1,timer_status_200_count);
        assertEquals(100,timer_status_200_sum,100*0.02);
        assertEquals(100,timer_status_200_max,100*0.02);

        float sum500 = timer500.getSum();
        assertEquals(60, sum500,60*0.02);

        float max401 = timer401.getSum();
        assertEquals(10, max401,10*0.02);
    }
}