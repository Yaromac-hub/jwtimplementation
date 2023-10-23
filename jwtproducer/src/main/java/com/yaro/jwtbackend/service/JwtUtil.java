package com.yaro.jwtbackend.service;

import com.yaro.jwtbackend.model.CredentialsEntity;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    public static String issueJwt(CredentialsEntity creds, Date issueDate, Date expirationDate) throws Exception {

        String jwt = Jwts.builder().header()
                .add("alg", "RS256")
                .add("typ", "JWT").and()
                .subject(creds.getLogin())
                .claim("name", creds.getName())
                .claim("role", creds.getRole())
                .issuedAt(issueDate)
                .expiration(expirationDate)
                .signWith(RSAKeyReader.readPrivateKey())
                .compact();

        return jwt;
    }
}
