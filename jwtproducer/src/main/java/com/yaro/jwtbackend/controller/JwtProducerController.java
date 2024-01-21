package com.yaro.jwtbackend.controller;

import com.yaro.jwtbackend.dto.Credentials;
import com.yaro.jwtbackend.metrics.MeterRegistry;
import com.yaro.jwtbackend.metrics.Timer;
import com.yaro.jwtbackend.service.JwtProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
public class JwtProducerController {

    private final JwtProducerService jwtProducerService;
    @Autowired
    public JwtProducerController(JwtProducerService jwtProducerService) {
        this.jwtProducerService = jwtProducerService;
    }

    @PostMapping("/issuejwt")
    ResponseEntity<?> getJwtToken(@RequestBody Credentials creds) throws InterruptedException {
        int i = ThreadLocalRandom.current().nextInt(0, 2 + 1);
        sendMetrics("getJwtToken", i, 1000000000l);
        return jwtProducerService.getJwt(creds);
    }

    private void sendMetrics(String method, int status, Long responseDurationNanoSeconds) {

        MeterRegistry.timer("http_server_requests_seconds",
                        "uri",method,
                        "status",String.valueOf(status),
                        "type","COUNTER")
                .record(responseDurationNanoSeconds, TimeUnit.NANOSECONDS);
    }

}
