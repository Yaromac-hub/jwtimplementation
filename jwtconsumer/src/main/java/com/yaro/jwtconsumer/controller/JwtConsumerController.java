package com.yaro.jwtconsumer.controller;

import com.yaro.jwtconsumer.model.JwtTokenWrapper;
import com.yaro.jwtconsumer.service.JwtProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtConsumerController {
    private final JwtProducerService jwtProducerService;
    @Autowired
    public JwtConsumerController(JwtProducerService jwtProducerService) {
        this.jwtProducerService = jwtProducerService;
    }

    @GetMapping("/validate_jwt")
    public ResponseEntity<String> checkJwt(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader
    ) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            JwtTokenWrapper wrapper = jwtProducerService.verifyJwt(token);
            if (wrapper.getStatus() == 0){
                return ResponseEntity.ok(wrapper.getJwtDecoded());
            }
            else {
                return ResponseEntity.ok(wrapper.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid or missing JWT token");
        }
    }

    @GetMapping("/user_info")
    public ResponseEntity<?> getUserInfo(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token, @RequestParam String login){
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is empty");
        }
        JwtTokenWrapper wrapper = jwtProducerService.verifyJwt(token.substring(7));
        if (!wrapper.getStatus().equals(0l)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied: " + wrapper.getMessage());
        }
        if (wrapper.getJwtToken().getSubject().equals(login)){
            return jwtProducerService.getUserData(login);
        }
        else if (wrapper.getJwtToken().getRole().equalsIgnoreCase("Admin")){
            return jwtProducerService.getUserData(login);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied");
        }
    }
}
