package com.yaro.jwtbackend.metrics;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerMetricsTest {

    @Test
    public void testNames(){
        //HttpServerMetrics.register("http_server_requests_seconds","application=sas-fns","uri=/fnsInnByPassport","status=0","type=COUNTER");

        String[] strings = {"str1=str2", "str3=str4"};

        String result = Arrays.stream(strings)
                .collect(Collectors.joining(","));

        String name = "http_server_requests_seconds";
        String name1 = name.replaceFirst("_",":");
        System.out.println(name1);
    }
}