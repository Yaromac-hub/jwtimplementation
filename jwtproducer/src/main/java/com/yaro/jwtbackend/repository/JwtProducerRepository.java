package com.yaro.jwtbackend.repository;

import com.yaro.jwtbackend.model.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtProducerRepository extends JpaRepository<CredentialsEntity, Long> {
    CredentialsEntity findByLogin(String login);
}