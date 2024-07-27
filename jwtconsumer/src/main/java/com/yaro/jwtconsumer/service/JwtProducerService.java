package com.yaro.jwtconsumer.service;

import com.yaro.jwtconsumer.model.JwtTokenWrapper;
import com.yaro.jwtconsumer.repository.JwtProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JwtProducerService {

    JwtProducerRepository jwtProducerRepository;

    @Autowired
    public JwtProducerService(JwtProducerRepository jwtProducerRepository) {
        this.jwtProducerRepository = jwtProducerRepository;
    }

    public JwtTokenWrapper verifyJwt(String token) {
        return JwtUtil.parseToken(token);
    }

    public ResponseEntity<?> getUserData(String login){
        return ResponseEntity.ok(jwtProducerRepository.findByLogin(login));
    }

}
