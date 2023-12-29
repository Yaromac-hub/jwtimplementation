package com.yaro.jwtbackend.controller;

import com.yaro.jwtbackend.dto.Credentials;
import com.yaro.jwtbackend.metrics.HttpServerMetrics;
import com.yaro.jwtbackend.service.JwtProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtProducerController {

    private static final HttpServerMetrics fnsInnResponseSatusCode_0 = HttpServerMetrics.register("http_server_requests_seconds","application=sas-fns","uri=/fnsInnByPassport","status=0","type=COUNTER");

    private final JwtProducerService jwtProducerService;
    @Autowired
    public JwtProducerController(JwtProducerService jwtProducerService) {
        this.jwtProducerService = jwtProducerService;
    }

    @PostMapping("/issuejwt")
    ResponseEntity<?> getJwtToken(@RequestBody Credentials creds) throws InterruptedException {
        fnsInnResponseSatusCode_0.inc();
        fnsInnResponseSatusCode_0.add(1000000);
        return jwtProducerService.getJwt(creds);
    }

}
