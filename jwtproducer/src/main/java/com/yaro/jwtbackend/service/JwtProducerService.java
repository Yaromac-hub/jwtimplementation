package com.yaro.jwtbackend.service;

import com.yaro.jwtbackend.dto.Credentials;
import com.yaro.jwtbackend.dto.JwtTokenResponse;
import com.yaro.jwtbackend.model.CredentialsEntity;
import com.yaro.jwtbackend.repository.JwtProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static com.yaro.jwtbackend.service.JwtUtil.issueJwt;

@Service
public class JwtProducerService {

    private static final long JWT_TOKEN_DURATION = 60000L;
    JwtProducerRepository jwtProducerRepository;

    @Autowired
    public JwtProducerService(JwtProducerRepository jwtProducerRepository) {
        this.jwtProducerRepository = jwtProducerRepository;
    }

    public ResponseEntity<?> getJwt(Credentials creds) {
        CredentialsEntity credentialsEntity = getCredentials(creds);
        if (credentialsEntity != null &&
                creds.getPassword().equals(credentialsEntity.getPassword())) {
            try {
                Date issueDate = new Date();
                Date expirationDate = new Date(issueDate.getTime() + JWT_TOKEN_DURATION);
                String jwt = issueJwt(credentialsEntity, issueDate, expirationDate);
                return ResponseEntity.ok(new JwtTokenResponse(jwt, issueDate.getTime(), expirationDate.getTime()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JWT creation failed.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }

    public CredentialsEntity getCredentials(Credentials creds){
        return jwtProducerRepository.findByLogin(creds.getLogin());
    }
}
