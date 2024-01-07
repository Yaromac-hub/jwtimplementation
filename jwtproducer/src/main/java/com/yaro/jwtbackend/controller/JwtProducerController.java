package com.yaro.jwtbackend.controller;

import com.yaro.jwtbackend.dto.Credentials;
import com.yaro.jwtbackend.metrics.HttpServerMetrics;
import com.yaro.jwtbackend.metrics.SelfRegisteredMetricInterface;
import com.yaro.jwtbackend.service.JwtProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.ObjectName;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class JwtProducerController {

    private static final HashMap<Integer, HttpServerMetrics> fnsInnResponseStatusCode_multi = new HashMap<>();

    private final JwtProducerService jwtProducerService;
    @Autowired
    public JwtProducerController(JwtProducerService jwtProducerService) {
        this.jwtProducerService = jwtProducerService;
    }

    @PostMapping("/issuejwt")
    ResponseEntity<?> getJwtToken(@RequestBody Credentials creds) throws InterruptedException {
        int i = ThreadLocalRandom.current().nextInt(0, 2 + 1);
        fnsInnResponseStatusCode_multi.computeIfAbsent(i,
                k -> HttpServerMetrics.register("http_server_requests_seconds",
                        "application","sas-fns",
                        "uri","/fnsInnByPassport",
                        "status",String.valueOf(k),
                        "type","COUNTER"));

        fnsInnResponseStatusCode_multi.get(i).inc();
        fnsInnResponseStatusCode_multi.get(i).add(1000_000_000);
        return jwtProducerService.getJwt(creds);
    }

}
