package com.yaro.jwtconsumer.repository;
import com.yaro.jwtconsumer.model.UserDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtProducerRepository extends JpaRepository<UserDataEntity, Long> {
    UserDataEntity findByLogin(String login);
}